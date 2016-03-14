package cn.thinkjoy.jx.statistics.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.UserApiService;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.jx.statistics.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.common.HttpUtil;
import com.alibaba.dubbo.common.json.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yhwang on 15/9/2.
 */
@Controller
@RequestMapping("/statistics/login")
public class LoginController {
    @Autowired
    private UserApiService userApiService;

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    /**
     * 伪登录
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkLogin",method = RequestMethod.GET)
    public void checkLogin(HttpServletRequest request,HttpServletResponse response) {
        String token = request.getParameter("token");
        String callBack=request.getParameter("callback");
        if(StringUtils.isBlank(token)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        UserPojo userPojo = userApiService.getUserPojo(token);
        if(userPojo == null){
            throw new BizException(ERRORCODE.NO_PERMISSION.getCode(),ERRORCODE.NO_PERMISSION.getMessage());
        }
        if(userPojo.getAreaIds() == null || userPojo.getAreaIds().size() == 0){
            userPojo.setAreaIds(null);
        }
        if(userPojo.getSchoolIds() == null  || userPojo.getSchoolIds().size() == 0){
            userPojo.setSchoolIds(null);
        }
        HttpUtil.setSession(request, "user", userPojo);
        try {
            String userStr=JSON.json(userPojo);
            if (callBack != null) {
                userStr = callBack+"("+userStr+")";
            }
            response.getWriter().write(userStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
