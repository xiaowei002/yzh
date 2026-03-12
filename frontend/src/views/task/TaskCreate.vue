<template>
  <el-card>
    <h3>新建任务</h3>
    <crud-form :model="form" :rules="rules" :submit="onSubmit" @submitted="onSaved">
      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="截止时间" prop="deadLine">
        <el-date-picker v-model="form.deadLine" type="datetime" />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-input v-model="form.type" />
      </el-form-item>
      <el-form-item label="描述">
        <el-input type="textarea" v-model="form.description" />
      </el-form-item>
    </crud-form>
  </el-card>
</template>

<script setup lang="ts">
import { reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import CrudForm from '@/components/CrudForm.vue';
import { createTask } from '@/api/task';

const router = useRouter();

const form = reactive({
  name: '',
  deadLine: '',
  type: '',
  description: ''
});

const rules = {
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  deadLine: [{ required: true, message: '请选择截止时间', trigger: 'change' }],
  type: [{ required: true, message: '请输入类型', trigger: 'blur' }]
};

const onSubmit = async () => {
  await createTask(form as any);
  ElMessage.success('创建成功');
};

const onSaved = () => router.push('/task/list');
</script>


