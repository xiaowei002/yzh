import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from 'axios';
import { ElMessage } from 'element-plus';
import router from '@/router';
import { useAuthStore } from '@/stores/auth';
import type { ResponseVO } from '@/typings/backend';

const instance = axios.create({
  baseURL: '/api',
  timeout: 10000
});

instance.interceptors.request.use((config) => {
  const auth = useAuthStore();
  if (auth.token) {
    config.headers = config.headers ?? {};
    config.headers.Authorization = `Bearer ${auth.token}`;
  }
  return config;
});

instance.interceptors.response.use(
  (response: AxiosResponse<ResponseVO<any> | string>) => {
    // 登录接口直接返回字符串 token
    if (typeof response.data === 'string') {
      return response.data;
    }
    const payload = response.data as ResponseVO<any>;
    if (payload.code === 200 || payload.code === 0) {
      return payload.data;
    }
    ElMessage.error(payload.msg || '请求失败');
    return Promise.reject(payload);
  },
  (error: AxiosError) => {
    const status = error.response?.status;
    if (status === 401) {
      const auth = useAuthStore();
      auth.clear();
      ElMessage.error('登录已过期，请重新登录');
      router.push('/login');
    } else {
      const errorMsg = (error.response?.data as any)?.msg || error.response?.data || error.message;
      ElMessage.error(errorMsg);
    }
    return Promise.reject(error);
  }
);

export function httpGet<T>(url: string, config?: AxiosRequestConfig) {
  return instance.get<T>(url, config);
}

export function httpPost<T>(url: string, data?: any, config?: AxiosRequestConfig) {
  return instance.post<T>(url, data, config);
}

export function httpPut<T>(url: string, data?: any, config?: AxiosRequestConfig) {
  return instance.put<T>(url, data, config);
}

export function httpDelete<T>(url: string, config?: AxiosRequestConfig) {
  return instance.delete<T>(url, config);
}

export default instance;


