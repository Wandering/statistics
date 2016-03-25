/**
 * Created by pdeng on 16/3/23.
 */
define('static/scripts/index/goodsManager/goodsSelect', ['sea-modules/jquery/cookie/jquery.cookie'], function (require, exports, module) {
    /**
     * 货物查询
     */
    require('sea-modules/jquery/cookie/jquery.cookie');
    var systemCode = $.cookie('systemCode');
    var goodsSelectTpl = $('#goodsSelect').html();
    $('#page_0').html(goodsSelectTpl);
    /**
     * 通过选中树节点的ID获取每个节点下的数据表格
     * @param selectedId
     */
    require.async(['static/scripts/index/goodsManager/goodsSelectList'], function (module) {
        module();
    });
});