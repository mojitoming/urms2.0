package com.dhcc.urms.role.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dhcc.urms.role.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-17
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select  from T_ROLE")
    IPage<Role> findRole(Page<?> page);
}
