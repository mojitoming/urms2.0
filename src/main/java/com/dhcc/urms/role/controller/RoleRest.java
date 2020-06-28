package com.dhcc.urms.role.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
    private RoleDTO dto;

    @GetMapping("/roles")
    public MyPage findRole(RoleDTO dto) {
        return roleBLH.findRole(dto);
    }

    @DeleteMapping("/roles")
    public RoleDTO deleteItems(@RequestBody JSONArray jsonArray) {
        List<Role> roleList = jsonArray.toJavaList(Role.class);
        dto.setRoleList(roleList);
        roleBLH.deleteRoles(dto);

        return dto;
    }

    @PostMapping("/role/modify")
    public RoleDTO addRole(@RequestBody JSONObject jsonObject) {
        Role role = JSON.toJavaObject(jsonObject, Role.class);
        dto.setRole(role);
        roleBLH.addRole(dto);

        return dto;
    }

    @PutMapping("/role/modify")
    public RoleDTO updateRole(@RequestBody JSONObject jsonObject) {
        Role role = JSON.toJavaObject(jsonObject, Role.class);
        dto.setRole(role);
        roleBLH.updateRole(dto);

        return dto;
    }

    @DeleteMapping("/role/modify")
    public RoleDTO deleteRole(@RequestBody JSONObject jsonObject) {
        Role role = JSON.toJavaObject(jsonObject, Role.class);
        dto.setRole(role);
        roleBLH.deleteRole(dto);

        return dto;
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

        return dto;
    }
}
