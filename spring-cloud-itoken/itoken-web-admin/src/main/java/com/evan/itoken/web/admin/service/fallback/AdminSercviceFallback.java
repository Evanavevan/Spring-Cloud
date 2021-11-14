package com.evan.itoken.web.admin.service.fallback;

import com.evan.itoken.common.dto.BaseResult;
import com.evan.itoken.common.utils.MapperUtils;
import com.evan.itoken.web.admin.service.AdminService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

@Component
public class AdminSercviceFallback implements AdminService {
    @Override
    public String Login(String loginCode, String password) {
        BaseResult baseResult = BaseResult.notOk(Lists.newArrayList(new BaseResult.Error("502", "从上游服务器接收到无效的响应")));
        try {
            return MapperUtils.obj2json(baseResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
