package com.dhcc.urms.userrole.service.impl;

import com.dhcc.urms.userrole.entity.UserRole;
import com.dhcc.urms.userrole.mapper.UserRoleMapper;
import com.dhcc.urms.userrole.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色 映射表 服务实现类
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-30
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
