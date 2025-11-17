package com.gsly.yzh.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jdk.jfr.DataAmount;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 未完成统计表
 * @author 魏国伟
 */
@Data
@TableName("task_assign")
public class TaskAssignEntity {

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
     * 状态
     */
    @TableField
    private Integer status;

    /**
     * 文件路径
     */
    @TableField
    private String filePath;

    /**
     * 提交时间
     */
    @TableField
    private LocalDateTime submitTime;
}
