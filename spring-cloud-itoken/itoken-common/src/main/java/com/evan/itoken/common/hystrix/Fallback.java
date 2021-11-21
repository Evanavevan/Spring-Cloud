package com.evan.itoken.common.hystrix;

import com.evan.itoken.common.dto.BaseResult;
import com.evan.itoken.common.utils.MapperUtils;
import com.google.common.collect.Lists;

/**
 * 通用的熔断方法
 */
public class Fallback {
    /**
     * 502错误
     * @return
     */
    public static String badGateway() {
        BaseResult baseResult = BaseResult.notOk(Lists.newArrayList(new BaseResult.Error("502", "从上游服务器接收到无效的响应")));
        try {
            return MapperUtils.obj2json(baseResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
