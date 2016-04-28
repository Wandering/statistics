/**
 * Created by kepeng on 15/10/23.
 */
define(function (require, exports, module) {

    require('cookie');
    var token = $.cookie('bizData');
    var baseUrl = '/statistics';
    //var testUrl = "10.136.13.245:8080/";
    var testUrl = "agent.zhigaokao.cn/";
    var UrlConfig = {
        /**
         * 统一登录入口
         */
        login: testUrl + 'dist/app/login.html',
        /**
         * 获取用户信息
         */
        checkLogin: baseUrl + '/login/checkLogin?token=' + token,
        getMenuTree: baseUrl + '/menu/getMenuTree?token=' + token,
        queryOpFans: baseUrl + '/fans/queryOpFans',
        queryArticle: baseUrl + '/article/queryArticle',
        queryPageMenu: baseUrl + '/menu/queryPage?token=' + token,
        addOrEditMenu: baseUrl + '/menu/addOrEditMenu?token=' + token,
        getMenuDetail: baseUrl + 'menu/getMenuDetail?token=' + token,
        delMenu: baseUrl + '/menu/delMenu?token=' + token,


        /**
         * 货物管理模块
         */
        getGoodsMangeOut: '/admin/agents?token=' + token + '&isOutput=true', //已出库
        getGoodsMange: '/admin/agents?token=' + token + '&isOutput=false', //未出库

        /**
         * 异常模块
         */
        getMonitorsList: '/admin/monitors?token=' + token,


        /**
         * 省份数据权限拉取
         */
        getProvinceList:'/admin/getCurrUserNextArea',// 获取用户下一级区域


        /**
         * 图表
         */
        getErrorChart:'/admin/errorChart',//图表


        /**
         *  根据条件查询订单信息
         *
         */
        queryOrderPageByConditions:'/statistics/order/queryOrderPageByConditions', //

        /**
         *  收益总览
         *
         */
        queryAllDepartmentIncome:'/statistics/order/queryAllDepartmentIncome', //
        /**
         *  个单位收益总览
         *
         */
        querySingleDepartmentIncome:'/statistics/order/querySingleDepartmentIncome', //
        /**
         *  省份
         *
         */
        findProvinceList:'/system/dataDictionary/findProvinceList',//
        /**
         *  市
         *
         */
        findCityList:'/system/dataDictionary/findCityList',//
        /**
         *  区县
         *
         */
        findCountyList:'/system/dataDictionary/findCountyList', //

        /**
         *  分享收益
         *
         */
        queryAllUserIncome:'/statistics/order/queryAllUserIncome', //


        /**
         *  分成
         *
         */
        findSeparate:'/separateController/findSeparate', //
        /**
         *  修改状态为发货
         *
         */
        updateSendGoodsState:'/statistics/order/updateSendGoodsState', //

        /**
         *  获取结算记录
         *
         */
        querySettlementRecordsByDepartmentCode:'/statistics/order/querySettlementRecordsByDepartmentCode', //


        /**
         *  确定结算
         *
         */
        settlementByDepartmentCode:'/statistics/order/settlementByDepartmentCode', //



        /**
         *  获取收益详情
         *
         */
        queryUserIncomeDetailByUserId:'/statistics/order/queryUserIncomeDetailByUserId' //




    };
    module.exports = UrlConfig;
});
