package cn.thinkjoy.test.http;

import cn.thinkjoy.test.common.RequestUtils;
import junit.framework.TestCase;

/**
 * Created by admin on 2016/1/14.
 */

/**
 * HTTP测试类 测试相关接口每次发包之前应该执行用以保证接口基本运转
 */
public class HttpTest extends TestCase {
    //当前server主机
    String host = "http://localhost:8080";
    /*接口地址*/

    /*列表主请求*/
    String agents_url="/admin/agents";
    /*出库*/
    String output_url="/admin/output";
    /*获取用户下一级区域*/
    String getCurrUserNextArea_url="/admin/getCurrUserNextArea";

    String monitors_url="/admin/monitors";


    public void testAgents() {
        String cardNumber="GK60349298";
        String area=null;
        String isOutput="true";
        String page="1";
        String rows="1";
        String url = host + agents_url +
                "?cardNumber="+RequestUtils.paramCheckToEmpty(cardNumber)+
                "&area="+RequestUtils.paramCheckToEmpty(area)+
                "&isOutput="+RequestUtils.paramCheckToEmpty(isOutput)+
                "&currentPageNo="+RequestUtils.paramCheckToEmpty(page)+
                "&pageSize="+RequestUtils.paramCheckToEmpty(rows)+"&debug=true";
        String result = RequestUtils.requestPost(url);
        //校验返回码是不是正常代码
        assertTrue(result.contains("\"rtnCode\":\"0000000\""));
    }

    public void testOutput() {
        String area="01";
        String outputList="1";
        String rows=null;
        String url = host + output_url + "?outputList="+RequestUtils.paramCheckToEmpty(outputList)+"&area="+RequestUtils.paramCheckToEmpty(area)+"+&rows="+RequestUtils.paramCheckToEmpty(rows)+"&debug=true";
        String result = RequestUtils.requestPost(url);
        //校验返回码是不是正常代码
        assertTrue(result.contains("\"rtnCode\":\"0000000\""));
    }

    public void testGetCurrUserNextArea() {
        String url = host + getCurrUserNextArea_url+"?debug=true";
        String result = RequestUtils.requestPost(url);
        //校验返回码是不是正常代码
        assertTrue(result.contains("\"rtnCode\":\"0000000\""));

        url = host + getCurrUserNextArea_url+"?nextArea=6101&debug=true";
        result = RequestUtils.requestPost(url);
        //校验返回码是不是正常代码
        assertTrue(result.contains("\"rtnCode\":\"0000000\""));
    }

    public void testMonitors() {
        String queryParam=null;
        String area=null;
        String status=null;
        String startDate="";
        String endDate="";
        String activityStatus="0";
        String page=null;
        String rows=null;
        String url = host + monitors_url
                +"?queryParam="+RequestUtils.paramCheckToEmpty(queryParam)
                +"&area="+RequestUtils.paramCheckToEmpty(area)
                +"&status="+RequestUtils.paramCheckToEmpty(status)
                +"&startDate="+RequestUtils.paramCheckToEmpty(startDate)
                +"&endDate="+RequestUtils.paramCheckToEmpty(endDate)
                +"&page="+RequestUtils.paramCheckToEmpty(page)
                +"&rows="+RequestUtils.paramCheckToEmpty(rows)
                +"&activityStatus="+RequestUtils.paramCheckToEmpty(activityStatus)
                ;
        String result = RequestUtils.requestPost(url);
        //校验返回码是不是正常代码
        assertTrue(result.contains("\"rtnCode\":\"0000000\""));
    }
}
