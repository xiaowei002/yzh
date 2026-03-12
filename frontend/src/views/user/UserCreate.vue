<template>
  <div class="register-container">
    <div class="register-background">
      <div class="background-shapes">
        <div class="shape shape-1"></div>
        <div class="shape shape-2"></div>
        <div class="shape shape-3"></div>
      </div>
    </div>
    <div class="register-content">
      <el-card class="register-card" shadow="always">
        <div class="register-header">
          <div class="logo">
            <el-icon :size="48" color="#67C23A"><UserFilled /></el-icon>
          </div>
          <h2 class="title">加入我们</h2>
          <p class="subtitle">创建您的 Yzh 任务管理账户</p>
        </div>

        <el-form :model="form" :rules="rules" ref="formRef" label-width="0" class="register-form">
          <el-form-item prop="username">
            <el-input
              v-model="form.username"
              placeholder="请输入用户名"
              size="large"
              :prefix-icon="User"
              clearable
            />
          </el-form-item>
          <el-form-item prop="nickname">
            <el-input
              v-model="form.nickname"
              placeholder="请输入昵称"
              size="large"
              :prefix-icon="CircleCheck"
              clearable
            />
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              v-model="form.password"
              type="password"
              placeholder="设置密码 (不少于6位)"
              size="large"
              :prefix-icon="Lock"
              show-password
            />
          </el-form-item>
          <el-form-item prop="confirmPassword">
            <el-input
              v-model="form.confirmPassword"
              type="password"
              placeholder="确认您的密码"
              size="large"
              :prefix-icon="Key"
              show-password
            />
          </el-form-item>
          
          <el-form-item class="register-button-item">
            <el-button
              type="success"
              @click="handleManualSubmit"
              :loading="loading"
              size="large"
              class="register-button"
            >
              {{ loading ? '注册中...' : '立即注册' }}
            </el-button>
          </el-form-item>
        </el-form>

        <div class="register-footer">
          <el-link type="info" :underline="false" @click="goToLogin">已有账号？</el-link>
          <el-divider direction="vertical" />
          <el-link type="primary" :underline="false" @click="goToLogin">立即登录</el-link>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { User, Lock, UserFilled, CircleCheck, Key } from '@element-plus/icons-vue';
import { createUser, checkUserExist } from '@/api/user';
import { encryptPassword } from '@/utils/encrypt';

const router = useRouter();
const formRef = ref();
const loading = ref(false);

const form = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  nickname: '',
  role: 0,
  status: 1
});

const validateConfirmPassword = (_rule: any, value: string, callback: any) => {
  if (value === '') {
    callback(new Error('请再次输入密码'));
  } else if (value !== form.password) {
    callback(new Error('两次输入的密码不一致'));
  } else {
    callback();
  }
};

watch(
  () => form.password,
  () => {
    if (form.confirmPassword && formRef.value) {
      formRef.value.validateField('confirmPassword');
    }
  }
);

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    {
      async validator(_rule: any, value: string, callback: any) {
        if (!value) return callback();
        try {
          const exists = await checkUserExist(value);
          if (exists) callback(new Error('用户名已存在'));
          else callback();
        } catch (e) {
          callback();
        }
      },
      trigger: 'blur'
    }
  ],
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: ['blur', 'change'] }
  ]
};

const handleManualSubmit = () => {
  formRef.value.validate(async (valid: boolean) => {
    if (!valid) return;
    loading.value = true;
    try {
      const registerData = {
        username: form.username,
        password: encryptPassword(form.password),
        nickname: form.nickname,
        role: 0,
        status: 1
      };
      await createUser(registerData as any);
      ElMessage.success('注册成功，请登录');
      router.push('/login');
    } catch (e) {
      // 错误已由拦截器处理
    } finally {
      loading.value = false;
    }
  });
};

const goToLogin = () => {
  router.push('/login');
};
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  overflow: hidden;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.register-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  z-index: 0;
}

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

@keyframes float {
  0%, 100% { transform: translate(0, 0) rotate(0deg); }
  33% { transform: translate(30px, -30px) rotate(120deg); }
  66% { transform: translate(-20px, 20px) rotate(240deg); }
}

.register-content {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 480px;
  padding: 20px;
}

.register-card {
  border-radius: 16px;
  backdrop-filter: blur(10px);
  background: rgba(255, 255, 255, 0.95);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.register-card ::v-deep(.el-card__body) {
  padding: 40px;
}

.register-header {
  text-align: center;
  margin-bottom: 32px;
}

.register-header .logo {
  margin-bottom: 16px;
}

.register-header .title {
  font-size: 28px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 8px 0;
}

.register-header .subtitle {
  font-size: 14px;
  color: #909399;
  margin: 0;
}

.register-form .el-form-item {
  margin-bottom: 20px;
}

.register-form ::v-deep(.el-input__wrapper) {
  border-radius: 8px;
  box-shadow: 0 0 0 1px #dcdfe6 inset;
}

.register-form .register-button-item {
  display: flex;
  justify-content: center;
  margin-top: 12px;
}

.register-form .register-button {
  height: 44px;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  width: 100%;
}

.register-footer {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 24px;
  font-size: 14px;
}

.register-footer .el-divider {
  margin: 0 12px;
}
</style>


