/**
 * Created by kepeng on 15/10/8.
 */
define(function(require, exports, module) {

    module.exports = function(dateDay) {
        require.async(['../common/businessTable', '../common/columsCommon'], function(businessTable, tableModel) {
            businessTable({
                dateDay: dateDay,
                url: {
                    areaUrl: '/statistics/chat/queryAreaUserChatNum',
                    schoolUrl: '/statistics/chat/querySchoolUserChatNum',
                    classUrl: '/statistics/chat/queryClassUserChatNum'
                },
                tableModel: tableModel()
            });
        });
    };
});

