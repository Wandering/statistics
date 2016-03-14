package cn.thinkjoy.jx.statistics.common;

import cn.thinkjoy.common.protocol.RequestT;
import cn.thinkjoy.common.restful.ITypeReference;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 支持泛型，这里把所有的 request的泛型参数在这里做记录    （因为spring requestbody处理会丢掉泛型信息）
 * <p/>
 * 创建时间: 14/11/30 上午11:08<br/>
 *
 * @author qyang
 * @since v0.0.1
 * /
 */
public class TypeReferenceMaps implements ITypeReference {

    private Map<String, TypeReference> typeReferenceMaps = Maps.newHashMap();

    public void init() {
        typeReferenceMaps.put("/app/insertAppActionStatistics.do", new TypeReference<RequestT<Map<String,Object>>>() {});
    }

    @Override
    public TypeReference getTypeReference(String url) {
        return typeReferenceMaps.get(url);
    }

}
