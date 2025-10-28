package com.gsly.yzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsly.yzh.domain.GradeEntity;

public interface GradeService extends IService<GradeEntity> {

    boolean insertGrade(GradeEntity grade);

    boolean updateGrade(GradeEntity grade);


}
