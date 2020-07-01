package com.dhcc.urms.user.blh;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dhcc.urms.common.entity.CommonParams;
import com.dhcc.urms.common.entity.DictEnum;
import com.dhcc.urms.common.entity.GrantJsonVO;
import com.dhcc.urms.common.entity.MyPage;
import com.dhcc.urms.role.entity.Role;
import com.dhcc.urms.role.service.IRoleService;
import com.dhcc.urms.user.dto.UserDTO;
import com.dhcc.urms.user.entity.User;
import com.dhcc.urms.user.entity.UserVO;
import com.dhcc.urms.user.service.IUserService;
import com.dhcc.urms.userrole.entity.UserRole;
import com.dhcc.urms.userrole.service.IUserRoleService;
import com.mojitoming.casclient.util.SecurityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Annotation:
 * User 逻辑处理层
 *
 * @Author: Adam Ming
 * @Date: Jun 28, 2020 at 10:50:28 AM
 */
@Component
public class UserBLH implements Serializable {
    private static final long serialVersionUID = -8164926589058244604L;

    @Resource
    private IUserService userService;

    @Resource
    private IRoleService roleService;

    @Resource
    private IUserRoleService userRoleService;

    /*
     * Annotation:
     * 查询 User 信息
     * 分页查询
     *
     * @Author: Adam Ming
     * @Date: Jun 28, 2020 at 11:01:25 AM
     */
    public MyPage findUser(UserDTO dto) {
        IPage<UserVO> iPage = userService.findUser(dto);

        return new MyPage(iPage.getTotal(), iPage.getRecords());
    }

    /*
     * Annotation:
     * 新增用户
     *
     * @Author: Adam Ming
     * @Date: Jun 28, 2020 at 2:28:42 PM
     */
    public void addUser(UserDTO dto) {
        User user = dto.getUser();
        convertStatus(user);

        // 初始化密码
        initPassword(user);

        userService.save(user);
    }

    /*
     * Annotation:
     * 修改用户
     *
     * @Author: Adam Ming
     * @Date: Jun 28, 2020 at 2:34:08 PM
     */
    public void updateUser(UserDTO dto) {
        UserVO userVO = dto.getUserVO();
        String resetPassword = userVO.getResetPassword();
        if ("on".equals(resetPassword)) { // 重置密码
            initPassword(userVO);
        }
        convertStatus(userVO);

        userService.updateById(userVO);
    }

    /*
     * Annotation:
     * 删除用户
     *
     * @Author: Adam Ming
     * @Date: Jun 28, 2020 at 2:35:28 PM
     */
    public void deleteUser(UserDTO dto) {
        userService.deleteUser(dto);
    }

    /*
     * Annotation:
     * 批量删除用户
     *
     * @Author: Adam Ming
     * @Date: Jun 28, 2020 at 2:36:16 PM
     */
    public void deleteUsers(UserDTO dto) {
        userService.deleteUsers(dto);
    }

    /*
     * Annotation:
     * 根据 username 获取 user 数量
     *
     * @Author: Adam Ming
     * @Date: Jun 28, 2020 at 4:41:59 PM
     */
    public void findUserCountByUsername(UserDTO dto) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("USERNAME", dto.getUser().getUsername());
        int count = userService.count(qw);

        dto.setCount(count);
    }

    /*
     * Annotation:
     * 用户 - 角色 授权逻辑
     *
     * @Author: Adam Ming
     * @Date: Jun 30, 2020 at 11:42:55 AM
     */
    public void userRoleGrant(JSONArray jsonArray) {
        List<GrantJsonVO> grantJsonVOList = jsonArray.toJavaList(GrantJsonVO.class);
        if (grantJsonVOList.size() == 0) {
            return;
        }

        // 找优先级最高的角色
        List<Long> roleIdList = grantJsonVOList.stream().map(e -> Long.parseLong(e.getNodeId())).collect(Collectors.toList());
        List<Role> roleList = roleService.listByIds(roleIdList);
        Role topRole = roleList.stream().min(Comparator.comparingLong(Role::getPriority)).orElse(new Role());

        List<UserRole> userRoleList = new ArrayList<>();
        UserRole userRole;

        long roleIdTemp;
        for (GrantJsonVO grantJsonVO : grantJsonVOList) {
            userRole = new UserRole();
            userRole.setUserId(Long.parseLong(grantJsonVO.getUserId()));

            roleIdTemp = Long.parseLong(grantJsonVO.getNodeId());

            if (roleIdTemp == 0) { // 排除树根
                continue;
            }

            userRole.setRoleId(roleIdTemp);

            // 优先级最高的角色设置为默认角色
            if (roleIdTemp == topRole.getRoleId()) {
                userRole.setIsDefault("1");
            }

            userRoleList.add(userRole);
        }

        // 先删后插 ---> 根据 userId 删除所有 关联的 role，然后再插入上面构造的 List
        long userId = userRoleList.get(0).getUserId();
        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq("USER_ID", userId);
        userRoleService.remove(qw);

        userRoleService.saveBatch(userRoleList);
    }

    // =================== 私有方法分割线 ===================

    /*
     * Annotation:
     * User Status 状态转变
     *
     * @Author: Adam Ming
     * @Date: Jun 22, 2020 at 5:33:29 PM
     */
    private void convertStatus(User user) {
        switch (user.getStatus()) {
            case "on":
                user.setStatus(DictEnum.STATUS_ACTIVE.getValue());

                break;
            case "off":
                user.setStatus(DictEnum.STATUS_INACTIVE.getValue());

                break;
        }
    }

    /*
     * Annotation:
     * 初始化密码
     *
     * @Author: Adam Ming
     * @Date: Jun 30, 2020 at 9:39:31 AM
     */
    private void initPassword(User user) {
        // 密码 及 盐
        String salt = SecurityUtils.getSalt(CommonParams.INIT_PASSWORD);
        String encryptedPassword = SecurityUtils.encryptPassword(salt, CommonParams.INIT_PASSWORD);

        user.setSalt(salt);
        user.setPassword(encryptedPassword);
    }
}
