package com.dhcc.urms.common.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.dhcc.urms.user.util.UserInfo;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    private final Logger logger = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        logger.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createDate", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "creator", Long.class, UserInfo.getUser().getUserId());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updater", Long.class, UserInfo.getUser().getUserId());
        this.strictInsertFill(metaObject, "odn", Long.class, 0L);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        logger.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        this.strictUpdateFill(metaObject, "updater", Long.class, UserInfo.getUser().getUserId()); // 起始版本 3.3.0(推荐使用)
    }
}
