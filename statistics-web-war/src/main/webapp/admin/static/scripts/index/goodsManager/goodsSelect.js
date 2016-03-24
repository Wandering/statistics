/**
 * Created by pdeng on 16/3/23.
 */
define(function (require, exports, module) {
    /**
     * 货物查询
     */
    require('cookie');
    var systemCode = $.cookie('systemCode');
    var UrlConfig = require('../common/urlConfig');
   // alert('货物查询');
    var goodsSelectTpl = $('#goodsSelect').html();
    $('#page_0').html(goodsSelectTpl);


    /**
     * 通过选中树节点的ID获取每个节点下的数据表格
     * @param selectedId
     */
    require.async('./goodsSelectList.js', function (module) {
        module();
    });

    $.get(UrlConfig.getGoodsMange + '&systemCode=' + systemCode, function (data) {
        console.info(data);
        //var dataJson = {
        //    "bizData": {
        //        "conditions": {},
        //        "page": 1,
        //        "pagesize": 10,
        //        "records": 5,
        //        "rows": [
        //            {
        //                "id": 49197,
        //                "inputDate": 0,
        //                "goodsNumber": "61",
        //                "goodsStatus": "未出库",
        //                "createDate": 0,
        //                "cardNumber": "GK60349295"
        //            },
        //            {
        //                "id": 49196,
        //                "inputDate": 0,
        //                "goodsNumber": "61",
        //                "goodsStatus": "未出库",
        //                "createDate": 0,
        //                "cardNumber": "GK60349294"
        //            }
        //        ],
        //        "total": 1
        //    },
        //    "rtnCode": "0000000",
        //    "ts": 1458720546578
        //};
    });
});