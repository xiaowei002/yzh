import type { ResponseVO, PageResult } from '@/typings/backend';
import { mockUsers, mockGrades, mockTasks, mockStatistics, mockLoginResponse } from './data';

export type MockHandler = (url: string, data?: any, params?: any) => any;

const ok = <T>(data: T): ResponseVO<T> => ({ code: 200, data });

// Simple ID counter for create operations
let nextId = 100;

export const mockHandlers: Record<string, MockHandler> = {
  // Auth
  'POST /login': () => mockLoginResponse,
  'POST /logout': () => ok(true),

  // User
  'GET /user/getUserPageList': (_url, _data, params: any) => {
    const { current = 1, size = 10 } = params || {};
    const records = mockUsers.slice((current-1)*size, current*size);
    return ok<PageResult<any>>({ records, total: mockUsers.length });
  },
  'POST /user/register': (url, data) => {
    mockUsers.push({ ...data, id: String(++nextId), createdAt: new Date().toISOString() });
    return ok(true);
  },
  'DELETE /user/deleteById': () => ok(true),
  'PUT /user/enable': () => ok(true),
  'PUT /user/forbidden': () => ok(true),
  'GET /user/exist': () => ok(false),
  'PUT /user/update': () => ok(true),
  'GET /user/getById': (url) => {
    const id = url.split('/').pop();
    return ok(mockUsers.find(u => u.id === id) || null);
  },

  // Grade
  'GET /grade/getGradePageList': (_url, _data, params: any) => {
    const { current = 1, size = 10 } = params || {};
    const records = mockGrades.slice((current-1)*size, current*size);
    return ok<PageResult<any>>({ records, total: mockGrades.length });
  },
  'POST /grade/addGrade': () => ok(true),
  'PUT /grade/updGrade': () => ok(true),
  'PUT /grade/deleteById': () => ok(true),
  'GET /grade/exist': () => ok(false),
  'GET /grade/getById': (url) => {
    const id = url.split('/').pop();
    return ok(mockGrades.find(g => g.id === id) || null);
  },

  // Task
  'GET /taskList/getPageList': (_url, _data, params: any) => {
    const { current = 1, size = 10 } = params || {};
    const records = mockTasks.slice((current-1)*size, current*size);
    return ok<PageResult<any>>({ records, total: mockTasks.length });
  },
  'POST /taskList/addTaskList': () => ok(true),
  'PUT /taskList/updTaskList': () => ok(true),
  'PUT /taskList/delTaskList': () => ok(true),
  'GET /taskList/getById': (url) => {
    const id = url.split('/').pop();
    return ok(mockTasks.find(t => t.id === id) || null);
  },

  // Task statistic
  'GET /taskStatistic/getPageList': (_url, _data, params: any) => {
    const { current = 1, size = 10 } = params || {};
    const records = mockStatistics.slice((current-1)*size, current*size);
    return ok<PageResult<any>>({ records, total: mockStatistics.length });
  },

  // Files
  'GET /file/task': () => new Blob(['mock'], { type: 'application/zip' }),
  'POST /taskAssign/completeTask': () => ok(true),

  // Password
  'POST /user/resetPassword': () => ok(true),
};