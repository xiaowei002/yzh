import { defineStore } from 'pinia';

interface AuthState {
  token: string | null;
  userId: string | null;
  role: number | null;
}

const TOKEN_KEY = 'yzh_token';
const USER_ID_KEY = 'yzh_user';
const ROLE_KEY = 'yzh_role';

export const useAuthStore = defineStore('auth', {
  state: (): AuthState => ({
    token: localStorage.getItem(TOKEN_KEY),
    userId: localStorage.getItem(USER_ID_KEY),
    role: localStorage.getItem(ROLE_KEY)
      ? Number(localStorage.getItem(ROLE_KEY))
      : null
  }),
  actions: {
    setAuth(token: string, userId?: string, role?: number) {
      this.token = token;
      if (userId) {
        this.userId = userId;
        localStorage.setItem(USER_ID_KEY, userId);
      }
      if (role !== undefined) {
        this.role = role;
        localStorage.setItem(ROLE_KEY, String(role));
      }
      localStorage.setItem(TOKEN_KEY, token);
    },
    clear() {
      this.token = null;
      this.userId = null;
      this.role = null;
      localStorage.removeItem(TOKEN_KEY);
      localStorage.removeItem(USER_ID_KEY);
      localStorage.removeItem(ROLE_KEY);
    }
  }
});


