<template>
  <div class="dashboard">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stats-row">
      <el-col :xs="24" :sm="12" :md="6" v-for="stat in stats" :key="stat.title">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <div class="stat-icon" :style="{ background: stat.color }">
              <el-icon :size="28">
                <component :is="stat.icon" />
              </el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stat.value }}</div>
              <div class="stat-title">{{ stat.title }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 快捷操作 -->
    <el-card class="quick-actions-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <span class="card-title">
            <el-icon><Lightning /></el-icon>
            快捷操作
          </span>
        </div>
      </template>
      <el-row :gutter="20">
        <el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="action in quickActions" :key="action.title">
          <div class="quick-action-item" @click="handleQuickAction(action)">
            <el-icon :size="32" :color="action.color">
              <component :is="action.icon" />
            </el-icon>
            <div class="action-title">{{ action.title }}</div>
          </div>
        </el-col>
      </el-row>
    </el-card>

    <!-- 最近活动 -->
    <el-row :gutter="20" class="content-row">
      <el-col :xs="24" :lg="12">
        <el-card class="recent-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="card-title">
                <el-icon><Clock /></el-icon>
                最近任务
              </span>
            </div>
          </template>
          <el-empty v-if="recentTasks.length === 0" description="暂无最近任务" />
          <el-timeline v-else>
            <el-timeline-item
              v-for="task in recentTasks"
              :key="task.id"
              :timestamp="task.deadLine"
              placement="top"
            >
              <el-card>
                <h4>{{ task.name }}</h4>
                <p>{{ task.description || '无描述' }}</p>
                <el-tag :type="getTaskTypeTag(task.type)">{{ task.type }}</el-tag>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>
      <el-col :xs="24" :lg="12">
        <el-card class="system-info-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="card-title">
                <el-icon><InfoFilled /></el-icon>
                系统信息
              </span>
            </div>
          </template>
          <div class="system-info">
            <div class="info-item">
              <span class="info-label">系统名称：</span>
              <span class="info-value">Yzh 任务管理系统</span>
            </div>
            <div class="info-item">
              <span class="info-label">当前用户：</span>
              <span class="info-value">{{ currentUser }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">登录时间：</span>
              <span class="info-value">{{ loginTime }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">系统版本：</span>
              <span class="info-value">v1.0.0</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import {
  User,
  School,
  Document,
  Lightning,
  Clock,
  InfoFilled,
  Plus,
  Edit,
  Search,
  Setting
} from '@element-plus/icons-vue';
import { fetchTaskPage } from '@/api/task';
import type { TaskList } from '@/typings/backend';
import { formatDateTime } from '@/utils/format';

const router = useRouter();
const auth = useAuthStore();

const stats = ref([
  { title: '用户总数', value: '0', icon: User, color: '#409EFF' },
  { title: '班级总数', value: '0', icon: School, color: '#67C23A' },
  { title: '任务总数', value: '0', icon: Document, color: '#E6A23C' },
  { title: '进行中', value: '0', icon: Clock, color: '#F56C6C' }
]);

const quickActions = [
  { title: '新增用户', icon: Plus, color: '#409EFF', path: '/user/create' },
  { title: '新增班级', icon: Plus, color: '#67C23A', path: '/grade/create' },
  { title: '新增任务', icon: Plus, color: '#E6A23C', path: '/task/create' },
  { title: '用户管理', icon: User, color: '#409EFF', path: '/user/list' },
  { title: '班级管理', icon: School, color: '#67C23A', path: '/grade/list' },
  { title: '任务管理', icon: Document, color: '#E6A23C', path: '/task/list' }
];

const recentTasks = ref<TaskList[]>([]);
const currentUser = ref('管理员');
const loginTime = ref(formatDateTime(new Date().toISOString()));

const loadRecentTasks = async () => {
  try {
    const res = await fetchTaskPage({ current: 1, size: 5 });
    recentTasks.value = res.records || [];
  } catch (e) {
    console.error('加载最近任务失败', e);
  }
};

const handleQuickAction = (action: any) => {
  router.push(action.path);
};

const getTaskTypeTag = (type: string) => {
  const typeMap: Record<string, string> = {
    作业: 'primary',
    考试: 'danger',
    项目: 'success',
    其他: 'info'
  };
  return typeMap[type] || 'info';
};

onMounted(() => {
  loadRecentTasks();
});
</script>

<style scoped>
.dashboard {
  padding: 24px;
  background: transparent;
  min-height: calc(100vh - 60px);
}

.stats-row {
  margin-bottom: 24px;
}

.stat-card {
  border-radius: 20px;
  border: none;
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
  box-shadow: 0 8px 32px rgba(31, 38, 135, 0.07);
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  cursor: pointer;
}

.stat-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 15px 35px rgba(31, 38, 135, 0.12);
}

.stat-card .stat-content {
  display: flex;
  align-items: center;
  gap: 20px;
}

.stat-card .stat-icon {
  width: 56px;
  height: 56px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.stat-card .stat-info {
  flex: 1;
}

.stat-card .stat-value {
  font-size: 32px;
  font-weight: 700;
  color: #1e293b;
  margin-bottom: 4px;
  line-height: 1;
}

.stat-card .stat-title {
  font-size: 14px;
  font-weight: 500;
  color: #64748b;
}

.quick-actions-card,
.recent-card,
.system-info-card {
  border-radius: 20px;
  border: none;
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
  box-shadow: 0 8px 32px rgba(31, 38, 135, 0.07);
  margin-bottom: 24px;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.card-title {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 18px;
  font-weight: 700;
  color: #1e293b;
}

.quick-action-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 30px 20px;
  border-radius: 16px;
  background: #f8fafc;
  cursor: pointer;
  transition: all 0.3s;
  margin-bottom: 20px;
}

.quick-action-item:hover {
  background: #ffffff;
  transform: scale(1.05);
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.05);
}

.quick-action-item .action-title {
  margin-top: 14px;
  font-size: 15px;
  font-weight: 600;
  color: #475569;
}

.content-row {
  margin-top: 12px;
}

.recent-card .el-timeline {
  padding-left: 0;
}

.recent-card h4 {
  margin: 0 0 8px 0;
  font-size: 16px;
  color: #1e293b;
}

.recent-card p {
  margin: 0 0 8px 0;
  color: #64748b;
  font-size: 14px;
}

.system-info .info-item {
  display: flex;
  padding: 16px 0;
  border-bottom: 1px solid #f1f5f9;
}

.system-info .info-item:last-child {
  border-bottom: none;
}

.system-info .info-item .info-label {
  width: 120px;
  color: #64748b;
  font-size: 14px;
}

.system-info .info-item .info-value {
  flex: 1;
  color: #1e293b;
  font-size: 14px;
  font-weight: 600;
}
</style>
