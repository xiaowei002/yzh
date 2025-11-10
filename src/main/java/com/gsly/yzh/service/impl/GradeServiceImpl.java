package com.gsly.yzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsly.yzh.domain.BaseEntity;
import com.gsly.yzh.domain.GradeEntity;
import com.gsly.yzh.domain.UserEntity;
import com.gsly.yzh.domain.dto.req.GradeReqDTO;
import com.gsly.yzh.mapper.GradeMapper;
import com.gsly.yzh.mapper.UserMapper;
import com.gsly.yzh.service.GradeService;
import com.gsly.yzh.utils.BizException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.prefs.BackingStoreException;


@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, GradeEntity> implements GradeService {

    @Resource
    private GradeMapper gradeMapper;

    @Resource
    private UserMapper userMapper;


    @Override
    public boolean insertGrade(GradeEntity grade) {
        if (Objects.isNull(grade)) {
            throw new BizException("当前班级参数传递异常！");
        }
        if (existGrade(grade)) {
            throw new BizException("当前班级已经存在！");
        }
        return gradeMapper.insert(grade) > 0;
    }

    @Override
    public boolean updateGrade(GradeEntity grade) {
        if (Objects.isNull(grade)) {
            throw new BizException("当前班级参数传递异常！");
        }
        GradeEntity gradeEntity = gradeMapper.selectById(grade.getId());
        if (Objects.isNull(gradeEntity)) {
            throw new BizException("无法通过传入的id查询到班级！");
        }
        return gradeMapper.updateById(gradeEntity) > 0;
    }

    @Override
    public boolean deleteGradeById(Long id) {
        if (Objects.isNull(id) || Objects.isNull(gradeMapper.selectById(id))) {
            throw new BizException("当前Id无法查询到班级信息！");
        }
        //查看当前班级是否绑定了学生
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserEntity::getClassId, id);
        UserEntity userEntity = userMapper.selectOne(wrapper);
        if (Objects.nonNull(userEntity)) {
            throw new BizException("当前班级绑定了学生，无法删除！");
        }
        return gradeMapper.deleteById(id) > 0;
    }

    @Override
    public boolean existGrade(GradeEntity grade) {
        if (Objects.isNull(grade) || Objects.isNull(grade.getCollege())
        || Objects.isNull(grade.getGradeName()) || Objects.isNull(grade.getClassName())) {
            throw new BizException("当前传递班级不合法！");
        }
        //判断班级是否存在
        LambdaQueryWrapper<GradeEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GradeEntity::getCollege, grade.getCollege());
        wrapper.eq(GradeEntity::getGradeName, grade.getGradeName());
        wrapper.eq(GradeEntity::getClassName, grade.getClassName());
        GradeEntity gradeEntity = gradeMapper.selectOne(wrapper);
        return  Objects.nonNull(gradeEntity);
    }

    @Override
    public IPage<GradeEntity> getGradePageList(GradeReqDTO grade) {
        LambdaQueryWrapper<GradeEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.isNotBlank(grade.getCollege()), GradeEntity::getCollege, grade.getCollege());
        wrapper.like(StringUtils.isNotBlank(grade.getClassName()), GradeEntity::getClassName, grade.getClassName());
        wrapper.like(StringUtils.isNotBlank(grade.getGradeName()), GradeEntity::getGradeName, grade.getGradeName());
        return gradeMapper.selectPage(grade, wrapper);
    }
}
