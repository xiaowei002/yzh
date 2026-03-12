// 后端实体与 DTO 映射
export interface BaseEntity {
  id: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface User extends BaseEntity {
  classId?: string;
  username: string;
  password?: string;
  role?: number;
  nickname?: string;
  phone?: string;
  email?: string;
  status?: number;
}

export interface Grade extends BaseEntity {
  college?: string;
  gradeName?: string;
  className?: string;
}

export interface TaskList extends BaseEntity {
  name: string;
  deadLine: string;
  type: string;
  description?: string;
}

export interface TaskAssign {
  id?: string;
  taskId: string;
  userId?: string;
  status?: number;
  filePath?: string;
  submitTime?: string;
}

export interface TaskStatistic {
  id: string;
  taskId: string;
  userId: string;
  taskName: string;
  userName: string;
  deadLine: string;
}

export interface PageQuery {
  current?: number;
  size?: number;
}

export interface UserPageQuery extends PageQuery {
  username?: string;
  nickname?: string;
  status?: number;
}

export interface GradePageQuery extends PageQuery {
  college?: string;
  gradeName?: string;
  className?: string;
}

export interface TaskListPageQuery extends PageQuery {
  name?: string;
  deadlineStart?: string;
  deadlineEnd?: string;
}

export interface TaskStatisticPageQuery extends PageQuery {
  taskName?: string;
}

export interface PageResult<T> {
  records: T[];
  total: number;
  pages?: number;
  current?: number;
  size?: number;
}

export interface ResponseVO<T> {
  code: number;
  msg?: string;
  data: T;
}


