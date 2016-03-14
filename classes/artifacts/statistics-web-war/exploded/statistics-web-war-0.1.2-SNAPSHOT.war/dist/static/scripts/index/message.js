/**
 * Created by kepeng on 15/9/11.
 */
define('static/scripts/index/message', ['sea-modules/jquery/dialog/jquery.dialog'], function(require, exports, module) {

    require('sea-modules/jquery/dialog/jquery.dialog');

    var ErrorDialog = {
        clickHandle: null,
        messageId: null,
        buttons: [{
            text: "确认",
            'class': "btn btn-default",
            click: function() {
                ErrorDialog.destroy();
                if (ErrorDialog.clickHandle && typeof ErrorDialog.clickHandle === 'function') {
                    ErrorDialog.clickHandle();
                }
            }
        }],
        destroy: function() {
            $('#' + this.messageId).dialog("destroy");
        },
        init: function(options) {
            this.messageId = 'message_' + (+new Date());
            this.clickHandle = options.clickHandle || null;
            if ('confirm' === options.type) {
                this.buttons.push({
                    text: "取消",
                    'class': "btn btn-error",
                    click: function() {
                        ErrorDialog.destroy();
                    }
                });
            }
            $('#' + this.messageId).dialog({
                title: options.title || '温馨提示',
                tmpl: '<div id="' + this.messageId + '">' + options.msg + '</div>',
                onClose: function() {
                    ErrorDialog.destroy();
                },
                buttons: this.buttons
            });
        }
    }

    module.exports = function(options) {
        ErrorDialog.init(options);
    };
});
