import { httpGet } from './http';
import type { PageResult, TaskStatistic, TaskStatisticPageQuery } from '@/typings/backend';

/**
 * 获取任务统计分页列表（查询逾期未完成记录）
 */
export const fetchStatisticPage = (query: TaskStatisticPageQuery) =>
    httpGet<PageResult<TaskStatistic>>('/taskStatistic/getPageList', { params: query });
