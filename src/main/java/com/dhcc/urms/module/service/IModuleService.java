package com.dhcc.urms.module.service;

import com.dhcc.urms.module.dto.ModuleDTO;
import com.dhcc.urms.module.entity.Module;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dhcc.urms.roleprivilege.entity.RolePrivilege;

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
     * 查找 角色 —— 权限对应
     *
     * @Author: Adam Ming
     * @Date: Jun 23, 2020 at 3:22:45 PM
     */
    List<RolePrivilege> findRolePrivilege(ModuleDTO dto);
}
