package com.gsly.yzh.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author 魏国伟
 * 任务完成表
 */
@TableName("TASK_COMPLETE")
@Data
public class TaskCompleteEntity {

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
     * 完成人
     */
    @TableField
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 完成时间
     */
    @TableField
    private LocalDateTime completeTime;
}
