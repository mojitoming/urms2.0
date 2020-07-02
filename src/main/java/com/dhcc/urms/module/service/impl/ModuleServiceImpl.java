package com.dhcc.urms.module.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhcc.urms.module.dto.ModuleDTO;
import com.dhcc.urms.module.entity.Module;
import com.dhcc.urms.module.mapper.ModuleMapper;
import com.dhcc.urms.module.service.IModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public void deleteModule(ModuleDTO dto) {
        // 删除此节点及其叶子结点
        Module module = dto.getModule();
        moduleMapper.deleteModule(module);
    }
}
