package cn.thinkjoy.jx.statistics.controller;

import cn.thinkjoy.zgk.zgksystem.ProductApiService;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.jx.statistics.common.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yhwang on 15/10/22.
 */
@Controller
@RequestMapping("/statistics/product")
public class ProductController {
    @Autowired
    private ProductApiService productApiService;
    /**
     * 获取产品Code和产品名称树形结构
     * @return Map
     */
    @ResponseBody
    @RequestMapping(value = "queryTreeProduct",method = RequestMethod.GET)
    public List<TreeBean> queryTreeProduct(HttpServletRequest request){
        UserPojo userPojo=(UserPojo) HttpUtil.getSession(request, "user");
        return productApiService.queryTreeProduct(userPojo);
    }
}
