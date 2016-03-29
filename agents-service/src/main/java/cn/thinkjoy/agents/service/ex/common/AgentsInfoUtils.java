package cn.thinkjoy.agents.service.ex.common;

import cn.thinkjoy.agents.dao.ex.IAreaExDAO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/3/16.
 */
@Component
public class AgentsInfoUtils {

    //代理商基本信息获取=====================================start==================================================


    //mock代理商
    static class MockAgents{
        /** 代理商等级*/
        private Integer agenstsRank;
        /**代理商当前权限范围简码**/
        private String userArea;
        /**代理商当前对应区域ID**/
        private String areaId;
        /**用户数据权限**/
        private String userData;

        public Integer getAgenstsRank() {
            return agenstsRank;
        }

        public void setAgenstsRank(Integer agenstsRank) {
            this.agenstsRank = agenstsRank;
        }

        public String getUserArea() {
            return userArea;
        }

        public void setUserArea(String userArea) {
            this.userArea = userArea;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public MockAgents(Integer agenstsRank, String userArea, String areaId) {
            this.agenstsRank = agenstsRank;
            this.userArea = userArea;
            this.areaId = areaId;
        }

        public MockAgents(Integer agenstsRank, String userArea, String areaId, String userData) {
            this.agenstsRank = agenstsRank;
            this.userArea = userArea;
            this.areaId = areaId;
            this.userData = userData;
        }

        public String getUserData() {
            return userData;
        }

        public void setUserData(String userData) {
            this.userData = userData;
        }

        public MockAgents() {
        }
    }

    private static MockAgents mockAgents=new MockAgents(1,"61","610000","SELECT id FROM zgk_card WHERE goodsNumber LIKE '61%'");
    /**
     * 通过用户上下文做代理商等级判定
     * 1第一级代理商多为省级
     * 2第二级代理商多为市级
     * 4第三季代理商多为区县级
     *
     * @return
     */
    public static int getAgentsRank() {
        Map<String,Object> userinfo = UserInfoContext.getCurrentUserInfo();
        switch (Integer.valueOf(userinfo.get("roleType").toString())){
            case 2:
                return AgentsConstant.RANKONE;
            case 3:
                return AgentsConstant.RANKTWO;
            case 4:
                return AgentsConstant.RANKTHREE;
        }
        return -1;
    }

    public static String getUserWhereSql(){
        if(getAgentsUserArea()==null){
            return null;
        }
        return "SELECT id FROM zgk_card WHERE goodsNumber LIKE '"+getAgentsUserArea()+"%'";
    }

    /**
     * 通过用户上下文做代理商等级判定
     * 1第一级代理商多为省级
     * 2第二级代理商多为市级
     * 4第三季代理商多为区县级
     *
     * @return
     */
    public static String getAgentsUserArea() {
        Map<String,Object> userinfo = UserInfoContext.getCurrentUserInfo();
        String areaCode=(String)userinfo.get("areaCode");
        if(StringUtils.isNotEmpty(areaCode) && (!"00".equals(areaCode))){
            return areaCode;
        }
        return "";
    }


    public static String getAgentsUserAreaId(){
        Map<String,Object> userinfo = UserInfoContext.getCurrentUserInfo();
        String areaCode=(String)userinfo.get("areaCode");
        if(StringUtils.isNotEmpty(areaCode) && (!"00".equals(areaCode))){
            return addZeroForNum(areaCode,6);
        }
        return "";
    }
    /**
     * 判定代理商所在区域
     * String["区域级别"，"区域代码"]
     *
     * @return
     */
    public static String[] getAgentsAreaCode() {
        String areaRank=null;
        String userArea=getAgentsUserArea();
        if(userArea==null){
            areaRank="-1";
        }else{
            switch (userArea.length()){
                case 2:
                    areaRank=AgentsConstant.AREAONE;
                    break;
                case 4:
                    areaRank=AgentsConstant.AREATWO;
                    break;
                case 6:
                    areaRank=AgentsConstant.AREATHREE;
                    break;
                default:
                    areaRank=AgentsConstant.AREAERROR;
                    break;
            }
        }
        return new String[]{areaRank,getAgentsUserAreaId()};
    }


