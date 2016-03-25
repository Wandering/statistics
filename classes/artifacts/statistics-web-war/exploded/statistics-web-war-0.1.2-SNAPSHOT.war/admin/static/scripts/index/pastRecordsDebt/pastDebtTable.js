/**
 * Created by kepeng on 15/9/18.
 */
define(function(require, exports, module) {

    module.exports = function(dateDay) {
        require.async(['../common/businessTable', '../common/columsCommon1'], function(businessTable, tableModel) {
            businessTable({
                dateDay: dateDay,
                url: {
                    areaUrl: '/statistics/billing/queryAreaHistoryBillingNum',
                    schoolUrl: '/statistics/billing/querySchoolHistoryBillingNum',
                    classUrl: '/statistics/billing/queryClassHistoryBillingNum'
                },
                tableModel: tableModel()
            });
        });
    };
});