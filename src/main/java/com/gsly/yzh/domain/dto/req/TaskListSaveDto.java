package com.gsly.yzh.domain.dto.req;

import com.baomidou.mybatisplus.annotation.TableField;
import com.gsly.yzh.domain.TaskListEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskListSaveDto {
    /**
     * 任务名称
     */
    @NotBlank
    private String name;

    /**
     * 截止时间
     */
    @NotNull
    private LocalDateTime deadLine;

    /**
     * 类型
     */
    @NotBlank
    private String type;

    /**
     * 任务下发人员
     */
    @NotBlank
    private String userId;
}
