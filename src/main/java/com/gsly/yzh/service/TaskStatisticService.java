package com.gsly.yzh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gsly.yzh.domain.TaskStatisticEntity;
import com.gsly.yzh.domain.dto.req.TaskStatisticReqDTO;
import com.gsly.yzh.domain.dto.res.RespTaskStatisticDTO;

public interface TaskStatisticService extends IService<TaskStatisticEntity> {

    IPage<RespTaskStatisticDTO> getPageList(TaskStatisticReqDTO taskStatisticReqDTO);


}
