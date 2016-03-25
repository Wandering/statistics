/**
 * Created by kepeng on 15/10/8.
 */
define('static/scripts/index/chatUserNum/chatUserNumTable', [], function(require, exports, module) {
    module.exports = function(dateDay) {
        require.async(['static/scripts/index/common/businessTable','static/scripts/index/common/columsCommon'], function(businessTable, tableModel) {
            businessTable({
                dateDay: dateDay,
                url: {
                    areaUrl: '/statistics/chat/queryAreaChatUserNum',
                    schoolUrl: '/statistics/chat/querySchoolChatUserNum',
                    classUrl: '/statistics/chat/queryClassChatUserNum'
                },
                tableModel: tableModel()
            });
        });
    };
});

