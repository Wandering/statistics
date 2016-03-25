package cn.thinkjoy.jx.statistics.interceptor;

import cn.thinkjoy.agents.service.ex.common.UserInfoContext;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.Map;

/**
 * Created by yhwang on 15-6-23.
 */
public class WebCookieInterceptior implements HandlerInterceptor {
    private  static Log logger=LogFactory.getLog(WebCookieInterceptior.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfoContext.removeCurrentUser();
        try {
            String userInfoStr = null;
            Map<String, Object> userInfo = null;
            Cookie[] cookies = request.getCookies();
            for (Cookie c : cookies) {
                if (c.getName().equals("userInfo")) {
                    userInfo = JSON.parseObject(URLDecoder.decode(c.getValue(),"UTF-8"));
                    break;
                }
            }
            UserInfoContext.setCurrentUserInfo(userInfo);
        }catch (Exception e){
            logger.info("用户信息获取异常");
            throw new BizException(ERRORCODE.NO_SESSION.getCode(),ERRORCODE.NO_SESSION.getMessage());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}


