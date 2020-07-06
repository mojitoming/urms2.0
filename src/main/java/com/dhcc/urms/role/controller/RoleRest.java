package com.dhcc.urms.role.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dhcc.urms.common.entity.DTreeVO;
import com.dhcc.urms.common.entity.MyPage;
import com.dhcc.urms.role.blh.RoleBLH;
import com.dhcc.urms.role.dto.RoleDTO;
import com.dhcc.urms.role.entity.Role;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/role-api")
public class RoleRest {

    @Resource
    private RoleBLH roleBLH;

    @Resource
    private RoleDTO roleDTO;

    @GetMapping("/roles")
    public MyPage findRole(RoleDTO dto) {
        return roleBLH.findRole(dto);
    }

    @DeleteMapping("/roles")
    public RoleDTO deleteRoles(@RequestBody JSONArray jsonArray) {
        List<Role> roleList = jsonArray.toJavaList(Role.class);
        roleDTO.setRoleList(roleList);
        roleBLH.deleteRoles(roleDTO);

        return roleDTO;
    }

    @PostMapping("/role/modify")
    public RoleDTO addRole(@RequestBody JSONObject jsonObject) {
        Role role = JSON.toJavaObject(jsonObject, Role.class);
        roleDTO.setRole(role);
        roleBLH.addRole(roleDTO);

        return roleDTO;
    }

    @PutMapping("/role/modify")
    public RoleDTO updateRole(@RequestBody JSONObject jsonObject) {
        Role role = JSON.toJavaObject(jsonObject, Role.class);
        roleDTO.setRole(role);
        roleBLH.updateRole(roleDTO);

        return roleDTO;
    }

    @DeleteMapping("/role/modify")
    public RoleDTO deleteRole(@RequestBody JSONObject jsonObject) {
        Role role = JSON.toJavaObject(jsonObject, Role.class);
        roleDTO.setRole(role);
        roleBLH.deleteRole(roleDTO);

        return roleDTO;
    }

    /*
     * Annotation:
     * 角色 - 模块 赋权
     *
     * @Author: Adam Ming
     * @Date: Jun 24, 2020 at 11:14:11 AM
     */
    @PostMapping("/module-grant")
    public RoleDTO roleModuleGrant(@RequestBody JSONArray jsonArray) {
        roleBLH.roleModuleGrant(jsonArray);

        return roleDTO;
    }

    /*
     * Annotation:
     * 角色 - 数据 赋权
     *
     * @Author: Adam Ming
     * @Date: Jul 6, 2020 at 5:21:01 PM
     */
    @PostMapping("/data-grant")
    public RoleDTO roleDataGrant(@RequestBody JSONArray jsonArray){
        roleBLH.roleDataGrant(jsonArray);

        return roleDTO;
    }

    /*
     * Annotation:
     * 角色树
     *
     * @Author: Adam Ming
     * @Date: Jun 30, 2020 at 9:55:35 AM
     */
    @GetMapping("/role-tree")
    public DTreeVO roleTree(RoleDTO dto) {
        roleBLH.roleTree(dto);

        return dto.getdTreeVO();
    }
}
