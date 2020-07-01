package com.dhcc.urms.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dhcc.urms.common.entity.MyPage;
import com.dhcc.urms.user.blh.UserBLH;
import com.dhcc.urms.user.dto.UserDTO;
import com.dhcc.urms.user.entity.User;
import com.dhcc.urms.user.entity.UserVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Annotation:
 * 用户 Rest API
 *
 * @Author: Adam Ming
 * @Date: Jun 28, 2020 at 10:49:14 AM
 */
@RestController
@RequestMapping("/user-api")
public class UserRest {

    @Resource
    private UserBLH userBLH;

    @Resource
    private UserDTO userDTO;

    @GetMapping("/users")
    public MyPage findUser(UserDTO dto) {
        return userBLH.findUser(dto);
    }

    @DeleteMapping("/users")
    public UserDTO deleteUsers(@RequestBody JSONArray jsonArray) {
        List<User> userList = jsonArray.toJavaList(User.class);
        userDTO.setUserList(userList);
        userBLH.deleteUsers(userDTO);

        return userDTO;
    }

    @PostMapping("/user/modify")
    public UserDTO addUser(@RequestBody JSONObject jsonObject) {
        User user = JSON.toJavaObject(jsonObject, User.class);
        userDTO.setUser(user);
        userBLH.addUser(userDTO);

        return userDTO;
    }

    @PutMapping("/user/modify")
    public UserDTO updateUser(@RequestBody JSONObject jsonObject) {
        UserVO userVO = JSON.toJavaObject(jsonObject, UserVO.class);
        userDTO.setUserVO(userVO);
        userBLH.updateUser(userDTO);

        return userDTO;
    }

    @DeleteMapping("/user/modify")
    public UserDTO deleteUser(@RequestBody JSONObject jsonObject) {
        User user = JSON.toJavaObject(jsonObject, User.class);
        userDTO.setUser(user);
        userBLH.deleteUser(userDTO);

        return userDTO;
    }

    @GetMapping("/username")
    public UserDTO findUserCountByUsername(UserDTO dto) {
        userBLH.findUserCountByUsername(dto);

        return dto;
    }

    /*
     * Annotation:
     * 用户 - 角色 赋权
     *
     * @Author: Adam Ming
     * @Date: Jun 30, 2020 at 11:41:11 AM
     */
    @PostMapping("/user-grant")
    public UserDTO userRoleGrant(@RequestBody JSONArray jsonArray) {
        userBLH.userRoleGrant(jsonArray);

        return userDTO;
    }
}
