package cn.thinkjoy.agents.service.ex.common;

import cn.thinkjoy.agents.dao.ex.IAreaExDAO;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/3/16.
 */
public class AgentsInfoUtils {


    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
//            sb.append("0").append(str);// 左(前)补0
             sb.append(str).append("0");//右(后)补0
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }
    /**
     * 通过用户上下文做代理商等级判定
     * 1第一级代理商多为省级
     * 2第二级代理商多为市级
     * 4第三季代理商多为区县级
     * @return
     */
    public static int getAgentsRank(){
        return AgentsConstant.RANKONE;
    }


    /**
     * 判定代理商所在区域
     * String["区域级别"，"区域代码"]
     * @return
     */
    public static String[] getAgentsAreaCode(){

        return new String[]{AgentsConstant.AREAONE,"110000"};
    }

    /**
     * 组合代理商虚拟货号
     * 第1级代理商+省级取前两位1+1=2
     * 第1级代理商+市级取中两位1+2=3
     * 第1级代理商+区县级取后两位1+4=5
     * 第2级代理商+市级取中两位2+2=4
     * 第2级代理商+区县级取后两位2+4=6
     * 第3级代理商+区县级取后两位4+4=8
     * @return
     */
    public static String getAgentsVirtualItem(){
        String VirtualNumber="110000";
        String[] area=getAgentsAreaCode();
        switch (getAgentsType()){
            //取前两位
            case 2:
                VirtualNumber=area[1].substring(0,2)+"____";
                break;

            //取中两位
            case 3:
            case 4:
                VirtualNumber="__"+area[1].substring(2,4)+"__";
                break;
            //取后两位
            case 5:
            case 6:
            case 8:
                VirtualNumber="____"+area[1].substring(4,6);
                break;
            default:
                break;
        }
        return VirtualNumber;
    }

    /**
     * 获取代理商类型
     * 第1级代理商+省级1+1=2
     * 第1级代理商+市级1+2=3
     * 第1级代理商+区县级1+4=5
     * 第2级代理商+市级取2+2=4
     * 第2级代理商+区县级取2+4=6
     * 第3级代理商+区县级取4+4=8
     * @return
     */
    public static int getAgentsType(){
        String[] area=getAgentsAreaCode();
        return Integer.parseInt(area[0])+getAgentsRank();
    }

    /**
     * 根据代理商等级适配入库出库时间
     * inputDate 入库时间
     * outputDate 出库时间
     * 1：createDate outputDate1
     * 2：outputDate1 outputDate2
     * 3：outputDate2 outputDate3
     * @param map
     * @return
     */
    public static void setStorageDate(Map<String,Object> map){
        if(map==null){
            return;
        }
        switch (getAgentsRank()){
            case AgentsConstant.RANKONE:
                if(map.containsKey("createDate")){
                    map.put("inputDate",map.get("createDate"));
                }
                if(map.containsKey("outputDate1")){
                    map.put("onputDate",map.get("outputDate1"));
                }
                break;
            case AgentsConstant.RANKTWO:
                if(map.containsKey("outputDate1")){
                    map.put("inputDate",map.get("outputDate1"));
                }
                if(map.containsKey("outputDate2")){
                    map.put("onputDate",map.get("outputDate2"));
                }
                break;
            case AgentsConstant.RANKTHREE:
                if(map.containsKey("outputDate2")){
                    map.put("inputDate",map.get("outputDate2"));
                }
                if(map.containsKey("outputDate3")){
                    map.put("onputDate",map.get("outputDate3"));
                }
                break;
        }
    }

    /**
     * 批量模式
     * @param maps
     */
    public static void setStorageDate(List<Map<String,Object>> maps){
        for(Map<String,Object> map:maps){
            setStorageDate(map);
        }
    }

    /**
     * 根据货号规则适配当前货物出入库情况
     * 规则：
     * 货号未被延伸到下一级说明未出库，反之出库
     * 例如：
     * 省代当前货号为010000说明未出库010100/010101说明已出库
     * @param map
     * @return
     */
    public static void setGoodsStatus(Map<String,Object> map){
        //获取当前虚拟货号
        if(map.containsKey("goodsNumber")){
            String goosNumber=(String)map.get("goodsNumber");
            switch (getAgentsType()){
                case 2:
                    try {
                        if(goosNumber.substring(2, 4)!=null) {
                            map.put("goodsStatus",AgentsConstant.OUPUT );
                        }
                    }catch (StringIndexOutOfBoundsException e){
                        map.put("goodsStatus",AgentsConstant.NOTOUPUT);
                    }
                    break;
                case 3:
                case 4:
                    try {
                        if(goosNumber.substring(4, 6)!=null) {
                            map.put("goodsStatus",AgentsConstant.OUPUT );
                        }
                    }catch (StringIndexOutOfBoundsException e){
                        map.put("goodsStatus",AgentsConstant.NOTOUPUT);
                    }
                    break;
                case 5:
                case 6:
                case 8:
                    map.put("goodsStatus",AgentsConstant.NOTOUPUT);
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 流向地转换
     * 取得货号之后进行转换
     * @param map
     */
    public static void setFlow(Map<String,Object> map){
        //获取当前虚拟货号
        if(map.containsKey("goodsNumber")){
            String goosNumber=(String)map.get("goodsNumber");
            String areaName=null;
            String area=null;
            switch (getAgentsType()){
                case 2:
                    //判断当前位数，不够6位的后面补0
                    area=addZeroForNum(goosNumber,6);
                    //市表中查
                    areaName=areaExDAO.queryCityById(area);
                    break;
                case 3:
                case 4:
                    area=addZeroForNum(goosNumber,6);
                    //区县表中查
                    areaName=areaExDAO.queryCityById(area);
                    break;
                case 5:
                case 6:
                case 8:
                    break;
                default:
                    break;
            }
            map.put("flow",areaName);
        }
    }

    /**
     * 批量模式
     * @param maps
     */
    public static void setFlow(List<Map<String,Object>> maps){
        for(Map<String,Object> map:maps){
            setFlow(map);
        }
    }


    /**
     * 批量模式
     * @param maps
     */
    public static void setGoodsStatus(List<Map<String,Object>> maps){
        for(Map<String,Object> map:maps){
            setGoodsStatus(map);
        }
    }

    /**
     * 批量模式
     * @param maps
     */
    public static void setGoodsStatusAndStorageDate(List<Map<String,Object>> maps){
        for(Map<String,Object> map:maps){
            setGoodsStatus(map);
            setStorageDate(map);
        }
    }

    private static IAreaExDAO areaExDAO;

    public static IAreaExDAO getAreaExDAO() {
        return areaExDAO;
    }

    public static void setAreaExDAO(IAreaExDAO areaExDAO) {
        AgentsInfoUtils.areaExDAO = areaExDAO;
    }
}
