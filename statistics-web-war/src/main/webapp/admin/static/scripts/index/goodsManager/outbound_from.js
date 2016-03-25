/**
 * Created by kepeng on 15/9/9.
 */
define(function(require, exports, module) {
    function tip(ele, str) {
        var errorLable = ele.find('p');
        errorLable.html(str);
        errorLable.show(500);
        setTimeout(function() {
            errorLable.hide(500);
        }, 2000)
    }

    if (typeof String.prototype.trim !== 'function') {
        String.prototype.trim = function() {
            return this.replace(/(^\s*)|(\s*$)/g,'');
        };
    }

    function validateForm(callback) {


        var  areaV = $('#dep_provinces').find('option:selected').val();
        var  simpleCode = $('#dep_provinces').find('option:selected').attr('simpleCode');

        if (areaV=="") {
            tip($('#dep_provinces').parent().parent(), '请选择流向地');
            return;
        }
        callback([simpleCode]);
    }


    module.exports = {
        validate: function(callback) {
            validateForm(callback);
        }
    }
});