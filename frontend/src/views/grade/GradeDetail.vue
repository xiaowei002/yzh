<template>
  <el-card v-loading="loading">
    <h3>班级详情</h3>
    <el-descriptions :column="2" border>
      <el-descriptions-item label="学院">{{ grade.college }}</el-descriptions-item>
      <el-descriptions-item label="年级">{{ grade.gradeName }}</el-descriptions-item>
      <el-descriptions-item label="班级">{{ grade.className }}</el-descriptions-item>
    </el-descriptions>
  </el-card>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { getGradeById } from '@/api/grade';
import type { Grade } from '@/typings/backend';

const route = useRoute();
const loading = ref(false);
const grade = reactive<Partial<Grade>>({
  id: String(route.params.id),
  college: '',
  gradeName: '',
  className: ''
});

onMounted(async () => {
  const state = history.state?.state as Grade | undefined;
  if (state) {
    Object.assign(grade, state);
  } else {
    loading.value = true;
    try {
      const data = await getGradeById(String(route.params.id));
      if (data) Object.assign(grade, data);
    } catch (e) { /* ignore */ }
    finally { loading.value = false; }
  }
});
</script>


