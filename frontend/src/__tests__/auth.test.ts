import { describe, it, expect, beforeEach } from 'vitest';
import { setActivePinia, createPinia } from 'pinia';
import { useAuthStore } from '@/stores/auth';

describe('auth store', () => {
  beforeEach(() => {
    setActivePinia(createPinia());
    localStorage.clear();
  });

  it('初始状态为空', () => {
    const store = useAuthStore();
    expect(store.token).toBeNull();
    expect(store.userId).toBeNull();
    expect(store.username).toBeNull();
    expect(store.role).toBeNull();
  });

  it('setAuth 存储所有字段', () => {
    const store = useAuthStore();
    store.setAuth('tkn-123', '1', 'admin', 1);
    expect(store.token).toBe('tkn-123');
    expect(store.userId).toBe('1');
    expect(store.username).toBe('admin');
    expect(store.role).toBe(1);
    expect(localStorage.getItem('yzh_token')).toBe('tkn-123');
    expect(localStorage.getItem('yzh_user')).toBe('1');
    expect(localStorage.getItem('yzh_username')).toBe('admin');
    expect(localStorage.getItem('yzh_role')).toBe('1');
  });

  it('setAuth 可选字段可省略', () => {
    const store = useAuthStore();
    store.setAuth('tkn-only');
    expect(store.token).toBe('tkn-only');
    expect(store.userId).toBeNull();
    expect(store.username).toBeNull();
    expect(store.role).toBeNull();
  });

  it('clear 清除所有数据', () => {
    const store = useAuthStore();
    store.setAuth('tkn', '1', 'admin', 1);
    store.clear();
    expect(store.token).toBeNull();
    expect(store.userId).toBeNull();
    expect(store.username).toBeNull();
    expect(store.role).toBeNull();
    expect(localStorage.getItem('yzh_token')).toBeNull();
    expect(localStorage.getItem('yzh_user')).toBeNull();
    expect(localStorage.getItem('yzh_username')).toBeNull();
    expect(localStorage.getItem('yzh_role')).toBeNull();
  });

  it('从 localStorage 恢复状态', () => {
    localStorage.setItem('yzh_token', 'saved-token');
    localStorage.setItem('yzh_user', '99');
    localStorage.setItem('yzh_username', 'testuser');
    localStorage.setItem('yzh_role', '0');
    setActivePinia(createPinia());
    const store = useAuthStore();
    expect(store.token).toBe('saved-token');
    expect(store.userId).toBe('99');
    expect(store.username).toBe('testuser');
    expect(store.role).toBe(0);
  });
});