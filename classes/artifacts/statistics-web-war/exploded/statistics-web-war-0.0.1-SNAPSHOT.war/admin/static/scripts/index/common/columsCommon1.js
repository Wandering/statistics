/**
 * Created by kepeng on 15/10/10.
 */

define(function(require, exports, module) {
    module.exports = function() {
        return {
            areaColumn: [{
                data: 'areaId',
                title: ''
            }, {
                data: 'areaName',
                title: '区域'
            }, {
                data: 'num',
                title: '人数'
            }],
            schoolColumn: [{
                data: 'schoolId',
                title: ''
            }, {
                data: 'schoolName',
                title: '学校'
            }, {
                data: 'num',
                title: '人数'
            }],
            classColumn: [{
                data: 'classId',
                title: ''
            }, {
                data: 'className',
                title: '班级'
            }, {
                data: 'num',
                title: '人数'
            }],
            columnDefs: [{
                "bVisible": false,
                "aTargets": [0]
            }]
        }
    }
});
