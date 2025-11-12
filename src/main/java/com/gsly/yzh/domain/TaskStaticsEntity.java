package com.gsly.yzh.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jdk.jfr.DataAmount;
import lombok.Data;

/**
 * 未完成统计表
 * @author 魏国伟
 */
@Data
@TableName("TASK_STATICS")
public class TaskStaticsEntity {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 任务id
     */
    @TableField
    @JsonSerialize(using = ToStringSerializer.class)
    private Long taskId;

    /**
     * 未完成用户
     */
    @TableField
    private String unCompleteUser;
}
