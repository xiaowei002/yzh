package com.gsly.yzh.domain.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import lombok.Data;

@Data
public class TaskStatisticReqDTO extends Page {

    private String taskName;
}
