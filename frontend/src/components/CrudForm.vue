<template>
  <el-form :model="model" :rules="rules" :label-width="labelWidth" ref="formRef">
    <slot />
    <el-form-item>
      <el-button type="primary" @click="onSubmit" :loading="loading">保存</el-button>
      <el-button @click="$emit('cancel')">取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const props = defineProps<{
  model: Record<string, any>;
  rules?: any;
  submit: () => Promise<void>;
  labelWidth?: string;
}>();

const emit = defineEmits<{
  (e: 'cancel'): void;
  (e: 'submitted'): void;
}>();

const loading = ref(false);
const formRef = ref();

const onSubmit = async () => {
  await formRef.value.validate(async (valid: boolean) => {
    if (!valid) return;
    loading.value = true;
    try {
      await props.submit();
      emit('submitted');
    } finally {
      loading.value = false;
    }
  });
};

// 暴露 formRef 给父组件
defineExpose({
  formRef,
  validateField: (prop: string) => formRef.value?.validateField(prop)
});
</script>


