package com.gsly.yzh.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gsly.yzh.domain.GradeEntity;
import com.gsly.yzh.domain.TaskListEntity;
import com.gsly.yzh.domain.dto.req.GradeReqDTO;
import com.gsly.yzh.domain.dto.req.TaskListReqDto;
import com.gsly.yzh.domain.dto.req.TaskListSaveDto;
import com.gsly.yzh.domain.dto.req.TaskListUpdDto;
import com.gsly.yzh.mapper.TaskListMapper;
import com.gsly.yzh.service.TaskListService;
import com.gsly.yzh.utils.ResponseVO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taskList")
public class TaskListController {

    @Resource
    private TaskListService taskListService;

    /**
     * 创建任务
     * @param taskListSaveDto 任务
     * @return 统一返回
     */
    @PostMapping("/addTaskList")
    public ResponseVO<Boolean> addTaskList(@Valid @RequestBody TaskListSaveDto taskListSaveDto) {
        boolean b = taskListService.addTaskList(taskListSaveDto);
        return ResponseVO.success(b);
    }


    /**
     * 修改任务
     * @param taskListUpdDto 任务
     * @return 统一返回
     */
    @PutMapping("/updTaskList")
    public ResponseVO<Boolean> updTaskList(@Valid@RequestBody TaskListUpdDto taskListUpdDto){
        boolean b = taskListService.updTaskList(taskListUpdDto);
        return ResponseVO.success(b);
    }

    /**
     * 删除任务
     * @param id 主键
     * @return 统一返回
     */
    @PutMapping("/delTaskList/{id}")
    public ResponseVO<Boolean> delTaskList(@PathVariable("id") Long id){
        boolean b = taskListService.delTaskList(id);
        return ResponseVO.success(b);
    }

    /**
     * 任务分页
     * @param taskListReqDto
     * @return
     */
    @GetMapping("/getPageList")
    public ResponseVO<IPage<TaskListEntity>> getPageList(@RequestBody TaskListReqDto taskListReqDto){
        IPage<TaskListEntity> userPageList = taskListService.getPageList(taskListReqDto);
        return ResponseVO.success(userPageList);
    }

}
