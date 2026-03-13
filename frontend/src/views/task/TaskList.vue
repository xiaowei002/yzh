<template>
  <crud-table
    :columns="columns"
    :query="query"
    :data="records"
    :total="total"
    :fetch="load"
    :loading="loading"
    :action-width="260"
    @reset="reset"
  >
    <template #header-title>任务管理</template>
    <template #header-description>发布、分配、追踪和导出学生任务及提交详情</template>
    <template #search-fields>
      <el-form-item label="名称">
        <el-input v-model="query.name" placeholder="请输入任务名称" clearable />
      </el-form-item>
      <el-form-item label="截止开始">
        <el-date-picker
          v-model="query.deadlineStart"
          type="datetime"
          placeholder="选择开始时间"
          value-format="YYYY-MM-DD HH:mm:ss"
          style="width: 100%"
        />
      </el-form-item>
      <el-form-item label="截止结束">
        <el-date-picker
          v-model="query.deadlineEnd"
          type="datetime"
          placeholder="选择结束时间"
          value-format="YYYY-MM-DD HH:mm:ss"
          style="width: 100%"
        />
      </el-form-item>
    </template>
    <template #header-actions>
      <el-button type="primary" :icon="Plus" @click="$router.push('/task/create')">新增</el-button>
    </template>

    <template #row-actions="{ row }">
      <el-button link type="primary" @click="$router.push({ path: `/task/detail/${row.id}`, state: row })">详情</el-button>
      <el-button link type="primary" @click="$router.push({ path: `/task/edit/${row.id}`, state: row })">编辑</el-button>
      <el-button link type="primary" @click="$router.push(`/task/upload/${row.id}`)">提交</el-button>
      <el-button link type="success" @click="download(row)">下载</el-button>
      <el-popconfirm title="确认删除该任务?" @confirm="remove(row)" width="200">
        <template #reference>
          <el-button link type="danger">删除</el-button>
        </template>
      </el-popconfirm>
    </template>
  </crud-table>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import { Plus, View, Edit, Delete, Upload, Download } from '@element-plus/icons-vue';
import CrudTable from '@/components/CrudTable.vue';
import { fetchTaskPage, deleteTask } from '@/api/task';
import { downloadTaskFiles } from '@/api/file';
import type { TaskList } from '@/typings/backend';
import { formatDateTime } from '@/utils/format';

const query = ref({
  current: 1,
  size: 10,
  name: '',
  deadlineStart: '',
  deadlineEnd: ''
});

const columns = [
  { prop: 'name', label: '名称', minWidth: 150 },
  { prop: 'type', label: '类型', minWidth: 100 },
  { prop: 'deadLine', label: '截止时间', minWidth: 160 },
  { prop: 'description', label: '描述', minWidth: 200 },
  { prop: 'createdAt', label: '创建时间', minWidth: 160 }
];

const records = ref<TaskList[]>([]);
const total = ref(0);
const loading = ref(false);

const load = async () => {
  loading.value = true;
  try {
    const res = await fetchTaskPage(query.value);
    records.value = res.records?.map((r) => ({
      ...r,
      deadLine: formatDateTime(r.deadLine),
      createdAt: formatDateTime(r.createdAt)
    })) || [];
    total.value = res.total;
  } finally {
    loading.value = false;
  }
};

const reset = () => {
  query.value = { current: 1, size: 10, name: '', deadlineStart: '', deadlineEnd: '' };
  load();
};

const remove = async (row: TaskList) => {
  await deleteTask(String(row.id));
  ElMessage.success('删除成功');
  load();
};

const download = async (row: TaskList) => {
  try {
    const blob = (await downloadTaskFiles(String(row.id))) as unknown as Blob;
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `${row.name}-files.zip`;
    a.click();
    window.URL.revokeObjectURL(url);
    ElMessage.success('下载成功');
  } catch (e) {
    ElMessage.error('下载失败');
  }
};
</script>


