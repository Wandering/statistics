package cn.thinkjoy.jx.statistics.interceptor;

import cn.thinkjoy.agents.service.ex.common.CacheService;
import cn.thinkjoy.agents.service.ex.common.UserInfoContext;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by yhwang on 15-6-23.
 */
@Component
public class WebCookieInterceptior implements HandlerInterceptor {
    @Autowired
    private CacheService redisCacheService;

    private  static Log logger=LogFactory.getLog(WebCookieInterceptior.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfoContext.removeCurrentUser();
        try {
            //尝试从cookie上面获取用户信息
            String userInfoStr = null;
            Map<String, Object> userInfo = null;
            Cookie[] cookies = request.getCookies();
            if(cookies!=null && cookies.length!=0) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("userInfo")) {
                        userInfo = JSON.parseObject(URLDecoder.decode(c.getValue(), "UTF-8"));
                        break;
                    }
                }
            }
            if(userInfo==null){
                String token=request.getParameter("token");
                userInfoStr=redisCacheService.getValue(token);
                Cookie cookie=new Cookie("userInfo", URLEncoder.encode(userInfoStr, "UTF-8"));
                cookie.setPath("/");

                userInfo=JSON.parseObject(userInfoStr);
                if(userInfo==null){
                    if(!"/statistics/login/checkLogin".equals(request.getRequestURI())){
                        throw new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(), ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
                    }
                }
                response.addCookie(cookie);
            }
            logger.info("用户信息："+userInfoStr);
            UserInfoContext.setCurrentUserInfo(userInfo);
        }catch (Exception e){
            logger.info("用户信息获取异常");
            throw new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(),ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
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


