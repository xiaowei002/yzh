<template>
  <el-upload
    :show-file-list="false"
    :before-upload="beforeUpload"
    :http-request="handleRequest"
    accept="*"
  >
    <el-button type="primary" :loading="uploading">上传</el-button>
  </el-upload>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const props = defineProps<{
  request: (file: File) => Promise<any>;
}>();

const uploading = ref(false);

const beforeUpload = () => true;

const handleRequest = async (options: any) => {
  uploading.value = true;
  try {
    await props.request(options.file as File);
  } finally {
    uploading.value = false;
  }
};
</script>


