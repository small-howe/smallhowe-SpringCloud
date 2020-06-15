package com.tangwh.service;

import com.tangwh.api.IUserService;
import org.springframework.cloud.openfeign.FeignClient;

// 和那个服务绑定
//@FeignClient(value = "provider",fallback = HelloServiceFallback.class)
@FeignClient(value = "provider",fallbackFactory = HelloServiceFallbackFactory.class)
public interface HelloService extends IUserService {


    /**
     * 这里的hello 是 provider中的  hello接口 访问
     * 方法名 无所谓
     * 注解 中的 接口请求 和provider 所请求的一致
     * 返回类型 也必须一致
     * 参数  必须一致
     *
     * @return
     */


}
