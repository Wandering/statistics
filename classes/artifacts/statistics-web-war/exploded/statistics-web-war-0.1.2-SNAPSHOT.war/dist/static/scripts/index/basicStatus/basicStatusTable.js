/**
 * Created by shixin on 15/12/21.
 */
define('static/scripts/index/basicStatus/basicStatusTable', [], function(require, exports, module) {
    module.exports = function(dateDay) {
        require.async(['static/scripts/index/common/businessTable','static/scripts/index/common/columsCommon'], function(businessTable, tableModel) {
            businessTable({
                dateDay: dateDay,
                url: {
                    areaUrl: '/statistics/increaseUser/queryAreaIncreaseUser',
                    schoolUrl: '/statistics/increaseUser/querySchoolIncreaseUser',
                    classUrl: '/statistics/increaseUser/queryClassIncreaseUser',
                    detailUrl: '/statistics/increaseUser/queryTeacherIncreaseUserDetail',
                    parentUrl:'/statistics/increaseUser/queryParentIncreaseUserDetail',
                    teacherUrl:'/statistics/increaseUser/queryTeacherIncreaseUserDetail'
                },
                showType:2,
                tableModel: tableModel()
            });
        });
    };
});