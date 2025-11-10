package com.gsly.yzh.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private Long id;

    /**
     * 任务id
     */
    @TableField
    private Long taskId;

    /**
     * 完成人
     */
    @TableField
    private Long userId;

    /**
     * 完成时间
     */
    @TableField
    private LocalDateTime completeTime;
}
