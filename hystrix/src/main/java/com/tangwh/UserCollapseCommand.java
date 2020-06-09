package com.tangwh;

import com.netflix.hystrix.*;
import org.hibernate.validator.internal.util.IdentitySet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 请求合并方法
 */
public class UserCollapseCommand extends HystrixCollapser<List<User>,User,Integer> {


    private UserService userService;

    private Integer id;

    public UserCollapseCommand(UserService userService, Integer id) {
        super(HystrixCollapser.Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("UserCollapseCommand")).andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(200)));
        this.userService = userService;
        this.id = id;
    }

    /**
     * 请求参数
     * @return
     */
    @Override
    public Integer getRequestArgument() {
        return id;
    }

    /**
     * 请求合并的方法
     * @param collection
     * @return
     */
    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Integer>> collection) {
        // 处理参数
        List<Integer> ids = new ArrayList<>(collection.size());
        for (CollapsedRequest<User, Integer> userIntegerCollapsedRequest : collection) {
            ids.add(userIntegerCollapsedRequest.getArgument());
        }


        return new UserBatchCommar(ids, userService);
    }

    /**
     * 请求结果分发
     * @param users
     * @param collection
     */
    @Override
    protected void mapResponseToRequests(List<User> users, Collection<CollapsedRequest<User, Integer>> collection) {

        int count = 0;
        for (CollapsedRequest<User, Integer> request : collection) {

            request.setResponse(users.get(count++));
        }


    }
}
