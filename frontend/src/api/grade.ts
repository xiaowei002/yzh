import { httpGet, httpPost, httpPut } from './http';
import type { Grade, GradePageQuery, PageResult } from '@/typings/backend';

export const fetchGradePage = (query: GradePageQuery) =>
  httpGet<PageResult<Grade>>('/grade/getGradePageList', { params: query });

export const createGrade = (payload: Grade) =>
  httpPost<boolean>('/grade/addGrade', payload);

export const updateGrade = (payload: Grade) =>
  httpPut<boolean>('/grade/updGrade', payload);

export const deleteGrade = (id: string) =>
  httpPut<boolean>(`/grade/deleteById/${id}`);

export const existGrade = (payload: Grade) =>
  httpGet<boolean>('/grade/exist', { params: payload });


