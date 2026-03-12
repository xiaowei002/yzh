import { httpGet, httpPost, httpPut } from './http';
import type { PageResult, TaskList, TaskListPageQuery } from '@/typings/backend';

export const fetchTaskPage = (query: TaskListPageQuery) =>
  httpGet<PageResult<TaskList>>('/taskList/getPageList', { params: query });

export const createTask = (payload: TaskList) =>
  httpPost<boolean>('/taskList/addTaskList', payload);

export const updateTask = (payload: TaskList) =>
  httpPut<boolean>('/taskList/updTaskList', payload);

export const deleteTask = (id: string) =>
  httpPut<boolean>(`/taskList/delTaskList/${id}`);


