package com.dhcc.urms.role.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhcc.urms.common.entity.PageModel;
import com.dhcc.urms.role.dto.RoleDTO;
import com.dhcc.urms.role.entity.Role;
import com.dhcc.urms.role.entity.RoleVO;
import com.dhcc.urms.role.mapper.RoleMapper;
import com.dhcc.urms.role.service.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-17
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public IPage<RoleVO> findRole(RoleDTO dto) {
        PageModel pageModel = dto.getPageModel();
        Page<RoleVO> page = new Page<>(pageModel.getPageNo(), pageModel.getPageSize());

        return roleMapper.findRole(page);
    }
}
