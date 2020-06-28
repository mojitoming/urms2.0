package com.dhcc.urms.role.blh;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dhcc.urms.common.entity.DictEnum;
import com.dhcc.urms.common.entity.MyPage;
import com.dhcc.urms.role.dto.RoleDTO;
import com.dhcc.urms.role.entity.GrantJsonVO;
import com.dhcc.urms.role.entity.Role;
import com.dhcc.urms.role.entity.RoleVO;
import com.dhcc.urms.role.service.IRoleService;
import com.dhcc.urms.roleprivilege.entity.RolePrivilege;
import com.dhcc.urms.roleprivilege.service.IRolePrivilegeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Annotation:
 * 角色逻辑处理层
 *
 * @Author: Adam Ming
 * @Date: Jun 18, 2020 at 11:37:55 AM
 */
@Component
public class RoleBLH implements Serializable {
    private static final long serialVersionUID = 4475005562181493882L;

    @Resource
    private IRoleService roleService;

    @Resource
    private IRolePrivilegeService rolePrivilegeService;

    /*
     * Annotation:
     * 查找角色信息
     *
     * @Author: Adam Ming
     * @Date: Jun 18, 2020 at 11:37:32 AM
     */
    public MyPage findRole(RoleDTO dto) {
        IPage<RoleVO> iPage = roleService.findRole(dto);

        return new MyPage(iPage.getTotal(), iPage.getRecords());
    }

    /*
     * Annotation:
     * 新增角色
     *
     * @Author: Adam Ming
     * @Date: Jun 22, 2020 at 3:40:43 PM
     */
    public void addRole(RoleDTO dto) {
        Role role = dto.getRole();
        convertStatus(role);

        roleService.save(role);
    }

    /*
     * Annotation:
     * 修改角色
     *
     * @Author: Adam Ming
     * @Date: Jun 22, 2020 at 3:41:31 PM
     */
    public void updateRole(RoleDTO dto) {
        Role role = dto.getRole();
        convertStatus(role);

        roleService.updateById(role);
    }

    /*
     * Annotation:
     * 删除角色
     *
     * @Author: Adam Ming
     * @Date: Jun 22, 2020 at 3:42:20 PM
     */
    public void deleteRole(RoleDTO dto) {
        roleService.removeById(dto.getRole().getRoleId());
    }

    /*
     * Annotation:
     * 批量删除角色
     *
     * @Author: Adam Ming
     * @Date: Jun 22, 2020 at 4:55:15 PM
     */
    public void deleteRoles(RoleDTO dto) {
        List<Long> roleIdList = dto.getRoleList().stream().map(Role::getRoleId).collect(Collectors.toList());
        roleService.removeByIds(roleIdList);
    }

    /**
     * Annotation:
     * 角色 - 模块 授权
     *
     * @Author: Adam Ming
     * @Date: Jun 24, 2020 at 11:37:58 AM
     */
    public void roleModuleGrant(JSONArray jsonArray) {
        List<GrantJsonVO> grantJsonVOList = jsonArray.toJavaList(GrantJsonVO.class);
        if (grantJsonVOList.size() == 0) {
            return;
        }

        List<RolePrivilege> rolePrivilegeList = new ArrayList<>();
        RolePrivilege rolePrivilege;

        for (GrantJsonVO grantJsonVO : grantJsonVOList) {
            rolePrivilege = new RolePrivilege();
            rolePrivilege.setRoleId(Long.parseLong(grantJsonVO.getRoleId()));
            rolePrivilege.setPriviId(grantJsonVO.getNodeId());
            rolePrivilege.setPriviTypeCode("MODULE");

            rolePrivilegeList.add(rolePrivilege);
        }

        // 先删后插 ---> 根据 roleId 删除所有 关联的 module，然后再插入上面构造的 List
        Long roleId = rolePrivilegeList.get(0).getRoleId();
        QueryWrapper<RolePrivilege> qw = new QueryWrapper<>();
        qw.eq("ROLE_ID", roleId);
        rolePrivilegeService.remove(qw);

        rolePrivilegeService.saveBatch(rolePrivilegeList);
    }

    // =================== 私有方法分割线 ===================

    /*
     * Annotation:
     * Role Status 状态转变
     *
     * @Author: Adam Ming
     * @Date: Jun 22, 2020 at 5:33:29 PM
     */
    private void convertStatus(Role role) {
        switch (role.getStatus()) {
            case "on":
                role.setStatus(DictEnum.STATUS_ACTIVE.getValue());

                break;
            case "off":
                role.setStatus(DictEnum.STATUS_INACTIVE.getValue());

                break;
        }
    }
}
