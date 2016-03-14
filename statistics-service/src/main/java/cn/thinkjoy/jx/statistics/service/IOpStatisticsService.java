package cn.thinkjoy.jx.statistics.service;

import cn.thinkjoy.jx.statistics.domain.OpAccount;

import java.util.List;
import java.util.Map;

/**
 * Created by wpliu on 15/10/13.
 */
public interface IOpStatisticsService {
    List<Map<String,Object>> getClassfyInfo();

    List<OpAccount> getAccountInfoByClassfyId(Long classfyId);

    List<Map<String,Object>> getAccountFans(Long userId, String dateStart, String dateEnd);

    List<Map<String,Object>> getAccountArticles(Long userId, String dateStart, String dateEnd);

    List<Map<String,Object>> getAccountEvents(Long userId, String dateStart, String dateEnd);
}
