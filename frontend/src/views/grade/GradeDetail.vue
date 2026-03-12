<template>
  <el-card>
    <h3>班级详情</h3>
    <el-descriptions :column="2" border>
      <el-descriptions-item label="学院">{{ grade.college }}</el-descriptions-item>
      <el-descriptions-item label="年级">{{ grade.gradeName }}</el-descriptions-item>
      <el-descriptions-item label="班级">{{ grade.className }}</el-descriptions-item>
    </el-descriptions>
  </el-card>
</template>

<script setup lang="ts">
import { reactive, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import type { Grade } from '@/typings/backend';

const route = useRoute();
const grade = reactive<Partial<Grade>>({
  id: String(route.params.id),
  college: '',
  gradeName: '',
  className: ''
});

onMounted(() => {
  const state = history.state?.state as Grade | undefined;
  if (state) Object.assign(grade, state);
});
</script>


