package com.dhcc.urms.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dhcc.urms.user.entity.User;
import com.dhcc.urms.user.entity.UserVO;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-28
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select U.USER_ID, U.USERNAME, U.NICKNAME, U.WARRANT_START_DATE, U.WARRANT_END_DATE, " +
                "   U.STATUS, U.CREATE_DATE, U.CREATOR, D.NAME as STATUS_NAME " +
                "  from T_USER U, " +
                "       (SELECT CODE, NAME FROM T_DICT WHERE CLASS = 'STATUS' AND STATUS = 'ACTIVE') D " +
                " where U.STATUS = D.CODE(+)")
    IPage<UserVO> findUser(Page<?> page);
}
