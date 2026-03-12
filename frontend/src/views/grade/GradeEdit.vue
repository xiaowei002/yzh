<template>
  <div class="edit-container">
    <el-card class="premium-card">
      <template #header>
        <div class="card-header">
          <span class="card-title">编辑班级信息</span>
        </div>
      </template>
      <div class="form-wrapper">
        <crud-form :model="form" :rules="rules" :submit="onSubmit" @submitted="onSaved">
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="学院名称" prop="college">
                <el-input v-model="form.college" placeholder="请输入学院名称" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="年级" prop="gradeName">
                <el-input v-model="form.gradeName" placeholder="如：2021级" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="班级名称" prop="className">
                <el-input v-model="form.className" placeholder="如：1班" />
              </el-form-item>
            </el-col>
          </el-row>
        </crud-form>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { reactive, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import CrudForm from '@/components/CrudForm.vue';
import { updateGrade } from '@/api/grade';
import type { Grade } from '@/typings/backend';

const route = useRoute();
const router = useRouter();

const form = reactive<Partial<Grade>>({
  id: String(route.params.id),
  college: '',
  gradeName: '',
  className: ''
});

const rules = {
  college: [{ required: true, message: '请输入学院', trigger: 'blur' }],
  gradeName: [{ required: true, message: '请输入年级', trigger: 'blur' }],
  className: [{ required: true, message: '请输入班级', trigger: 'blur' }]
};

onMounted(() => {
  const state = history.state?.state as Grade | undefined;
  if (state) {
    Object.assign(form, state);
  }
});

const onSubmit = async () => {
  try {
    await updateGrade(form as Grade);
    ElMessage.success('更新成功');
    return true;
  } catch (e) {
    return false;
  }
};

const onSaved = () => router.push('/grade/list');
</script>

<style scoped>
.edit-container {
  padding: 24px;
}

.premium-card {
  border-radius: 20px;
  border: none;
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
  box-shadow: 0 8px 32px rgba(31, 38, 135, 0.07);
}

.card-header {
  padding: 8px 0;
}

.card-title {
  font-size: 18px;
  font-weight: 700;
  color: #1e293b;
  position: relative;
  padding-left: 12px;
}

.card-title::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 18px;
  background: #10b981;
  border-radius: 2px;
}

.form-wrapper {
  padding: 12px;
}

::v-deep(.el-input__wrapper) {
  border-radius: 10px;
  box-shadow: 0 0 0 1px #e2e8f0 inset;
  padding: 4px 12px;
}

::v-deep(.el-form-item__label) {
  font-weight: 600;
  color: #64748b;
  margin-bottom: 8px;
}
</style>


