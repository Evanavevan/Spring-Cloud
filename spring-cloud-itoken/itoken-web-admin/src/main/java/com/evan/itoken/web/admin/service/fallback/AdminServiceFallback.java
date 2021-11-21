package com.evan.itoken.web.admin.service.fallback;

import com.evan.itoken.common.hystrix.Fallback;
import com.evan.itoken.web.admin.service.AdminService;
import org.springframework.stereotype.Component;

@Component
public class AdminServiceFallback implements AdminService {
    @Override
    public String Login(String loginCode, String password) {
        return Fallback.badGateway();
    }
}