    /**
     * 获取代理商类型
     * 第1级代理商+省级1+1=2
     * 第1级代理商+市级1+2=3
     * 第1级代理商+区县级1+4=5
     * 第2级代理商+市级取2+2=4
     * 第2级代理商+区县级取2+4=6
     * 第3级代理商+区县级取4+4=8
     *
     * @return
     */
    public static int getAgentsType() {
        String[] area = getAgentsAreaCode();
        return Integer.parseInt(area[0]) + getAgentsRank();
    }
    //代理商基本信息获取=====================================end==================================================
    /**
     * 根据代理商等级适配入库出库时间
     * inputDate 入库时间
     * outputDate 出库时间
     * 1：createDate outputDate1
     * 2：outputDate1 outputDate2
     * 3：outputDate2 outputDate3
     *
     * @param map
     * @return
     */
    public static void setStorageDate(Map<String, Object> map) {
        if (map == null) {
            return;
        }
        switch (getAgentsUserArea().length()) {
            case 0:
                if (map.containsKey("createDate")) {
                    map.put("inputDate", map.get("createDate"));
                }
                if (map.containsKey("outputDate1")) {
                    map.put("outputDate", map.get("outputDate1"));
                }
                break;
            case 2:
                if (map.containsKey("outputDate1")) {
                    map.put("inputDate", map.get("outputDate1"));
                }
                if (map.containsKey("outputDate2")) {
                    map.put("outputDate", map.get("outputDate2"));
                }
                break;
            case 4:
                if (map.containsKey("outputDate2")) {
                    map.put("inputDate", map.get("outputDate2"));
                }
                if (map.containsKey("outputDate3")) {
                    map.put("outputDate", map.get("outputDate3"));
                }
                break;
            case 6:

                if (map.containsKey("outputDate3")) {
                    map.put("inputDate", map.get("outputDate3"));
                }
                break;
            default:
                if (map.containsKey("createDate")) {
                    map.put("inputDate", map.get("createDate"));
                }
                if (map.containsKey("outputDate1")) {
                    map.put("outputDate", map.get("outputDate1"));
                }
                break;
        }
    }


    /**
     * 根据货号规则适配当前货物出入库情况
     * 规则：
     * 货号未被延伸到下一级说明未出库，反之出库
     * 例如：
     * 省代当前货号为010000说明未出库010100/010101说明已出库
     *
     * @param map
     * @return
     */
    public static void setGoodsStatus(Map<String, Object> map) {
        //获取当前虚拟货号
        if (map.containsKey("goodsNumber")) {
            String goosNumber = (String) map.get("goodsNumber");
            if(goosNumber==null){
                goosNumber="";
            }
            switch (goosNumber.length()-getAgentsUserArea().length()) {
                case 0:
                    map.put("goodsStatus", AgentsConstant.NOTOUPUT);
                    break;
                default:
                    map.put("goodsStatus", AgentsConstant.OUPUT);
                    break;
            }
        }
    }


    /**
     * 流向地转换
     * 取得货号之后进行转换
     *
     * @param map
     */
    public static void setFlow(Map<String, Object> map) {
        //获取当前虚拟货号
        if (map.containsKey("goodsNumber")) {
            String goosNumber = (String) map.get("goodsNumber");
            String areaName = null;
            String area = null;
            switch (goosNumber.length()) {
                case 2:
                    //判断当前位数，不够6位的后面补0
                    area = addZeroForNum(goosNumber, 6);
                    //默认走省份表
                    areaName = AreaCacheUtils.getAreaCache("province", area);
                    break;
                case 4:
                    //判断当前位数，不够6位的后面补0
                    area = addZeroForNum(goosNumber, 6);
                    //市表中查
                    areaName = AreaCacheUtils.getAreaCache("city", area);

                    break;
                case 6:
                    area = addZeroForNum(goosNumber, 6);
                    //区县表中查
                    areaName = AreaCacheUtils.getAreaCache("county", area);
                case 8:
                    break;
                default:
            }

            map.put("flow", areaName);
        }
    }


