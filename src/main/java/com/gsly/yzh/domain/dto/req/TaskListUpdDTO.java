package com.gsly.yzh.domain.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskListUpdDTO {

    /**
     * id
     */
    @NotNull
    private Long id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 截止时间
     */
    private LocalDateTime deadLine;

    /**
     * 类型
     */
    private String type;
}
