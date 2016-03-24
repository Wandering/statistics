/**
 * Created by kepeng on 15/10/23.
 */
define(function (require, exports, module) {

    require('cookie');
    var token = $.cookie('bizData');
    var baseUrl = '/statistics';
    //var testUrl = 'http://setting.jx.xy189.cn';
    //var testUrl = "10.136.13.245:8080/";
    var testUrl = "172.16.170.127:8080/"; //yyp
    var UrlConfig = {
        /**
         * 统一登录入口
         */
        login: testUrl + '/dist/app/login.html',
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
        getGoodsMange: '/admin/agents?token=' + token

    };
    module.exports = UrlConfig;
});
