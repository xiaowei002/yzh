package com.gsly.yzh.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 魏国伟
 * 任务表
 */
@TableName("TASK_LIST")
@EqualsAndHashCode(callSuper = true)
@Data
public class TaskListEntity extends BaseEntity {

    /**
     * 任务名称
     */
    @TableField
    private String name;

    /**
     * 截止时间
     */
    @TableField
    private LocalDateTime deadLine;

    /**
     * 类型
     */
    @TableField
    private String type;

    /**
     * 任务下发人员
     */
    @TableField
    private String userId;

    /**
     * 状态 1进行中，0已完结
     */
    @TableField
    private Integer status;
}
