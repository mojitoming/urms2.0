package com.dhcc.urms.role.blh;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dhcc.urms.common.entity.DTreeNodeVO;
import com.dhcc.urms.common.entity.DTreeVO;
import com.dhcc.urms.common.entity.DictEnum;
import com.dhcc.urms.common.entity.MyPage;
import com.dhcc.urms.role.dto.RoleDTO;
import com.dhcc.urms.common.entity.GrantJsonVO;
import com.dhcc.urms.role.entity.Role;
import com.dhcc.urms.role.entity.RoleVO;
import com.dhcc.urms.role.service.IRoleService;
import com.dhcc.urms.roleprivilege.entity.RolePrivilege;
import com.dhcc.urms.roleprivilege.service.IRolePrivilegeService;
import com.dhcc.urms.userrole.entity.UserRole;
import com.dhcc.urms.userrole.service.IUserRoleService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Resource
    private IUserRoleService userRoleService;

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
        roleService.deleteRole(dto);
    }

    /*
     * Annotation:
     * 批量删除角色
     *
     * @Author: Adam Ming
     * @Date: Jun 22, 2020 at 4:55:15 PM
     */
    public void deleteRoles(RoleDTO dto) {
        roleService.deleteRoles(dto);
    }

    /*
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

        String priviIdTemp;
        for (GrantJsonVO grantJsonVO : grantJsonVOList) {
            rolePrivilege = new RolePrivilege();
            rolePrivilege.setRoleId(Long.parseLong(grantJsonVO.getRoleId()));

            priviIdTemp = grantJsonVO.getNodeId();
            if ("0".equals(priviIdTemp)) { // 过滤掉树根
                continue;
            }

            rolePrivilege.setPriviId(priviIdTemp);
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

    /*
     * Annotation:
     * 获取角色树
     *
     * @Author: Adam Ming
     * @Date: Jun 30, 2020 at 9:56:24 AM
     */
    public void roleTree(RoleDTO dto) {
        QueryWrapper<Role> qwR = new QueryWrapper<>();
        qwR.eq("STATUS", DictEnum.STATUS_ACTIVE.getValue());
        qwR.orderByAsc("PRIORITY");
        List<Role> roleList = roleService.list(qwR);

        // 根据 userId 获取 user-role 对应关系，转为 map
        QueryWrapper<UserRole> qwUR = new QueryWrapper<>();
        qwUR.eq("USER_ID", dto.getUserId());
        List<UserRole> userRoleList = userRoleService.list(qwUR);
        Map<Long, String> roleMap = userRoleList.stream().collect(Collectors.toMap(UserRole::getRoleId, e -> "1"));

        // 添加一个 root 节点
        String nickname = dto.getNickname();
        String rootTitle = "角色树";
        rootTitle = StringUtils.isEmpty(nickname) ? rootTitle : rootTitle + "-" + nickname;

        DTreeVO dTreeVO = new DTreeVO();
        DTreeNodeVO dTreeNodeVO = new DTreeNodeVO();
        dTreeNodeVO.setId("0");
        dTreeNodeVO.setTitle(rootTitle);
        dTreeVO.getData().add(dTreeNodeVO);

        String isCheck;
        for (Role role : roleList) {
            dTreeNodeVO = new DTreeNodeVO();
            dTreeNodeVO.setId(role.getRoleId().toString());
            dTreeNodeVO.setTitle(role.getRoleName());
            dTreeNodeVO.setParentId("0");

            isCheck = roleMap.get(role.getRoleId()) == null ? "0" : "1";
            dTreeNodeVO.setCheckArr(isCheck);

            dTreeVO.getData().add(dTreeNodeVO);
        }

        dto.setdTreeVO(dTreeVO);
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
        if ("on".equals(role.getStatus())) {
            role.setStatus(DictEnum.STATUS_ACTIVE.getValue());
        } else {
            role.setStatus(DictEnum.STATUS_INACTIVE.getValue());
        }
    }
}
