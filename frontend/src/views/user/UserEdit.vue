<template>
  <div class="edit-container">
    <el-card class="premium-card">
      <template #header>
        <div class="card-header">
          <span class="card-title">编辑用户信息</span>
        </div>
      </template>
      <div class="form-wrapper">
        <crud-form :model="form" :submit="onSubmit" @submitted="onSaved">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="用户名">
                <el-input v-model="form.username" disabled />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="昵称">
                <el-input v-model="form.nickname" placeholder="请输入昵称" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="电话">
                <el-input v-model="form.phone" placeholder="请输入电话" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="邮箱">
                <el-input v-model="form.email" placeholder="请输入邮箱" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="角色">
                <el-select v-model="form.role" placeholder="选择角色" style="width: 100%">
                  <el-option :value="1" label="管理员" />
                  <el-option :value="0" label="普通用户" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="状态">
                <el-select v-model="form.status" style="width: 100%">
                  <el-option :value="1" label="启用" />
                  <el-option :value="0" label="禁用" />
                </el-select>
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
import { updateUser } from '@/api/user';
import type { User } from '@/typings/backend';

const route = useRoute();
const router = useRouter();

const form = reactive<Partial<User>>({
  id: String(route.params.id),
  username: '',
  nickname: '',
  phone: '',
  email: '',
  role: 0,
  status: 1
});

onMounted(() => {
  const state = history.state?.state as User | undefined;
  if (state) {
    Object.assign(form, state);
  }
});

const onSubmit = async () => {
  try {
    await updateUser(form);
    ElMessage.success('更新成功');
    return true;
  } catch (e) {
    return false;
  }
};

const onSaved = () => router.push('/user/list');
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
  background: #38bdf8;
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


