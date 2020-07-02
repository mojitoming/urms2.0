package com.dhcc.urms.module.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dhcc.urms.module.dto.ModuleDTO;
import com.dhcc.urms.module.entity.Module;

/**
 * <p>
 * 模块表 服务类
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-23
 */
public interface IModuleService extends IService<Module> {
    /*
     * Annotation:
     * 删除模块
     * 删除模块及子模块
     *
     * @Author: Adam Ming
     * @Date: Jul 2, 2020 at 6:03:24 PM
     */
    void deleteModule(ModuleDTO dto);
}
