package cn.thinkjoy.jx.statistics.controller;


import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.protocol.RequestT;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.jx.statistics.service.IAppActionStatisticsService;
import cn.thinkjoy.jx.statistics.util.AbstractRestfulService;
import cn.thinkjoy.security.UserCredentials;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequestMapping(value="/app")
public class ClientController {

    private static final Logger LOGGER= LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private IAppActionStatisticsService appActionStatisticsService;


    /**
     * 找家教，在线专家，名师面对面客户端访问接口
     * @return
     */
    @RequestMapping(value = "/insertAppActionStatistics", method = RequestMethod.POST)
    @ResponseBody
    public boolean insertAppActionStatistics(@RequestBody RequestT<Map<String, Object>> request) throws BizException {
        // cn.thinkjoy.jx.statistics.util.UserCredentials userCredentials = AbstractRestfulService.getUserCredentials();
        // LOGGER.info("====app feedBack userName " + userCredentials.getUsername());
        // LOGGER.info("====app feedBack userId " + userCredentials.getUid());
        // Integer userId = userCredentials.getUid();
        //Integer userType = userCredentials.getUserType();
        /* */
        Map<String, Object> requestData = request.getData();
        LOGGER.info("====app feedBack param= " + JSON.toJSONString(requestData));


        Integer userId = (Integer) requestData.get("userId");
        if (null == userId || userId == 0) {
            LOGGER.info("====用户id不能为空！ ");
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Integer userType = (Integer) requestData.get("userType");
        if (null == userType || userType == 0) {
            LOGGER.info("====用户类型不能为空 ");
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Integer actionType = (Integer) requestData.get("actionType");
        if ("".equals(actionType)) {
            LOGGER.info("====用户行为不能为空！");
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }

        //Long userId, String userType,String actionType
        boolean flag = false;
          try{
                int i = appActionStatisticsService.insertAppActionStatistics(userId.longValue(),userType.toString(),actionType.toString());
                if(i > 0){
                    flag = true;
                }else{
                    throw new BizException(ERRORCODE.INSERT_ERROR.getCode(), ERRORCODE.INSERT_ERROR.getMessage());
                }
            }catch(Exception e){
           e.printStackTrace();
            throw new BizException(ERRORCODE.INSERT_ERROR.getCode(), ERRORCODE.INSERT_ERROR.getMessage());
        }
        return flag;
    }

}

