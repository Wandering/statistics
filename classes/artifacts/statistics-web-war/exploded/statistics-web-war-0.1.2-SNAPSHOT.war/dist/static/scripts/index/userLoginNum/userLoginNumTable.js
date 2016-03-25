/**
 * Created by kepeng on 15/10/8.
 */
define('static/scripts/index/userLoginNum/userLoginNumTable', [], function(require, exports, module) {
    module.exports = function(dateDay) {
        require.async(['static/scripts/index/common/businessTable','static/scripts/index/common/columsCommon'], function(businessTable, tableModel) {
            businessTable({
                dateDay: dateDay,
                url: {
                    areaUrl: '/statistics/userLogin/queryAreaUserLoginNum',
                    schoolUrl: '/statistics/userLogin/querySchoolUserLoginNum',
                    classUrl: '/statistics/userLogin/queryClassUserLoginNum'
                },
                tableModel: tableModel()
            });
        });
    };
});

