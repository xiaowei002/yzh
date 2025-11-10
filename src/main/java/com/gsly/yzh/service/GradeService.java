package com.gsly.yzh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gsly.yzh.domain.GradeEntity;
import com.gsly.yzh.domain.dto.req.GradeReqDTO;

public interface GradeService extends IService<GradeEntity> {

    boolean insertGrade(GradeEntity grade);

    boolean updateGrade(GradeEntity grade);

    boolean deleteGradeById(Long id);

    boolean existGrade(GradeEntity grade);

    IPage<GradeEntity> getGradePageList(GradeReqDTO grade);

}
