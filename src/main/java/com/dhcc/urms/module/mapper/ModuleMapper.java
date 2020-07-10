package com.dhcc.urms.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dhcc.urms.module.dto.ModuleDTO;
import com.dhcc.urms.module.entity.Module;
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

    @Select("DELETE " +
                "  FROM T_MODULE " +
                " WHERE MODULE_ID IN (SELECT MODULE_ID " +
                "                       FROM T_MODULE " +
                "                      START WITH MODULE_ID = #{moduleId} " +
                "                    CONNECT BY PARENT_ID = PRIOR MODULE_ID)")
    void deleteModule(Module module);

    @Select("select * " +
                "   from t_module " +
                "  start with MODULE_ID = #{moduleId} " +
                "connect by prior PARENT_ID = MODULE_ID ")
    List<Module> findParents(ModuleDTO dto);
}
