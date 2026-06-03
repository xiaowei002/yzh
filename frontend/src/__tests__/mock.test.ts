import { describe, it, expect } from 'vitest';
import { mockHandlers } from '@/mock/handlers';
import { mockUsers, mockGrades, mockTasks, mockStatistics, mockLoginResponse } from '@/mock/data';

describe('mock handlers', () => {
  it('登录返回 mock 数据', () => {
    const result = mockHandlers['POST /login']('', {});
    expect(result).toEqual(mockLoginResponse);
    expect(result.token).toBeTruthy();
    expect(result.username).toBe('admin');
  });

  it('用户分页返回正确结构', () => {
    const result = mockHandlers['GET /user/getUserPageList']('', {}, { current: 1, size: 2 });
    expect(result.code).toBe(200);
    expect(result.data.records).toHaveLength(2);
    expect(result.data.total).toBe(3);
  });

  it('班级分页返回正确结构', () => {
    const result = mockHandlers['GET /grade/getGradePageList']('', {}, { current: 1, size: 10 });
    expect(result.data.total).toBe(3);
  });

  it('任务分页返回正确结构', () => {
    const result = mockHandlers['GET /taskList/getPageList']('', {}, { current: 1, size: 10 });
    expect(result.data.total).toBe(3);
  });

  it('逾期统计返回正确数据', () => {
    const result = mockHandlers['GET /taskStatistic/getPageList']('', {}, { current: 1, size: 10 });
    expect(result.data.total).toBe(2);
    expect(result.data.records[0].taskName).toBe('算法期中考试');
  });

  it('getById 返回正确实体', () => {
    const result = mockHandlers['GET /user/getById']('/1', {}, {});
    expect(result.data.username).toBe('admin');
  });

  it('不存在的 ID 返回 null', () => {
    const result = mockHandlers['GET /user/getById']('/999', {}, {});
    expect(result.data).toBeNull();
  });

  it('登出返回成功', () => {
    const result = mockHandlers['POST /logout']('', {});
    expect(result.data).toBe(true);
  });
});