package com.dhcc.urms.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dhcc.urms.role.entity.Role;
import com.dhcc.urms.role.entity.RoleVO;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-17
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("SELECT R.*, D.NAME AS STATUS_NAME " +
                "  FROM T_ROLE R, " +
                "       (SELECT CODE, NAME FROM T_DICT WHERE CLASS = 'STATUS' AND STATUS = 'ACTIVE') D " +
                " WHERE R.STATUS = D.CODE(+) " +
                " ORDER BY R.PRIORITY ")
    IPage<RoleVO> findRole(Page<?> page);
}
