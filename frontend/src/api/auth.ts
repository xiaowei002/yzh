import { httpGet } from './http';
import { encryptPassword } from '@/utils/encrypt';

export function login(username: string, password: string) {
  // 对密码进行 MD5 加密后再传输
  const encryptedPassword = encryptPassword(password);
  return httpGet<string>('/login', { params: { username, password: encryptedPassword } });
}


