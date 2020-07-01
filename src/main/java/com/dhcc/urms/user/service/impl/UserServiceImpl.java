package com.dhcc.urms.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhcc.urms.common.entity.PageModel;
import com.dhcc.urms.user.dto.UserDTO;
import com.dhcc.urms.user.entity.User;
import com.dhcc.urms.user.entity.UserVO;
import com.dhcc.urms.user.mapper.UserMapper;
import com.dhcc.urms.user.service.IUserService;
import com.dhcc.urms.userrole.entity.UserRole;
import com.dhcc.urms.userrole.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-28
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public IPage<UserVO> findUser(UserDTO dto) {
        PageModel pageModel = dto.getPageModel();
        Page<UserVO> page = new Page<>(pageModel.getPageNo(), pageModel.getPageSize());

        return userMapper.findUser(page);
    }

    @Override
    public void deleteUser(UserDTO dto) {
        long userId = dto.getUser().getUserId();

        // 删除用户
        userMapper.deleteById(userId);

        // 删除用户关联的授权
        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq("USER_ID", userId);
        userRoleMapper.delete(qw);
    }

    @Override
    public void deleteUsers(UserDTO dto) {
        List<Long> userIdList = dto.getUserList().stream().map(User::getUserId).collect(Collectors.toList());

        // 删除用户
        userMapper.deleteBatchIds(userIdList);

        // 删除用户关联的授权
        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.in("USER_ID", userIdList);
        userRoleMapper.delete(qw);
    }
}
