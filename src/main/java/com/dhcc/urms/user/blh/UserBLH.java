package com.dhcc.urms.user.blh;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dhcc.urms.common.entity.CommonParams;
import com.dhcc.urms.common.entity.DictEnum;
import com.dhcc.urms.common.entity.MyPage;
import com.dhcc.urms.common.security.SecurityUtils;
import com.dhcc.urms.user.dto.UserDTO;
import com.dhcc.urms.user.entity.User;
import com.dhcc.urms.user.entity.UserVO;
import com.dhcc.urms.user.service.IUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
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
        userService.removeById(dto.getUser().getUserId());
    }

    /*
     * Annotation:
     * 批量删除用户
     *
     * @Author: Adam Ming
     * @Date: Jun 28, 2020 at 2:36:16 PM
     */
    public void deleteUsers(UserDTO dto) {
        List<Long> userIdList = dto.getUserList().stream().map(User::getUserId).collect(Collectors.toList());
        userService.removeByIds(userIdList);
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
