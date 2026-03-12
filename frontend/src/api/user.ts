import { httpDelete, httpGet, httpPost, httpPut } from './http';
import type { PageResult, User, UserPageQuery } from '@/typings/backend';

export const fetchUserPage = (query: UserPageQuery) =>
  httpGet<PageResult<User>>('/user/getUserPageList', { params: query });

export const createUser = (payload: User) =>
  httpPost<boolean>('/user/register', payload);

export const deleteUser = (id: string) =>
  httpDelete<boolean>(`/user/deleteById/${id}`);

export const enableUser = (id: string) =>
  httpPut<boolean>(`/user/enable/${id}`);

export const forbidUser = (id: string) =>
  httpPut<boolean>(`/user/forbidden/${id}`);

export const checkUserExist = (username: string) =>
  httpGet<boolean>('/user/exist', { params: { username } });

export const updateUser = (payload: Partial<User>) =>
  httpPut<boolean>('/user/update', payload);


