import { mockHandlers } from './handlers';

/**
 * 安装 Mock 拦截器 — 在 Vite 开发模式下拦截所有 /api 请求并返回模拟数据
 */
export function setupMock() {
  if (!import.meta.env.DEV) return;

  // 动态导入 axios instance 避免循环依赖
  import('@/api/http').then(({ default: instance }) => {
    instance.interceptors.request.use((config) => {
      // 注入 mock token
      if (!config.headers.Authorization) {
        config.headers.Authorization = 'Bearer mock-token';
      }
      return config;
    });

    instance.interceptors.response.use(undefined, async (error) => {
      // 当没有真实后端时，网络错误会触发这里，返回 mock 数据
      const cfg = error.config;
      if (!cfg) return Promise.reject(error);

      const method = (cfg.method || 'GET').toUpperCase();
      const url: string = cfg.url || '';

      // 匹配 mock handler
      const handlerKey = Object.keys(mockHandlers).find(k => {
        const [hMethod, hPath] = k.split(' ');
        return hMethod === method && url.includes(hPath);
      });

      if (handlerKey) {
        const handler = mockHandlers[handlerKey];
        const result = handler(url, cfg.data, cfg.params);
        // 模拟网络延迟
        await new Promise(r => setTimeout(r, 200 + Math.random() * 300));
        return Promise.resolve({
          data: result,
          status: 200,
          statusText: 'OK',
          headers: {},
          config: cfg,
        });
      }

      return Promise.reject(error);
    });

    console.log('[Mock] API mock layer active — %c所有 /api 请求已接管', 'color: #67C23A');
  });
}