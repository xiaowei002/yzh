<template>
  <el-card v-loading="loading">
    <h3>用户详情</h3>
    <el-descriptions :column="2" border>
      <el-descriptions-item label="用户名">{{ user.username }}</el-descriptions-item>
      <el-descriptions-item label="昵称">{{ user.nickname }}</el-descriptions-item>
      <el-descriptions-item label="电话">{{ user.phone }}</el-descriptions-item>
      <el-descriptions-item label="邮箱">{{ user.email }}</el-descriptions-item>
      <el-descriptions-item label="角色">{{ user.role }}</el-descriptions-item>
      <el-descriptions-item label="状态">{{ user.status }}</el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ user.createdAt }}</el-descriptions-item>
    </el-descriptions>
  </el-card>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { getUserById } from '@/api/user';
import type { User } from '@/typings/backend';
import { formatDateTime } from '@/utils/format';

const route = useRoute();
const loading = ref(false);
const user = reactive<Partial<User>>({
  id: String(route.params.id),
  username: '',
  nickname: '',
  phone: '',
  email: '',
  role: '',
  status: '',
  createdAt: ''
});

onMounted(async () => {
  const state = history.state?.state as User | undefined;
  if (state) {
    Object.assign(user, state, { createdAt: formatDateTime(state.createdAt) });
  } else {
    loading.value = true;
    try {
      const data = await getUserById(String(route.params.id));
      if (data) Object.assign(user, data, { createdAt: formatDateTime(data.createdAt) });
    } catch (e) { /* ignore */ }
    finally { loading.value = false; }
  }
});
</script>


