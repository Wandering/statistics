/**
 * Created by kepeng on 15/9/18.
 */
define('static/scripts/index/userSendNoticeNum/sendNoticeUserNumTable', [], function(require, exports, module) {

    module.exports = function(dateDay) {
        require.async(['static/scripts/index/common/businessTable','static/scripts/index/common/columsCommon1'], function(businessTable, tableModel) {
            businessTable({
                dateDay: dateDay,
                url: {
                    areaUrl: '/statistics/notice/queryAreaUserSendNoticeNum',
                    schoolUrl: '/statistics/notice/querySchoolUserSendNoticeNum',
                    classUrl: '/statistics/notice/queryClassUserSendNoticeNum'
                },
                tableModel: tableModel()
            });
        });
    };
});
