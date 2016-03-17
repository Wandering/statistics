package cn.thinkjoy.jx.statistics.controller.agents;

import cn.thinkjoy.agents.service.ICardService;
import cn.thinkjoy.agents.service.ex.ICardExService;
import cn.thinkjoy.agents.service.ex.common.IBaseExService;
import cn.thinkjoy.jx.statistics.controller.agents.common.BaseCommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yhwang on 15/9/2.
 */
@Controller
@RequestMapping("/admin")
public class AgentsController extends BaseCommonController {

    @Autowired
    private ICardService cardService;
    @Autowired
    private ICardExService cardExService;

//    @ResponseBody
//    @RequestMapping(value = "/agentlist")
//    public Object test(){
//        return cardExService.queryPageByDataPerm(new HashMap<String, Object>(),1,10,"id", SqlOrderEnum.DESC,null);
//    }

    @ResponseBody
    @RequestMapping(value = "/agents")
    public Object queryPage(@RequestParam(required=false,defaultValue = "1") Integer page,@RequestParam(required=false,defaultValue = "10") Integer rows){
        return doPage(page,rows);
    }

    @Override
    protected IBaseExService getService() {
        return cardExService;
    }
}
