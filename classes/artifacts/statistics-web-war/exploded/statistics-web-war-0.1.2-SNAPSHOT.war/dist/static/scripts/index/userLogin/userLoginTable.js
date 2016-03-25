/**
 * Created by kepeng on 15/10/8.
 */
define('static/scripts/index/userLogin/userLoginTable', [], function(require, exports, module) {
    module.exports = function(dateDay) {
        require.async(['static/scripts/index/common/businessTable','static/scripts/index/common/columsCommon'], function(businessTable, tableModel) {
            businessTable({
                dateDay: dateDay,
                url: {
                    areaUrl: '/statistics/userLogin/queryAreaLoginUserNum',
                    schoolUrl: '/statistics/userLogin/querySchoolLoginUserNum',
                    classUrl: '/statistics/userLogin/queryClassLoginUserNum'
                },
                tableModel: tableModel()
            });
        });
    };
});