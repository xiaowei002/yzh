<template>
  <el-card>
    <h3>任务详情</h3>
    <el-descriptions :column="2" border>
      <el-descriptions-item label="名称">{{ task.name }}</el-descriptions-item>
      <el-descriptions-item label="类型">{{ task.type }}</el-descriptions-item>
      <el-descriptions-item label="截止时间">{{ task.deadLine }}</el-descriptions-item>
      <el-descriptions-item label="描述">{{ task.description }}</el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ task.createdAt }}</el-descriptions-item>
    </el-descriptions>
  </el-card>
</template>

<script setup lang="ts">
import { reactive, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import type { TaskList } from '@/typings/backend';
import { formatDateTime } from '@/utils/format';

const route = useRoute();
const task = reactive<Partial<TaskList>>({
  id: String(route.params.id),
  name: '',
  type: '',
  deadLine: '',
  description: '',
  createdAt: ''
});

onMounted(() => {
  const state = history.state?.state as TaskList | undefined;
  if (state) {
    Object.assign(task, state, { deadLine: formatDateTime(state.deadLine), createdAt: formatDateTime(state.createdAt) });
  }
});
</script>


