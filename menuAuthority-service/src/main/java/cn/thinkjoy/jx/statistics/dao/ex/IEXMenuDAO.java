package cn.thinkjoy.jx.statistics.dao.ex;

import cn.thinkjoy.jx.statistics.pojo.MenuPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yhwang on 15/9/15.
 */
public interface IEXMenuDAO {
    /**
     * 根据岗位code获取菜单树
     * @param parentCode 父菜单编号
     * @param postCode 岗位编号
     * @return 菜单列表
     */
    List<MenuPojo> getMenuPojoListByPost(@Param("parentCode") Long parentCode,@Param("postCode") Long postCode);

    /**
     * 查询所有菜单
     * @param parentCode 父菜单编号
     * @return 菜单列表
     */
    List<MenuPojo> getMenuAllPojoList(@Param("parentCode") Long parentCode);

    /**
     * 按照菜单code修改菜单角色表
     * @param menuCode
     */
    void updateMenuRole(@Param("menuCode")Long menuCode);

    /**
     * 根据角色code获取菜单code列表
     * @param roleCode
     * @return
     */
    List<Long> getMenuCodeByRole(@Param("roleCode")Long roleCode);
}
