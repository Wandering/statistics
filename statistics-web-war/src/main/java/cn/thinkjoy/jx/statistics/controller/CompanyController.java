package cn.thinkjoy.jx.statistics.controller;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.CompanyApiService;
import cn.thinkjoy.zgk.zgksystem.PostApiService;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.pojo.CompanyPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.zgk.zgksystem.util.IdentityUtil;
import cn.thinkjoy.jx.statistics.common.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yhwang on 15/10/22.
 */
@Controller
@RequestMapping("/statistics/company")
public class CompanyController {
    @Autowired
    private CompanyApiService companyApiService;
    @Autowired
    private PostApiService postApiService;
    /**
     * 查询代理公司
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryCompany",method = RequestMethod.GET)
    public Page<CompanyPojo> queryCompany(HttpServletRequest request){
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request, "user");
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize =Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String parentCode=request.getParameter("productCode");
        Page<CompanyPojo> page = companyApiService.queryCompany(userPojo,currentPageNo,pageSize,parentCode);
        if(page.getList() == null || page.getCount() == 0){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        return  page;
    }

    /**
     * 给公司分配平台权限
     * @param request
     * @return
     */
    @RequestMapping(value = "companyAuthority",method = RequestMethod.POST)
    @ResponseBody
    public String companyAuthority(HttpServletRequest request){
        UserPojo userPojo = (UserPojo)HttpUtil.getSession(request,"user");
        String companyCode = request.getParameter("companyCode");
        if(StringUtils.isBlank(companyCode)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        postApiService.postSystemAuthority((long)IdentityUtil.COMPANY_MANAGER_POST,userPojo.getClientInfoPojo().getSystemCode());
        return "ok";
    }
}
