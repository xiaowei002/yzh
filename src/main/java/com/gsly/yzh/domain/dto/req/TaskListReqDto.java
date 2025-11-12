package com.gsly.yzh.domain.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gsly.yzh.domain.TaskListEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskListReqDto extends Page<TaskListEntity> {

    /**
     * 状态
     */
    private Integer status;


    /**
     * 名称
     */
    private String name;

    /**
     * 截止时间开始
     */
    private LocalDateTime deadlineStart;

    /**
     * 截止时间结束
     */
    private LocalDateTime deadlineEnd;

}
