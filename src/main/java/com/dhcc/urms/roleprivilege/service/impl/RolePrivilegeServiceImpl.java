package com.dhcc.urms.roleprivilege.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhcc.urms.roleprivilege.entity.RolePrivilege;
import com.dhcc.urms.roleprivilege.mapper.RolePrivilegeMapper;
import com.dhcc.urms.roleprivilege.service.IRolePrivilegeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色-权限 映射表 服务实现类
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-24
 */
@Service("rolePrivilegeService")
public class RolePrivilegeServiceImpl extends ServiceImpl<RolePrivilegeMapper, RolePrivilege> implements IRolePrivilegeService {

}