package com.dhcc.urms.module.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dhcc.urms.module.dto.ModuleDTO;
import com.dhcc.urms.module.entity.Module;

import java.util.List;

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

    /*
     * Annotation:
     * 根据 moduleId 查找父亲节点
     * 包含自身
     *
     * @Author: Adam Ming
     * @Date: Jul 10, 2020 at 10:55:32 AM
     */
    List<Module> findParents(ModuleDTO dto);
}
