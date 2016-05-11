/**
 * Created by kepeng on 15/9/21.
 */
define(function (require, exports, module) {

    var ErrorDialog = require('../message');
    require('cookie');
    var token = $.cookie('bizData');

    var AjaxController = {
        get: function (url, callback) {
            var that = this;
            if (url.indexOf('?') > -1) {
                url = url + '&token=' + token;
            } else {
                url = url + '?token=' + token;
            }

            $.get(url, function (data) {
                if ('0000000' === data.rtnCode || '0100010' === data.rtnCode) {
                    typeof callback === 'function' && callback(data);
                } else {
                    that.error(data);
                }
            });
        },
        post: function (url, data, callback) {
            url = url + '?token=' + token;
            $.ajax({
                type: 'post',
                url: url,
                contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                data: data,
                dataType: 'json',
                success: function (data) {
                    if ('0000000' === data.rtnCode) {
                        typeof callback === 'function' && callback(data);
                    } else {
                        that.error(data);
                    }
                }
            });
        },
        error: function (data) {
            if ('0100015' === data.rtnCode) {
                ErrorDialog({
                    msg: data.msg
                });
            } else if ('0100008' === data.rtnCode) {
                ErrorDialog({
                    msg: data.msg,
                    clickHandle: function () {
                        window.location.href = 'http://agent.zhigaokao.cn/dist/app/login.html';
                    }
                });
                $('.modal-header .close').off('click');
            }
        }
    };

    module.exports = AjaxController;

});
