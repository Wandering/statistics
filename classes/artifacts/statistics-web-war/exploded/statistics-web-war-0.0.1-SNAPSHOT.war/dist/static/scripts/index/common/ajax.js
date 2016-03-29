/**
 * Created by kepeng on 15/9/21.
 */
define('static/scripts/index/common/ajax', ['static/scripts/index/message', 'sea-modules/jquery/cookie/jquery.cookie'], function (require, exports, module) {

    var ErrorDialog = require('static/scripts/index/message');
    require('sea-modules/jquery/cookie/jquery.cookie');
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
                        window.location.href = 'http://setting.jx.xy189.cn/dist/app/login.html';
                    }
                });
                $('.modal-header .close').off('click');
            }
        }
    };

    module.exports = AjaxController;

});
