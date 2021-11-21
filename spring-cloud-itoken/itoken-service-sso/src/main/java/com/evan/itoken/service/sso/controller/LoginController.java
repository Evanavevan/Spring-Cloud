package com.evan.itoken.service.sso.controller;

import com.evan.itoken.common.domain.TbSysUser;
import com.evan.itoken.common.utils.CookieUtils;
import com.evan.itoken.common.utils.MapperUtils;
import com.evan.itoken.service.sso.service.LoginService;
import com.evan.itoken.service.sso.service.consumer.RedisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisService redisService;

    /**
     * 跳转登录页面
     * @param request 请求
     * @param url 重定向url
     * @param model {@link Model}
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(@RequestParam(required = false) String url, HttpServletRequest request, Model model) {
        String token = CookieUtils.getCookieValue(request, "token");

        // token不为空可能已登录
        if (StringUtils.isNotBlank(token)) {
            String loginCode = redisService.get(token);
            if (StringUtils.isNotBlank(loginCode)) {
                String json = redisService.get(loginCode);
                if (StringUtils.isNotBlank(json)) {
                    try {
                        TbSysUser tbSysUser = MapperUtils.json2pojo(json, TbSysUser.class);
                        // 已登录
                        if (tbSysUser != null && StringUtils.isNotBlank(url)) return "redirect:" + url;
                        // 将登录信息传到登录页
                        model.addAttribute("tbSysUser", tbSysUser);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "login";
    }

    /**
     * 登录业务
     * @param loginCode 账号
     * @param password 密码
     * @param url 重定向url
     * @param request 请求
     * @param response 响应
     * @param redirectAttributes {@link RedirectAttributes}
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(String loginCode, String password, @RequestParam(required = false)String url,
                        HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        TbSysUser tbSysUser = loginService.login(loginCode, password);

        // 登录成功
        if (tbSysUser != null) {
            String token = UUID.randomUUID().toString();
            String result = redisService.put(token, loginCode, 60 * 60 * 24);
            if ("ok".equals(result) && StringUtils.isNotBlank(result)) {
                CookieUtils.setCookie(request, response, "token", token);
                if (StringUtils.isNotBlank(result)) return "redirect:" + url;

            }
            // 熔断处理
            else {
                redirectAttributes.addFlashAttribute("message", "服务器异常，请稍后再试！");
            }
        }
        // 登录失败
        else {
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误，请重新输入");
        }

        return "login";
    }
}
