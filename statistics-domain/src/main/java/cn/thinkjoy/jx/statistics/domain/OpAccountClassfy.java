package cn.thinkjoy.jx.statistics.domain;

import java.util.List;

/**
 * Created by wpliu on 15/10/13.
 */
public class OpAccountClassfy {
    private long id;

    private String name;

    private List<OpAccount> children;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OpAccount> getChildren() {
        return children;
    }

    public void setChildren(List<OpAccount> children) {
        this.children = children;
    }
}
