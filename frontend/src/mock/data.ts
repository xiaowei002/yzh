import type { User, Grade, TaskList, TaskStatistic, LoginResponse } from '@/typings/backend';

export const mockUsers: User[] = [
  { id: '1', username: 'admin', nickname: '管理员', role: 1, status: 1, phone: '13800000001', email: 'admin@yzh.com', createdAt: '2025-01-01 10:00:00' },
  { id: '2', username: 'zhangsan', nickname: '张三', role: 0, status: 1, phone: '13800000002', email: 'zs@yzh.com', createdAt: '2025-03-15 14:30:00' },
  { id: '3', username: 'lisi', nickname: '李四', role: 0, status: 0, phone: '13800000003', email: 'ls@yzh.com', createdAt: '2025-04-20 09:00:00' },
];

export const mockGrades: Grade[] = [
  { id: '1', college: '计算机学院', gradeName: '2023级', className: '软件工程1班', createdAt: '2025-01-10 08:00:00' },
  { id: '2', college: '计算机学院', gradeName: '2023级', className: '软件工程2班', createdAt: '2025-01-10 08:00:00' },
  { id: '3', college: '数学学院', gradeName: '2024级', className: '应用数学1班', createdAt: '2025-02-20 10:00:00' },
];

export const mockTasks: TaskList[] = [
  { id: '1', name: '数据结构大作业', type: '作业', deadLine: '2026-06-15 23:59:59', description: '完成二叉树和图的实验报告', createdAt: '2026-05-01 10:00:00' },
  { id: '2', name: '期末项目答辩', type: '项目', deadLine: '2026-06-30 17:00:00', description: '小组项目演示和文档提交', createdAt: '2026-05-05 14:00:00' },
  { id: '3', name: '算法期中考试', type: '考试', deadLine: '2026-04-15 10:00:00', description: '闭卷考试', createdAt: '2026-03-20 08:00:00' },
];

export const mockStatistics: TaskStatistic[] = [
  { id: '1', taskId: '3', userId: '2', taskName: '算法期中考试', userName: '张三', deadLine: '2026-04-15 10:00:00' },
  { id: '2', taskId: '3', userId: '3', taskName: '算法期中考试', userName: '李四', deadLine: '2026-04-15 10:00:00' },
];

export const mockLoginResponse: LoginResponse = {
  token: 'mock-jwt-token-admin',
  userId: '1',
  username: 'admin',
  role: 1,
};