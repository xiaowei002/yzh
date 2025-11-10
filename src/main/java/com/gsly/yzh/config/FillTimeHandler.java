package com.gsly.yzh.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 创建时间和更新时间自动填充
 */
@Component
@Slf4j
public class FillTimeHandler implements MetaObjectHandler {
    /**
     * 填充创建时间
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createdAt", LocalDateTime.now(), metaObject);
    }

    /**
     * 填充更新时间
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updatedAt", LocalDateTime.now(), metaObject);
    }



    @Scheduled(
            cron = "0 */1 * * * *"
    )
    public void schedule(){
        log.debug("定时任务检查管理员布置任务是否完成");




        LocalDateTime now = LocalDateTime.now();
    }
}
