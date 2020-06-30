package com.dhcc.urms.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhcc.urms.common.entity.PageModel;
import com.dhcc.urms.user.dto.UserDTO;
import com.dhcc.urms.user.entity.User;
import com.dhcc.urms.user.entity.UserVO;
import com.dhcc.urms.user.mapper.UserMapper;
import com.dhcc.urms.user.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Override
    public IPage<UserVO> findUser(UserDTO dto) {
        PageModel pageModel = dto.getPageModel();
        Page<UserVO> page = new Page<>(pageModel.getPageNo(), pageModel.getPageSize());

        return userMapper.findUser(page);
    }
}
