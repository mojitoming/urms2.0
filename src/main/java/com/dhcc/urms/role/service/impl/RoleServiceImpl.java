package com.dhcc.urms.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhcc.urms.common.entity.PageModel;
import com.dhcc.urms.role.dto.RoleDTO;
import com.dhcc.urms.role.entity.Role;
import com.dhcc.urms.role.entity.RoleVO;
import com.dhcc.urms.role.mapper.RoleMapper;
import com.dhcc.urms.role.service.IRoleService;
import com.dhcc.urms.roleprivilege.entity.RolePrivilege;
import com.dhcc.urms.roleprivilege.mapper.RolePrivilegeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private RolePrivilegeMapper rolePrivilegeMapper;

    @Override
    public IPage<RoleVO> findRole(RoleDTO dto) {
        PageModel pageModel = dto.getPageModel();
        Page<RoleVO> page = new Page<>(pageModel.getPageNo(), pageModel.getPageSize());

        return roleMapper.findRole(page);
    }

    @Transactional
    @Override
    public void deleteRole(RoleDTO dto) {
        long roleId = dto.getRole().getRoleId();
        // 删除角色
        roleMapper.deleteById(roleId);

        // 删除角色关联的授权
        QueryWrapper<RolePrivilege> qw = new QueryWrapper<>();
        qw.eq("ROLE_ID", roleId);
        rolePrivilegeMapper.delete(qw);
    }

    @Transactional
    @Override
    public void deleteRoles(RoleDTO dto) {
        List<Long> roleIdList = dto.getRoleList().stream().map(Role::getRoleId).collect(Collectors.toList());

        // 删除角色
        roleMapper.deleteBatchIds(roleIdList);

        // 删除角色关联的授权
        QueryWrapper<RolePrivilege> qw = new QueryWrapper<>();
        qw.in("ROLE_ID", roleIdList);
        rolePrivilegeMapper.delete(qw);
    }
}
