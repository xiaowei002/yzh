package com.gsly.yzh.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gsly.yzh.domain.GradeEntity;
import com.gsly.yzh.domain.UserEntity;
import com.gsly.yzh.domain.dto.req.GradeReqDTO;
import com.gsly.yzh.domain.dto.req.UserReqDTO;
import com.gsly.yzh.service.GradeService;
import com.gsly.yzh.utils.ResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grade")
public class GradeController {

    @Resource
    private GradeService gradeService;

    /**
     * 创建班级
     * @param user 班级
     * @return 统一返回
     */
    @PostMapping("/addGrade")
    public ResponseVO<Boolean> addGrade(@RequestBody GradeEntity user) {
        boolean b = gradeService.insertGrade(user);
        return ResponseVO.success(b);
    }

    /**
     * 判断班级是否存在
     * @param gradeEntity 班级
     * @return 统一返回
     */
    @GetMapping("/exist")
    public ResponseVO<Boolean> exist(@RequestBody GradeEntity gradeEntity){
        boolean b = gradeService.existGrade(gradeEntity);
        return ResponseVO.success(b);
    }

    /**
     * 修改班级
     * @param gradeEntity 主键
     * @return 统一返回
     */
    @PutMapping("/updGrade")
    public ResponseVO<Boolean> updGrade(@RequestBody GradeEntity gradeEntity){
        boolean b = gradeService.updateGrade(gradeEntity);
        return ResponseVO.success(b);
    }

    /**
     * 删除班级
     * @param id 主键
     * @return 统一返回
     */
    @PutMapping("/deleteById/{id}")
    public ResponseVO<Boolean> enable(@PathVariable("id") Long id){
        boolean b = gradeService.deleteGradeById(id);
        return ResponseVO.success(b);
    }

    /**
     * 班级分页
     * @param gradeReqDTO
     * @return
     */
    @GetMapping("/getGradePageList")
    public ResponseVO<IPage<GradeEntity>> getGradePageList(@RequestBody GradeReqDTO gradeReqDTO){
        IPage<GradeEntity> userPageList = gradeService.getGradePageList(gradeReqDTO);
        return ResponseVO.success(userPageList);
    }


}
