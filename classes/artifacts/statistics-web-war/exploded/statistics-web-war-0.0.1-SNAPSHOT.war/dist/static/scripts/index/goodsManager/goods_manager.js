/**
 * Created by pdeng on 16/3/23.
 */
define('static/scripts/index/goodsManager/goods_manager', ['sea-modules/jquery/cookie/jquery.cookie'], function(require, exports, module) {

    module.exports = function() {
        require('sea-modules/jquery/cookie/jquery.cookie');
        var token = $.cookie('bizData');
        /**
         * 货物查询
         */
        alert(1);
        alert(token);
    };
});