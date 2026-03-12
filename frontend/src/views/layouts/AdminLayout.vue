<template>
  <el-container class="layout">
    <el-aside :width="isCollapse ? '64px' : '220px'" class="sidebar">
      <div class="logo">
        <transition name="fade">
          <span v-if="!isCollapse" class="logo-text">Yzh 管理端</span>
          <span v-else class="logo-icon">Y</span>
        </transition>
      </div>
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :collapse-transition="false"
        router
        class="sidebar-menu"
        background-color="#1e293b"
        text-color="#94a3b8"
        active-text-color="#38bdf8"
      >
        <el-menu-item index="/dashboard">
          <el-icon><Odometer /></el-icon>
          <template #title>仪表盘</template>
        </el-menu-item>
        <el-sub-menu index="user">
          <template #title>
            <el-icon><User /></el-icon>
            <span>用户管理</span>
          </template>
          <el-menu-item index="/user/list">
            <el-icon><List /></el-icon>
            <template #title>用户列表</template>
          </el-menu-item>
          <el-menu-item index="/user/create">
            <el-icon><Plus /></el-icon>
            <template #title>新增用户</template>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="grade">
          <template #title>
            <el-icon><School /></el-icon>
            <span>班级管理</span>
          </template>
          <el-menu-item index="/grade/list">
            <el-icon><List /></el-icon>
            <template #title>班级列表</template>
          </el-menu-item>
          <el-menu-item index="/grade/create">
            <el-icon><Plus /></el-icon>
            <template #title>新增班级</template>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="task">
          <template #title>
            <el-icon><Document /></el-icon>
            <span>任务管理</span>
          </template>
          <el-menu-item index="/task/list">
            <el-icon><List /></el-icon>
            <template #title>任务列表</template>
          </el-menu-item>
          <el-menu-item index="/task/create">
            <el-icon><Plus /></el-icon>
            <template #title>新增任务</template>
          </el-menu-item>
          <el-menu-item index="/task/statistic">
            <el-icon><TrendCharts /></el-icon>
            <template #title>任务统计</template>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-button
            :icon="isCollapse ? Expand : Fold"
            circle
            text
            @click="toggleCollapse"
            class="collapse-btn"
          />
          <el-breadcrumb separator="/" class="breadcrumb">
            <el-breadcrumb-item v-for="item in breadcrumbs" :key="item.path" :to="item.path">
              {{ item.title }}
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <div class="user-info">
              <el-avatar :size="32" :icon="UserFilled" />
              <span class="username">管理员</span>
              <el-icon class="arrow-down"><ArrowDown /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">
                  <el-icon><User /></el-icon>
                  个人中心
                </el-dropdown-item>
                <el-dropdown-item command="settings">
                  <el-icon><Setting /></el-icon>
                  系统设置
                </el-dropdown-item>
                <el-dropdown-item divided command="logout">
                  <el-icon><SwitchButton /></el-icon>
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main class="main-content">
        <router-view v-slot="{ Component }">
          <transition name="fade-transform" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import {
  Odometer,
  User,
  School,
  Document,
  List,
  Plus,
  Fold,
  Expand,
  UserFilled,
  ArrowDown,
  Setting,
  SwitchButton,
  TrendCharts
} from '@element-plus/icons-vue';
import { ElMessageBox } from 'element-plus';

const auth = useAuthStore();
const router = useRouter();
const route = useRoute();

const isCollapse = ref(false);

const activeMenu = computed(() => route.path);

const breadcrumbs = computed(() => {
  const matched = route.matched.filter((item) => item.meta && item.meta.title);
  const first = matched[0];
  if (!first || first.name !== 'Dashboard') {
    matched.unshift({ path: '/dashboard', meta: { title: '首页' } } as any);
  }
  return matched.map((item) => ({
    path: item.path,
    title: (item.meta?.title as string) || ''
  }));
});

const toggleCollapse = () => {
  isCollapse.value = !isCollapse.value;
};

const handleCommand = (command: string) => {
  switch (command) {
    case 'logout':
      ElMessageBox.confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          auth.clear();
          router.push('/login');
        })
        .catch(() => {});
      break;
    case 'profile':
      ElMessageBox.alert('个人中心功能开发中', '提示');
      break;
    case 'settings':
      ElMessageBox.alert('系统设置功能开发中', '提示');
      break;
  }
};
</script>

<style scoped>
.layout {
  height: 100vh;
  overflow: hidden;
}

.sidebar {
  background-color: #1e293b;
  transition: width 0.3s;
  overflow: hidden;
  box-shadow: 4px 0 10px rgba(0, 0, 0, 0.05);
}

.sidebar .logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #0f172a;
  border-bottom: 1px solid #1e293b;
}

.sidebar .logo .logo-text {
  color: #fff;
  font-size: 18px;
  font-weight: 600;
  white-space: nowrap;
}

.sidebar .logo .logo-icon {
  color: #409eff;
  font-size: 24px;
  font-weight: bold;
}

.sidebar .sidebar-menu {
  border: none;
  height: calc(100vh - 60px);
  overflow-y: auto;
}

.sidebar .sidebar-menu:not(.el-menu--collapse) {
  width: 220px;
}

.header {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  z-index: 1000;
}

.header .header-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.header .header-left .collapse-btn {
  font-size: 18px;
}

.header .header-left .breadcrumb {
  font-size: 14px;
}

.header .header-right .user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  padding: 4px 12px;
  border-radius: 4px;
  transition: background 0.3s;
}

.header .header-right .user-info:hover {
  background: #f5f7fa;
}

.header .header-right .user-info .username {
  font-size: 14px;
  color: #303133;
}

.header .header-right .user-info .arrow-down {
  font-size: 12px;
  color: #909399;
}

.main-content {
  background: linear-gradient(135deg, #f0f4f8 0%, #e2e8f0 100%);
  padding: 0;
  overflow-y: auto;
}

/* 过渡动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.fade-transform-enter-active,
.fade-transform-leave-active {
  transition: all 0.3s;
}

.fade-transform-enter-from {
  opacity: 0;
  transform: translateX(-30px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
</style>


