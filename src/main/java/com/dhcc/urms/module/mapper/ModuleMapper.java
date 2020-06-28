package com.dhcc.urms.module.mapper;

import com.dhcc.urms.module.dto.ModuleDTO;
import com.dhcc.urms.module.entity.Module;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dhcc.urms.roleprivilege.entity.RolePrivilege;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 模块表 Mapper 接口
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-23
 */
public interface ModuleMapper extends BaseMapper<Module> {

    @Select("select * from t_role_privilege where role_id = #{roleId}")
    List<RolePrivilege> findRolePrivilege(ModuleDTO dto);
}
