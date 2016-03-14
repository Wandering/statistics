package cn.thinkjoy.jx.statistics.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.EXCodeApiService;
import cn.thinkjoy.zgk.zgksystem.UserAccountApiService;
import cn.thinkjoy.zgk.zgksystem.UserInfoApiService;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.domain.UserAccount;
import cn.thinkjoy.zgk.zgksystem.domain.UserInfo;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.zgk.zgksystem.util.CodeFactoryUtil;
import cn.thinkjoy.zgk.zgksystem.util.Constants;
import cn.thinkjoy.zgk.zgksystem.util.IdentityUtil;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.jlusoft.microschool.core.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zhangjiangbo on 15/11/23
 *
 *
 *
 */
@Controller
@RequestMapping("/statistics/userInfo")
public class UserInfoController {
    private static Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);
    @Autowired
    private UserInfoApiService userInfoApiService;

    @Autowired
    private UserAccountApiService userAccountApiService;

    @Autowired
    private EXCodeApiService excodeApiService;
    /**
     * 登录账户验证
     */
    @ResponseBody
    @RequestMapping(value = "checkLoginNumberIsExist",method = RequestMethod.POST)
    public String checkLoginNumberIsExist(HttpServletRequest request){
        String loginNumber = request.getParameter("loginNumber");
        UserAccount userAccount = (UserAccount)userAccountApiService.findOne("loginNumber",loginNumber);
        if (userAccount!=null){
            return "0";
        }
        return "1";
    }


    /**
     * 新增和修改人员信息
     * @return String
     *
     */
    @ResponseBody
    @RequestMapping(value = "addOrEditUserInfo", method = RequestMethod.POST)
    public String addOrEditUserInfo(HttpServletRequest request){
        String userPojoJson = request.getParameter("userPojoJson");
        if(StringUtils.isBlank(userPojoJson)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        UserPojo userPojo =  null;
        try {
            userPojo=JsonMapper.buildNormalMapper().fromJson(userPojoJson, UserPojo.class);
        }catch (Exception e){
            LOGGER.error(ERRORCODE.JSONCONVERT_ERROR.getMessage());
            throw new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(), ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        UserAccount userAccount = (UserAccount)userAccountApiService.findOne("loginNumber",userPojo.getLoginNumber());
        if (userAccount!=null){
            throw new BizException(ERRORCODE.ACCOUNT_ISEXIST.getCode(), ERRORCODE.ACCOUNT_ISEXIST.getMessage());
        }
        if(userPojo == null){
            throw  new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(), ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }

        if(userPojo.getUserInfoId()==null || userPojo.getUserInfoId()==0){
            Long maxUserInfoCode=excodeApiService.selectMaxCodeByScope(CodeFactoryUtil.USERINFO_CODE, CodeFactoryUtil.USERINFO_TABLE, CodeFactoryUtil.getMinUserInfoCode(userPojo.getDepartmentCode()), CodeFactoryUtil.getMaxUserInfoCode(userPojo.getDepartmentCode()));
            if(maxUserInfoCode==null||maxUserInfoCode==0){
                maxUserInfoCode= CodeFactoryUtil.getInitUserInfo(userPojo.getDepartmentCode());
            }else{
                ++maxUserInfoCode;
            }
            UserInfo u=new UserInfo();
            u.setUserName(userPojo.getUserName());
            u.setDepartmentCode(userPojo.getDepartmentCode());
            u.setPostCode(userPojo.getPostCode());
            u.setDescription(userPojo.getDescription());
            u.setEmail(userPojo.getEmail());
            u.setPhone(userPojo.getPhone());
            u.setUserCode(maxUserInfoCode);
            u.setId(userPojo.getUserInfoId());
            userInfoApiService.updateOrSave(u, null);

            UserAccount account =new UserAccount();
            account.setLoginNumber(userPojo.getLoginNumber());//账户名
            account.setUserCode(maxUserInfoCode);
            account.setPassword(userPojo.getPassword());
            account.setUserType(Constants.USER_TYPE_NORMAL);
            account.setIdentityCode((long) IdentityUtil.NOMAL_MANAGER_IDENTITY);
            Long maxAccountCode=excodeApiService.selectMaxCodeByScope(CodeFactoryUtil.ACCOUNT_CODE, CodeFactoryUtil.ACCOUNT_TABLE, CodeFactoryUtil.getMinUserInfoCode(userPojo.getDepartmentCode()), CodeFactoryUtil.getMaxUserInfoCode(userPojo.getDepartmentCode()));
            if(maxAccountCode==null||maxAccountCode==0){
                maxAccountCode= CodeFactoryUtil.getInitAccount(userPojo.getDepartmentCode());
            }else{
                ++maxAccountCode;
            }
            account.setAccountCode(maxAccountCode);
            userAccountApiService.updateOrSave(account,null);
        }else{
            UserInfo u=new UserInfo();
            u.setUserName(userPojo.getUserName());
            u.setDescription(userPojo.getDescription());
            u.setEmail(userPojo.getEmail());
            u.setPhone(userPojo.getPhone());
            u.setId(userPojo.getUserInfoId());
            userInfoApiService.updateOrSave(u, userPojo.getUserInfoId());
        }
        return "ok";

    }


    /**
     * 修改个人信息
     */
    @ResponseBody
    @RequestMapping(value="updateUserInfo",method=RequestMethod.POST)
    public Object updateUserInfo(HttpServletRequest request){
        String userPojoJson = request.getParameter("userPojoJson");
        if(StringUtils.isBlank(userPojoJson)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        UserPojo userPojo =  null;
        try {
            userPojo=JsonMapper.buildNormalMapper().fromJson(userPojoJson, UserPojo.class);
        }catch (Exception e){
            LOGGER.error(ERRORCODE.JSONCONVERT_ERROR.getMessage());
            throw new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(), ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        if(userPojo == null){
            throw  new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(), ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        UserInfo u=new UserInfo();
        u.setUserName(userPojo.getUserName());
        u.setEmail(userPojo.getEmail());
        u.setPhone(userPojo.getPhone());
        u.setId(userPojo.getUserInfoId());
        userInfoApiService.updateOrSave(u, userPojo.getUserInfoId());
        if(userPojo.getPassword()!=null || !"".equals(userPojo.getPassword())) {
            UserAccount account = new UserAccount();
            account.setPassword(userPojo.getPassword());
            account.setId(userPojo.getAccountId());
            userAccountApiService.updateOrSave(account, account.getId());
        }
        return "ok";
    }


    @ResponseBody
    @RequestMapping(value="checkOldPassword",method=RequestMethod.POST)
    public String checkOldPassword(HttpServletRequest request){
        String userPojoJson = request.getParameter("userPojoJson");
        if(StringUtils.isBlank(userPojoJson)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        UserPojo userPojo =  null;
        try {
            userPojo=JsonMapper.buildNormalMapper().fromJson(userPojoJson, UserPojo.class);
        }catch (Exception e){
            LOGGER.error(ERRORCODE.JSONCONVERT_ERROR.getMessage());
            throw new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(), ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("password", userPojo.getPassword());
        dataMap.put("id", userPojo.getAccountId());
        UserAccount account=(UserAccount) userAccountApiService.queryOne(dataMap);
        if(account!=null){
            return  "ok";
        }else{
            throw  new BizException(ERRORCODE.USER_NAME_OR_PASSWORD_DO_NOT_MATCH.getCode(), ERRORCODE.USER_NAME_OR_PASSWORD_DO_NOT_MATCH.getMessage());
        }
    }



    /**
     * 通过人员信息名称检查是否已存在的人员信息信息
     * @param request request
     * @return String
     */
    @ResponseBody
    @RequestMapping(value="checkUserInfo",method=RequestMethod.POST)
    public String checkUserInfo(HttpServletRequest request){
        String userName=request.getParameter("userName");
        if(StringUtils.isBlank(userName)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
       List<UserInfo> userInfoList= userInfoApiService.findList("productName", userName);
        if(userInfoList!=null && userInfoList.size()>0){
            throw  new BizException(ERRORCODE.ALREADY_EXIST_ERROR.getCode(), ERRORCODE.ALREADY_EXIST_ERROR.getMessage());
        }else{
            return  "ok";
        }
    }


    /**
     * 删除人员信息
     * @param request request
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "delUserInfo",method = RequestMethod.GET)
    public String delUserInfo(HttpServletRequest request){
        String userInfoId = request.getParameter("id");
        if(StringUtils.isBlank(userInfoId)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        UserInfo userInfo = (UserInfo)userInfoApiService.findOne("id", userInfoId);
        userInfo.setStatus(Constants.DELETEED_STATUS);
        userInfoApiService.update(userInfo);
        Long userCode = userInfo.getUserCode();
        UserAccount userAccount = (UserAccount) userAccountApiService.findOne("userCode",userCode);
        userAccount.setStatus(Constants.DELETEED_STATUS);
        userAccountApiService.update(userAccount);
        return "ok";
    }
    /**
     * 获取单个人员信息
     * @param request request
     * @return T
     */
    @ResponseBody
    @RequestMapping(value = "getUserInfo",method = RequestMethod.GET)
    public UserInfo getUserInfo(HttpServletRequest request){
        String userInfoId = request.getParameter("id");
        if (StringUtils.isBlank(userInfoId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id",Long.parseLong(userInfoId));
        dataMap.put("status", Constants.NORMAL_STATUS);
        UserInfo userInfo = (UserInfo)userInfoApiService.queryOne(dataMap);
        if(userInfo == null){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return userInfo;
    }


}
