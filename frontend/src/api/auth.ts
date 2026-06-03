import { httpPost } from './http';
import { encryptPassword } from '@/utils/encrypt';
import type { LoginResponse } from '@/typings/backend';

export function login(username: string, password: string) {
  // 对密码进行 MD5 加密后再传输
  const encryptedPassword = encryptPassword(password);
  return httpPost<LoginResponse>('/login', { username, password: encryptedPassword });
}



export const logout = () => httpPost<boolean>('/logout');

export const resetPassword = (username: string) =>
  httpPost<boolean>('/user/resetPassword', { username });
