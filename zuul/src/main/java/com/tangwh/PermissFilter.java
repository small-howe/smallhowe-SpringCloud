package com.tangwh;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/***
 * 权限过滤器
 */
@Component
public class PermissFilter extends ZuulFilter {

    /**
     * 过滤器的类型
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器优先级
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 核心的过滤逻辑写这里
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //获取当前请求
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();//获取当前请求
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!"javaboy".equals(username) || !"123".equals(password)){

            //如果请求条件不满足的话,直接从这里给出响应
            //请求到此为止
            ctx.setSendZuulResponse(false);
            // 设置响应状态
            ctx.setResponseStatusCode(401);
            //设置请求头
            ctx.addZuulResponseHeader("content-type", "text/html;charset=utf-8");
            ctx.setResponseBody("非法访问");
        }

        return null;
    }
}
