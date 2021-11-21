package com.evan.itoken.service.sso.service;

import com.evan.itoken.common.domain.TbSysUser;

/**
 * 登录业务
 */
public interface LoginService {
    /**
     * 登录
     * @param loginCode 账号
     * @param plantPassword 密码
     * @return
     */
    TbSysUser login(String loginCode, String plantPassword);
}
