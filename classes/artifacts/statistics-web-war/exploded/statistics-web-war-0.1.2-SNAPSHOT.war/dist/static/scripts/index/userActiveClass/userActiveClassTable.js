/**
 * Created by kepeng on 15/10/8.
 */
define('static/scripts/index/userActiveClass/userActiveClassTable', [], function(require, exports, module) {
    module.exports = function(dateDay) {
        require.async(['static/scripts/index/common/businessTable','static/scripts/index/common/columsCommon'], function(businessTable, tableModel) {
            businessTable({
                dateDay: dateDay,
                url: {
                    areaUrl: '/statistics/share/queryAreaShareUserNum',
                    schoolUrl: '/statistics/share/querySchoolShareUserNum',
                    classUrl: '/statistics/share/queryClassShareUserNum'
                },
                tableModel: tableModel()
            });
        });
    };
});