    /**
     * 获取用户当前所在区域的下一级列表
     *
     * @return
     */
    public static List<Map<String, Object>> getFlowNextArea() {
        List<Map<String, Object>> areaNames = null;
        String userArea = getAgentsUserArea();
        switch (userArea.length()) {
            case 2:
                //市表中查
                areaNames = areaExDAO.likeCityById(addStrForNum(userArea, 6, "_"));
                break;
            case 4:
                //区县表中查
                areaNames = areaExDAO.likeCountyById(addStrForNum(userArea, 6, "_"));
                break;
            default:
                //默认省份表中查询
                areaNames = areaExDAO.likeProvince();
                break;
        }
        return areaNames;
    }

    /**
     * 获取用户当前所在区域的下一级列表
     *
     * @return
     */
    public static List<Map<String, Object>> getFlowNextArea(String nextArea) {
        List<Map<String, Object>> areaNames = null;
        String userArea = getAgentsUserArea();
        switch (nextArea.length()) {
            case 2:
                //市表中查
                areaNames = areaExDAO.likeCityById(addStrForNum(userArea, 6, "_"));
                break;
            case 4:
                //区县表中查
                areaNames = areaExDAO.likeCountyById(addStrForNum(userArea, 6, "_"));
                break;
            default:
                //默认省份表中查询
                areaNames = areaExDAO.likeProvince();
                break;
        }
        return areaNames;
    }
    /**
     * 获取用户当前所在区域的下一级列表
     *
     * @return
     */
    public static List<Map<String, Object>> getVIPUserAreaLine(Map<String,Object> map) {
        List<Map<String, Object>> areaNames = null;
        String userArea = getAgentsUserArea();
        if(map.containsKey("area")) {//一切建立在area！=null
            String queryArea=map.get("area")+AgentsInfoUtils.getAgentsUserArea();
            switch (queryArea.length()) {
                case 2:
                //省列
                    map.put("provinceId",addStrForNum(userArea + map.get("area"), 6, "_"));
                    break;
                case 4:
                    //市列
                    map.put("cityId", addStrForNum(userArea + map.get("area"), 6, "_"));

                    break;
                case 6:

                    //区县列
                    map.put("countyId", addStrForNum(userArea + map.get("area"), 6,"_"));

                    break;
            }
        }
        return areaNames;
    }
    /**
     * 批量模式
     *
     * @param maps
     */
    public static void setFlow(List<Map<String, Object>> maps) {
        for (Map<String, Object> map : maps) {
            setFlow(map);
        }
    }


    /**
     * 批量模式
     *
     * @param maps
     */
    public static void setGoodsStatus(List<Map<String, Object>> maps) {
        for (Map<String, Object> map : maps) {
            setGoodsStatus(map);
        }
    }

    /**
     * 批量模式
     *
     * @param maps
     */
    public static void setGoodsStatusAndStorageDate(List<Map<String, Object>> maps) {
        for (Map<String, Object> map : maps) {
            setGoodsStatus(map);
            setStorageDate(map);
        }
    }

    /**
     * 批量模式
     *
     * @param maps
     */
    public static void setStorageDate(List<Map<String, Object>> maps) {
        for (Map<String, Object> map : maps) {
            setStorageDate(map);
        }
    }

    public static String addStrForNum(String str, int strLength, String appendStr) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
//            sb.append("0").append(str);// 左(前)补0
            sb.append(str).append(appendStr);//右(后)补0
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    /**
     * 追加0到字符串后面
     * @param str
     * @param strLength
     * @return
     */
    public static String addZeroForNum(String str, int strLength) {
        return addStrForNum(str, strLength, "0");
    }

    private static IAreaExDAO areaExDAO;

    @Autowired
    private IAreaExDAO autoAreaExDao;

    /**
     * 自动给工具类的AreaExDao赋值，Spring调用
     */
    @PostConstruct
    public void initAreaExDAO() {
        AgentsInfoUtils.areaExDAO = autoAreaExDao;
    }

    public static void initAreaExDAO(IAreaExDAO autoAreaExDao) {
        AgentsInfoUtils.areaExDAO = autoAreaExDao;
    }
}
