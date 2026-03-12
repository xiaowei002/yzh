-- ----------------------------
-- 数据库初始化脚本
-- ----------------------------

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 1. 用户表 (UserEntity -> USERS)
-- ----------------------------
DROP TABLE IF EXISTS `USERS`;
CREATE TABLE `USERS` (
  `id` BIGINT NOT NULL COMMENT '主键ID',
  `class_id` BIGINT DEFAULT NULL COMMENT '班级id',
  `username` VARCHAR(255) NOT NULL COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `role` INT DEFAULT 0 COMMENT '角色：1-管理员，0-普通用户',
  `nickname` VARCHAR(255) DEFAULT NULL COMMENT '昵称',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '电话',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `status` INT DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- 2. 班级表 (GradeEntity -> GRADE)
-- ----------------------------
DROP TABLE IF EXISTS `GRADE`;
CREATE TABLE `GRADE` (
  `id` BIGINT NOT NULL COMMENT '主键ID',
  `college` VARCHAR(255) DEFAULT NULL COMMENT '学院',
  `grade_name` VARCHAR(100) DEFAULT NULL COMMENT '年级',
  `class_name` VARCHAR(100) DEFAULT NULL COMMENT '班级',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级表';

-- ----------------------------
-- 3. 任务列表 (TaskListEntity -> TASK_LIST)
-- ----------------------------
DROP TABLE IF EXISTS `TASK_LIST`;
CREATE TABLE `TASK_LIST` (
  `id` BIGINT NOT NULL COMMENT '主键ID',
  `name` VARCHAR(255) NOT NULL COMMENT '任务名称',
  `dead_line` DATETIME NOT NULL COMMENT '截止时间',
  `type` VARCHAR(255) DEFAULT NULL COMMENT '允许上传的文件类型(逗号分隔)',
  `description` TEXT DEFAULT NULL COMMENT '任务描述/备注',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务表';

-- ----------------------------
-- 4. 任务分配/待完成表 (TaskAssignEntity -> task_assign)
-- ----------------------------
DROP TABLE IF EXISTS `task_assign`;
CREATE TABLE `task_assign` (
  `id` BIGINT NOT NULL COMMENT '主键ID',
  `task_id` BIGINT NOT NULL COMMENT '任务id',
  `user_id` BIGINT NOT NULL COMMENT '用户id',
  `status` INT DEFAULT 0 COMMENT '状态：0-未完成，1-已完成',
  `file_path` VARCHAR(500) DEFAULT NULL COMMENT '提交的文件路径',
  `submit_time` DATETIME DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`),
  KEY `idx_task_user` (`task_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务分配记录表';

-- ----------------------------
-- 5. 任务完成记录表 (TaskCompleteEntity -> TASK_COMPLETE)
-- ----------------------------
DROP TABLE IF EXISTS `TASK_COMPLETE`;
CREATE TABLE `TASK_COMPLETE` (
  `id` BIGINT NOT NULL COMMENT '主键ID',
  `task_id` BIGINT NOT NULL COMMENT '任务id',
  `user_id` BIGINT NOT NULL COMMENT '完成人ID',
  `complete_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '完成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务完成详情表';

-- ----------------------------
-- 6. 任务统计/逾期告警表 (TaskStatisticEntity -> task_statistic)
-- ----------------------------
DROP TABLE IF EXISTS `task_statistic`;
CREATE TABLE `task_statistic` (
  `id` BIGINT NOT NULL COMMENT '主键ID',
  `task_id` BIGINT NOT NULL COMMENT '任务id',
  `user_id` BIGINT NOT NULL COMMENT '用户id',
  `task_name` VARCHAR(255) DEFAULT NULL COMMENT '任务名称',
  `user_name` VARCHAR(255) DEFAULT NULL COMMENT '用户名',
  `dead_line` DATETIME DEFAULT NULL COMMENT '任务截止时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_task_user` (`task_id`, `user_id`) COMMENT '每个用户针对每个任务只记录一次逾期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='逾期未完成任务统计记录';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- 7. 初始化数据
-- ----------------------------

-- 插入一个默认班级 (ID: 1)
INSERT INTO `GRADE` (`id`, `college`, `grade_name`, `class_name`) 
VALUES (1, '信息工程学院', '2022级', '计算机科学1班');

-- 插入管理员用户 (ID: 1, 关联班级: 1, 角色: 1)
-- 密码 'admin' 的双重 MD5 值为 c3284d0f94606de1fd2af172aba15bf3
INSERT INTO `USERS` (`id`, `class_id`, `username`, `password`, `role`, `nickname`, `status`) 
VALUES (1, 1, 'admin', 'c3284d0f94606de1fd2af172aba15bf3', 1, '系统管理员', 1);

