/**
 * Created by kepeng on 15/10/10.
 */

define('static/scripts/index/common/columsCommon', [], function(require, exports, module) {
    module.exports = function() {
        return {
            areaColumn: [{
                data: 'areaId',
                title: ''
            }, {
                data: 'areaName',
                title: '区域'
            }, {
                data: 'teacherNum',
                title: '教师'
            }, {
                data: 'parentNum',
                title: '家长'
            }],
            schoolColumn: [{
                data: 'schoolId',
                title: ''
            }, {
                data: 'schoolName',
                title: '学校'
            }, {
                data: 'teacherNum',
                title: '教师'
            }, {
                data: 'parentNum',
                title: '家长'
            }],
            classColumn: [{
                data: 'classId',
                title: ''
            }, {
                data: 'className',
                title: '班级'
            }, {
                data: 'teacherNum',
                title: '教师'
            }, {
                data: 'parentNum',
                title: '家长'
            }],
            parentColumn: [{
                data: 'parentId',
                title: ''
            }, {
                data: 'areaName',
                title: '区域'
            }, {
                data: 'schoolName',
                title: '学校'
            }, {
                data: 'className',
                title: '班级'
            }, {
                data: 'loginNumber',
                title: '登录账号'
            }, {
                data: 'parentId',
                title: '家长ID'
            }, {
                data: 'parentName',
                title: '家长姓名'
            }, {
                data: 'childName',
                title: '孩子'
            }, {
                data: 'studentId',
                title: '孩子ID'
            }],
            teacherColumn: [{
                data: 'teacherId',
                title: ''
            }, {
                data: 'areaName',
                title: '区域'
            }, {
                data: 'schoolName',
                title: '学校'
            }, {
                data: 'className',
                title: '班级'
            }, {
                data: 'loginNumber',
                title: '登录账号'
            }, {
                data: 'teacherId',
                title: '教师ID'
            }, {
                data: 'teacherName',
                title: '教师姓名'
            }, {
                data: 'createDate',
                title: '创建时间'
            }],
            columnDefs: [{
                "bVisible": false,
                "aTargets": [0]
            }]
        }
    }
});
