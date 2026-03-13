import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import { useAuthStore } from '@/stores/auth';

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/auth/Login.vue')
  },
  {
    path: '/user/create',
    name: 'UserCreate',
    component: () => import('@/views/user/UserCreate.vue'),
    meta: { title: '注册' }
  },
  {
    path: '/',
    redirect: '/dashboard'
  },
  {
    path: '/',
    component: () => import('@/views/layouts/AdminLayout.vue'),
    children: [
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/Dashboard.vue'),
        meta: { title: '仪表盘' }
      },
      {
        path: '/user/list',
        name: 'UserList',
        component: () => import('@/views/user/UserList.vue'),
        meta: { title: '用户列表' }
      },
      {
        path: '/user/edit/:id',
        name: 'UserEdit',
        component: () => import('@/views/user/UserEdit.vue'),
        meta: { title: '编辑用户' }
      },
      {
        path: '/user/detail/:id',
        name: 'UserDetail',
        component: () => import('@/views/user/UserDetail.vue'),
        meta: { title: '用户详情' }
      },
      {
        path: '/grade/list',
        name: 'GradeList',
        component: () => import('@/views/grade/GradeList.vue'),
        meta: { title: '班级列表' }
      },
      {
        path: '/grade/list',
        name: 'GradeList',
        component: () => import('@/views/grade/GradeList.vue'),
        meta: { title: '班级列表' }
      },
      {
        path: '/grade/detail/:id',
        name: 'GradeDetail',
        component: () => import('@/views/grade/GradeDetail.vue'),
        meta: { title: '班级详情' }
      },
      {
        path: '/task/list',
        name: 'TaskList',
        component: () => import('@/views/task/TaskList.vue'),
        meta: { title: '任务列表' }
      },
      {
        path: '/task/create',
        name: 'TaskCreate',
        component: () => import('@/views/task/TaskCreate.vue'),
        meta: { title: '新增任务' }
      },
      {
        path: '/task/edit/:id',
        name: 'TaskEdit',
        component: () => import('@/views/task/TaskEdit.vue'),
        meta: { title: '编辑任务' }
      },
      {
        path: '/task/detail/:id',
        name: 'TaskDetail',
        component: () => import('@/views/task/TaskDetail.vue'),
        meta: { title: '任务详情' }
      },
      {
        path: '/task/upload/:id',
        name: 'TaskUpload',
        component: () => import('@/views/task/TaskUpload.vue'),
        meta: { title: '提交任务' }
      },
      {
        path: '/task/statistic',
        name: 'TaskStatistic',
        component: () => import('@/views/task/TaskStatistic.vue'),
        meta: { title: '任务统计' }
      }
    ]
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, _from, next) => {
  const auth = useAuthStore();
  // 不需要认证的页面
  const publicPaths = ['/login', '/user/create'];
  if (publicPaths.includes(to.path)) return next();
  if (!auth.token) {
    return next('/login');
  }
  next();
});

export default router;


