package com.dhcc.urms.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dhcc.urms.user.dto.UserDTO;
import com.dhcc.urms.user.entity.User;
import com.dhcc.urms.user.entity.UserVO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-28
 */
public interface IUserService extends IService<User> {
    /*
     * Annotation:
     * 用户分页查询
     *
     * @Author: Adam Ming
     * @Date: Jun 28, 2020 at 10:59:13 AM
     */
    IPage<UserVO> findUser(UserDTO dto);
}
