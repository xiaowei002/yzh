<template>
  <el-card>
    <h3>提交任务</h3>
    <p>任务 ID：{{ taskId }}</p>
    <upload-button :request="onUpload" />
    <el-alert
      type="info"
      title="提交成功后可在下载页查看。"
      show-icon
      style="margin-top: 12px"
    />
  </el-card>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router';
import { ElMessage } from 'element-plus';
import UploadButton from '@/components/UploadButton.vue';
import { uploadTaskFile } from '@/api/file';

const route = useRoute();
const taskId = route.params.id as string;

const onUpload = async (file: File) => {
  await uploadTaskFile(taskId, file);
  ElMessage.success('上传成功');
};
</script>


