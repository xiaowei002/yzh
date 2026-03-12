<template>
  <div class="edit-container">
    <el-card class="premium-card">
      <template #header>
        <div class="card-header">
          <span class="card-title">编辑任务</span>
        </div>
      </template>
      <div class="form-wrapper">
        <crud-form :model="form" :rules="rules" :submit="onSubmit" @submitted="onSaved">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="名称" prop="name">
                <el-input v-model="form.name" placeholder="请输入任务名称" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="任务类型" prop="type">
                <el-select v-model="form.type" placeholder="请选择类型" style="width: 100%">
                  <el-option label="作业" value="作业" />
                  <el-option label="考试" value="考试" />
                  <el-option label="项目" value="项目" />
                  <el-option label="其他" value="其他" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="截止时间" prop="deadLine">
                <el-date-picker
                  v-model="form.deadLine"
                  type="datetime"
                  placeholder="选择截止时间"
                  style="width: 100%"
                  value-format="YYYY-MM-DD HH:mm:ss"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="任务描述">
                <el-input
                  type="textarea"
                  v-model="form.description"
                  :rows="4"
                  placeholder="请输入任务详细描述..."
                />
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
import { updateTask } from '@/api/task';
import type { TaskList } from '@/typings/backend';

const route = useRoute();
const router = useRouter();

const form = reactive<Partial<TaskList>>({
  id: String(route.params.id),
  name: '',
  deadLine: '',
  type: '',
  description: ''
});

const rules = {
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  type: [{ required: true, message: '请输入类型', trigger: 'blur' }],
  deadLine: [{ required: true, message: '请选择截止时间', trigger: 'change' }]
};

onMounted(() => {
  const state = history.state?.state as TaskList | undefined;
  if (state) {
    Object.assign(form, state);
  }
});

const onSubmit = async () => {
  try {
    await updateTask(form as TaskList);
    ElMessage.success('更新成功');
    return true;
  } catch (e) {
    return false;
  }
};

const onSaved = () => router.push('/task/list');
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
  background: #f59e0b;
  border-radius: 2px;
}

.form-wrapper {
  padding: 12px;
}

::v-deep(.el-input__wrapper),
::v-deep(.el-textarea__inner) {
  border-radius: 10px;
  box-shadow: 0 0 0 1px #e2e8f0 inset;
  padding: 8px 12px;
}

::v-deep(.el-form-item__label) {
  font-weight: 600;
  color: #64748b;
  margin-bottom: 8px;
}
</style>


