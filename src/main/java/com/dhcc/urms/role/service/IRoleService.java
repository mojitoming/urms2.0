package com.dhcc.urms.role.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dhcc.urms.role.dto.RoleDTO;
import com.dhcc.urms.role.entity.Role;
import com.dhcc.urms.role.entity.RoleVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-17
 */
public interface IRoleService extends IService<Role> {
    /*
     * Annotation:
     * 查找角色信息
     *
     * @Author: Adam Ming
     * @Date: Jun 18, 2020 at 11:24:53 AM
     */
    IPage<RoleVO> findRole(RoleDTO dto);
}
