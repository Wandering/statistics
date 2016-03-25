package cn.thinkjoy.agents.service.ex.common;


import cn.thinkjoy.common.domain.UserDomain;

import java.util.Map;

/**
 * 用户上下文
 * <p/>
 * 创建时间: 14-9-1 下午11:21<br/>
 *
 * @author qyang
 * @since v0.0.1
 * @author Michael
 * @since v0.0.10
 */
public class UserInfoContext {
    private static ThreadLocal<Map<String,Object>> context = new ThreadLocal<>();

    public static Map<String,Object> getCurrentUserInfo(){
       return context.get();
    }

    public static void setCurrentUserInfo(Map<String,Object> userInfo){
        context.set(userInfo);
    }

    /**
     * 应该显示调用
     */
    public static void removeCurrentUser() {
        context.remove();
    }

}
