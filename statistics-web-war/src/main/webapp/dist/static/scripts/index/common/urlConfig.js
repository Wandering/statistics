/**
 * Created by kepeng on 15/10/23.
 */
define('static/scripts/index/common/urlConfig', ['sea-modules/jquery/cookie/jquery.cookie'], function (require, exports, module) {

    require('sea-modules/jquery/cookie/jquery.cookie');
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
        queryOrderPageByConditions:'http://172.16.160.226:8082/statistics/order/queryOrderPageByConditions' //


        // 172.16.160.226:8081   父系统
        // 172.16.160.226:8082   子系统


    };
    module.exports = UrlConfig;
});
