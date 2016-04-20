package cn.thinkjoy.jx.statistics.util;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.jx.statistics.edomain.ErrorCode;

/**
 * Created by yangguorong on 16/4/14.
 */
public class ModelUtil {

    /**
     * 初始化对象
     *
     * @param domain
     */
    public static void initBuild(CreateBaseDomain domain) {
        domain.setStatus(0);
        domain.setCreator(0l);
        domain.setCreateDate(System.currentTimeMillis());
        domain.setLastModifier(0l);
        domain.setLastModDate(System.currentTimeMillis());
    }


    /**
     * 抛出异常
     *
     * @param errorCode
     */
    public static void throwException(ErrorCode errorCode){
        throw new BizException(errorCode.getCode(),errorCode.getMessage());
    }
}
