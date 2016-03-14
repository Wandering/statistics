/**
 * Created by kepeng on 15/10/8.
 */
define('static/scripts/index/webuserLogin/webuserLoginTable', [], function(require, exports, module) {
    module.exports = function(dateDay) {
        require.async(['static/scripts/index/common/businessTable','static/scripts/index/common/columsCommon'], function(businessTable, tableModel) {
            businessTable({
                dateDay: dateDay,
                url: {
                    areaUrl: '/statistics/webUserLogin/queryAreaLoginUserNum',
                    schoolUrl: '/statistics/webUserLogin/querySchoolLoginUserNum',
                    classUrl: '/statistics/webUserLogin/queryClassLoginUserNum'
                },
                tableModel: tableModel()
            });
        });
    };
});