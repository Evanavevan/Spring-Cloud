package com.evan.itoken.service.admin.service;

import com.evan.itoken.service.admin.domain.TbSysUser;

public interface AdminService {
    /**
     * 注册
     * @param tbSysUser {@link TbSysUser}
     */
    void register(TbSysUser tbSysUser);

    /**
     * 登录
     * @param loginCode 登录账号
     * @param plantPassword 明文登录密码
     * @return
     */
    TbSysUser login(String loginCode, String plantPassword);
}
