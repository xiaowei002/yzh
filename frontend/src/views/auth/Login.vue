<template>
  <div class="login-container">
    <div class="login-background">
      <div class="background-shapes">
        <div class="shape shape-1"></div>
        <div class="shape shape-2"></div>
        <div class="shape shape-3"></div>
      </div>
    </div>
    <div class="login-content">
      <el-card class="login-card" shadow="always">
        <div class="login-header">
          <div class="logo">
            <el-icon :size="48" color="#409EFF"><UserFilled /></el-icon>
          </div>
          <h2 class="title">欢迎登录</h2>
          <p class="subtitle">Yzh 任务管理系统</p>
        </div>
        <el-form :model="form" :rules="rules" ref="formRef" label-width="0" class="login-form">
          <el-form-item prop="username">
            <el-input
              v-model="form.username"
              placeholder="请输入用户名"
              size="large"
              :prefix-icon="User"
              clearable
            />
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              v-model="form.password"
              type="password"
              placeholder="请输入密码"
              size="large"
              :prefix-icon="Lock"
              show-password
              @keyup.enter="onSubmit"
            />
          </el-form-item>
          <el-form-item class="login-button-item">
            <el-button
              type="primary"
              @click="onSubmit"
              :loading="loading"
              size="large"
              class="login-button"
            >
              {{ loading ? '登录中...' : '登录' }}
            </el-button>
          </el-form-item>
        </el-form>
        <div class="login-footer">
          <el-link type="info" :underline="false">忘记密码？</el-link>
          <el-divider direction="vertical" />
          <el-link type="primary" :underline="false" @click="goToRegister">立即注册</el-link>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { User, Lock, UserFilled } from '@element-plus/icons-vue';
import { login } from '@/api/auth';
import { useAuthStore } from '@/stores/auth';

const router = useRouter();
const auth = useAuthStore();

const loading = ref(false);
const formRef = ref();
const form = reactive({
  username: '',
  password: ''
});

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
};

const onSubmit = () => {
  formRef.value.validate(async (valid: boolean) => {
    if (!valid) return;
    loading.value = true;
    try {
      const token = await login(form.username, form.password);
      auth.setAuth(token);
      ElMessage.success('登录成功');
      router.push('/dashboard');
    } finally {
      loading.value = false;
    }
  });
};

const goToRegister = () => {
  router.push('/user/create');
};
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  overflow: hidden;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  z-index: 0;

  .background-shapes {
    position: relative;
    width: 100%;
    height: 100%;
  }

  .shape {
    position: absolute;
    border-radius: 50%;
    background: rgba(255, 255, 255, 0.1);
    animation: float 20s infinite ease-in-out;
  }

  .shape.shape-1 {
    width: 300px;
    height: 300px;
    top: -100px;
    left: -100px;
    animation-delay: 0s;
  }

  .shape.shape-2 {
    width: 200px;
    height: 200px;
    bottom: -50px;
    right: -50px;
    animation-delay: 5s;
  }

  .shape.shape-3 {
    width: 150px;
    height: 150px;
    top: 50%;
    right: 10%;
    animation-delay: 10s;
  }
}

@keyframes float {
  0%,
  100% {
    transform: translate(0, 0) rotate(0deg);
  }
  33% {
    transform: translate(30px, -30px) rotate(120deg);
  }
  66% {
    transform: translate(-20px, 20px) rotate(240deg);
  }
}

.login-content {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 420px;
  padding: 20px;
}

.login-card {
  border-radius: 16px;
  backdrop-filter: blur(10px);
  background: rgba(255, 255, 255, 0.95);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.login-card ::v-deep(.el-card__body) {
  padding: 40px;
}

.login-header {
  text-align: center;
  margin-bottom: 32px;
}

.login-header .logo {
  margin-bottom: 16px;
}

.login-header .title {
  font-size: 28px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 8px 0;
}

.login-header .subtitle {
  font-size: 14px;
  color: #909399;
  margin: 0;
}

.login-form .el-form-item {
  margin-bottom: 20px;
}

.login-form ::v-deep(.el-input__wrapper) {
  border-radius: 8px;
  box-shadow: 0 0 0 1px #dcdfe6 inset;
}

.login-form ::v-deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px #c0c4cc inset;
}

.login-form ::v-deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 1px #409eff inset;
}

.login-form .login-button-item {
  display: flex;
  justify-content: center;
  margin-bottom: 0;
}

.login-form .login-button-item ::v-deep(.el-form-item__content) {
  margin-left: 0 !important;
  display: flex;
  justify-content: center;
}

.login-form .login-button {
  height: 44px;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  margin-top: 8px;
  width: 100%;
}

.login-footer {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 24px;
  font-size: 14px;
}

.login-footer .el-divider {
  margin: 0 12px;
}
</style>


