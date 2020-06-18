package com.dhcc.urms.role.service.impl;

import com.dhcc.urms.role.entity.Role;
import com.dhcc.urms.role.mapper.RoleMapper;
import com.dhcc.urms.role.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-17
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
