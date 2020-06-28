package com.dhcc.urms.module.service.impl;

import com.dhcc.urms.module.dto.ModuleDTO;
import com.dhcc.urms.module.entity.Module;
import com.dhcc.urms.roleprivilege.entity.RolePrivilege;
import com.dhcc.urms.module.mapper.ModuleMapper;
import com.dhcc.urms.module.service.IModuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 模块表 服务实现类
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-23
 */
@Service("moduleService")
public class ModuleServiceImpl extends ServiceImpl<ModuleMapper, Module> implements IModuleService {

    @Resource
    private ModuleMapper moduleMapper;

    @Override
    public List<RolePrivilege> findRolePrivilege(ModuleDTO dto) {
        return moduleMapper.findRolePrivilege(dto);
    }
}
