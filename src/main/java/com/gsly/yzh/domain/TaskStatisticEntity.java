package com.gsly.yzh.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@TableName("task_statistic")
public class TaskStatisticEntity {

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
     * 用户id
     */
    @TableField
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;


    /**
     * 任务名称
     */
    @TableField
    private String taskName;

    /**
     * 用户名
     */
    @TableField
    private String userName;


    /**
     * 截止时间
     */
    @TableField
    private LocalDateTime deadLine;

}
