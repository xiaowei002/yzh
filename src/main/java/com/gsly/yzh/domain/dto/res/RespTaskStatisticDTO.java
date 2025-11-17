package com.gsly.yzh.domain.dto.res;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RespTaskStatisticDTO {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 任务id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long taskId;

    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;


    /**
     * 用户名
     */
    private String userName;


    /**
     * 任务名称
     */
    private String taskName;


    /**
     * 截止时间
     */
    private LocalDateTime deadLine;

}
