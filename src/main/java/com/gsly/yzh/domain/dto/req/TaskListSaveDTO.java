package com.gsly.yzh.domain.dto.req;

import com.baomidou.mybatisplus.annotation.TableField;
import com.gsly.yzh.domain.TaskListEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskListSaveDTO {
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
     * 任务描述
     */
    private String description;

    /**
     * 用户
     */
    @NotEmpty
    private List<Long> userIdList;
}
