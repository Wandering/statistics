package cn.thinkjoy.jx.statistics.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.DeparmentApiService;
import cn.thinkjoy.zgk.zgksystem.PostApiService;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.domain.K12systemPost;
import cn.thinkjoy.zgk.zgksystem.domain.Post;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.jx.statistics.common.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yhwang on 15/10/23.
 */
@Controller
@RequestMapping("/statistics/post")
public class PostController {
    @Autowired
    private PostApiService postApiService;
    @Autowired
    private DeparmentApiService deparmentApiService;
    /**
     * 查询岗位
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryPost", method = RequestMethod.GET)
    public Page<Post> queryPost(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo)HttpUtil.getSession(request,"user");
        String currentPageNo = HttpUtil.getParameter(request, "currentPageNo", "1");
        String pageSize = HttpUtil.getParameter(request, "pageSize", "10");
        Page<Post> postPage = null;
        if(userPojo.getPostCode() == -2){//系统管理员
            Long postCode=Long.valueOf(HttpUtil.getParameter(request, "postCode", "-1"));
            postPage =  postApiService.getManagerPost(postCode);

        }else {
//            postPage = postApiService.queryPostBycompanyCode(currentPageNo,pageSize,userPojo.getAccountCode()/100000);
            postPage = postApiService.queryPostByCreator(currentPageNo,pageSize,userPojo.getAccountCode().toString());
        }
        return postPage;
    }

    /**
     * 获取管理岗位
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getManagerPost",method = RequestMethod.GET)
    public Page<Post> getManagerPost(HttpServletRequest request){
        Long postCode=Long.valueOf(HttpUtil.getParameter(request, "postCode", "-1"));
        return  postApiService.getManagerPost(postCode);
    }
    /**
     * 获取部门Code和部门名称树形结构
     * @param request request
     * @return List
     */
    @ResponseBody
    @RequestMapping(value = "queryTreeDepartmentAll",method = RequestMethod.GET)
    public List<TreeBean> queryTreeDepartmentAll(HttpServletRequest request){
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        return deparmentApiService.recursionTree(userPojo.getDepartmentCode());
    }
    @ResponseBody
    @RequestMapping(value = "postyAuthority",method = RequestMethod.POST)
    public String postyAuthority(HttpServletRequest request){
        String postCode = request.getParameter("postCode");
        if(StringUtils.isBlank(postCode)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        UserPojo userPojo = (UserPojo)HttpUtil.getSession(request,"user");
        K12systemPost k12systemPost = postApiService.queryK12systemPost(Long.valueOf(postCode),userPojo.getClientInfoPojo().getSystemCode());
        if (null==k12systemPost || (null!=k12systemPost && k12systemPost.getStatus()==-1)) {
            postApiService.postSystemAuthority(Long.valueOf(postCode), userPojo.getClientInfoPojo().getSystemCode());
        }
        return "ok";

    }
}
