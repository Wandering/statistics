/**
 * Created by kepeng on 15/10/8.
 */
define('static/scripts/index/webuserLoginNum/webuserLoginNumTable', [], function(require, exports, module) {
    module.exports = function(dateDay) {
        require.async(['static/scripts/index/common/businessTable','static/scripts/index/common/columsCommon'], function(businessTable, tableModel) {
            businessTable({
                dateDay: dateDay,
                url: {
                    areaUrl: '/statistics/webUserLogin/queryAreaUserLoginNum',
                    schoolUrl: '/statistics/webUserLogin/querySchoolUserLoginNum',
                    classUrl: '/statistics/webUserLogin/queryClassUserLoginNum'
                },
                tableModel: tableModel()
            });
        });
    };
});

