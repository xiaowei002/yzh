<template>
  <div class="profile-container">
    <el-card class="premium-card">
      <template #header>
        <div class="card-header">
          <span class="card-title">个人中心</span>
        </div>
      </template>
      <div class="form-wrapper">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名">
              <el-input :model-value="auth.username" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色">
              <el-input :model-value="auth.role === 1 ? '管理员' : '普通用户'" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" v-if="userInfo">
          <el-col :span="12">
            <el-form-item label="昵称">
              <el-input :model-value="userInfo.nickname" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话">
              <el-input :model-value="userInfo.phone || '未设置'" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" v-if="userInfo">
          <el-col :span="12">
            <el-form-item label="邮箱">
              <el-input :model-value="userInfo.email || '未设置'" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="注册时间">
              <el-input :model-value="formatDateTime(userInfo.createdAt)" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-empty v-if="!userInfo && !loading" description="无法加载用户信息" />
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { getUserById } from '@/api/user';
import type { User } from '@/typings/backend';
import { formatDateTime } from '@/utils/format';

const auth = useAuthStore();
const userInfo = ref<User | null>(null);
const loading = ref(false);

onMounted(async () => {
  if (auth.userId) {
    loading.value = true;
    try {
      const data = await getUserById(auth.userId);
      userInfo.value = data || null;
    } catch (e) { /* ignore */ }
    finally { loading.value = false; }
  }
});
</script>

<style scoped>
.profile-container { padding: 24px; }
.premium-card { border-radius: 20px; border: none; background: rgba(255,255,255,0.8); backdrop-filter: blur(10px); box-shadow: 0 8px 32px rgba(31,38,135,0.07); }
.card-header { padding: 8px 0; }
.card-title { font-size: 18px; font-weight: 700; color: #1e293b; padding-left: 12px; position: relative; }
.card-title::before { content: ''; position: absolute; left: 0; top: 50%; transform: translateY(-50%); width: 4px; height: 18px; background: #409EFF; border-radius: 2px; }
.form-wrapper { padding: 12px; }
.form-wrapper ::v-deep(.el-input__wrapper) { border-radius: 10px; }
.form-wrapper ::v-deep(.el-form-item__label) { font-weight: 600; color: #64748b; }
.form-wrapper ::v-deep(.el-form-item) { margin-bottom: 24px; }
</style>