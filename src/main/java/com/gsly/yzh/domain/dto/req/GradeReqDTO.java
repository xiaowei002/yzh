package com.gsly.yzh.domain.dto.req;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.gsly.yzh.domain.GradeEntity;
import lombok.Data;

@Data
public class GradeReqDTO extends Page<GradeEntity> {

    private String college;

    private String gradeName;

    private String className;

}
