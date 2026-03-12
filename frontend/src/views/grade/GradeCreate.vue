<template>
  <el-card>
    <h3>新建班级</h3>
    <crud-form :model="form" :rules="rules" :submit="onSubmit" @submitted="onSaved">
      <el-form-item label="学院" prop="college">
        <el-input v-model="form.college" />
      </el-form-item>
      <el-form-item label="年级" prop="gradeName">
        <el-input v-model="form.gradeName" />
      </el-form-item>
      <el-form-item label="班级" prop="className">
        <el-input v-model="form.className" />
      </el-form-item>
    </crud-form>
  </el-card>
</template>

<script setup lang="ts">
import { reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import CrudForm from '@/components/CrudForm.vue';
import { createGrade } from '@/api/grade';

const router = useRouter();

const form = reactive({
  college: '',
  gradeName: '',
  className: ''
});

const rules = {
  college: [{ required: true, message: '请输入学院', trigger: 'blur' }],
  gradeName: [{ required: true, message: '请输入年级', trigger: 'blur' }],
  className: [{ required: true, message: '请输入班级', trigger: 'blur' }]
};

const onSubmit = async () => {
  await createGrade(form as any);
  ElMessage.success('创建成功');
};

const onSaved = () => router.push('/grade/list');
</script>


