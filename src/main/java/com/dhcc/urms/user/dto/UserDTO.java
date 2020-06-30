package com.dhcc.urms.user.dto;

import com.dhcc.urms.common.entity.BaseAbstractDTO;
import com.dhcc.urms.user.entity.User;
import com.dhcc.urms.user.entity.UserVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Annotation:
 * User 数据传输对象
 *
 * @Author: Adam Ming
 * @Date: Jun 28, 2020 at 10:51:35 AM
 */
@Component
public class UserDTO extends BaseAbstractDTO {
    private static final long serialVersionUID = 1779401999139205478L;

    private User user;
    private List<User> userList;
    private UserVO userVO;
    private int count;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
