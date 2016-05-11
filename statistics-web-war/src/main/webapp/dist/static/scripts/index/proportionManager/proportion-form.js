/**
 * Created by kepeng on 15/9/9.
 */
define('static/scripts/index/proportionManager/proportion-form', ['static/scripts/index/tools'], function (require, exports, module) {
    var Tool = require('static/scripts/index/tools');


    function tip(ele, str) {
        var errorLable = ele.find('p');
        errorLable.html(str);
        errorLable.show(500);
        setTimeout(function () {
            errorLable.hide(500);
        }, 2000)
    }

    if (typeof String.prototype.trim !== 'function') {
        String.prototype.trim = function () {
            return this.replace(/(^\s*)|(\s*$)/g, '');
        };
    }
    //验证用户账号是否存在
    var token = $.cookie('bizData');


    function validateForm(callback) {

        var proportionFirst = $.trim($('#proportion-first').val());
        if (proportionFirst.length > 3 || isNaN(proportionFirst)) {
            tip($('#proportion-first').parent().parent(), '一级奖励比例格式错误');
            return;
        }


        var proportionSecond = $.trim($('#proportion-second').val());
        if (proportionSecond.length > 3 || isNaN(proportionSecond)) {
            tip($('#proportion-second').parent().parent(), '二级奖励比例格式错误');
            return;
        }

        if(parseInt(proportionFirst) + parseInt(proportionSecond) >= 100){
            tip($('#proportion-second').parent().parent(), '输入分成比例总和必须小于100');
            return;
        }


        callback(
            [
                proportionFirst,    // 一级奖励比例
                proportionSecond    // 二级奖励比例
            ]);
    }

    module.exports = {
        validate: function (callback) {
            validateForm(callback);
        }
    }
});