<template>
  <div class="statistic-container">
    <el-card class="premium-card">
      <template #header>
        <div class="card-header">
          <span class="card-title">逾期未完成统计</span>
          <div class="header-desc">自动扫描超过截止时间仍未提交任务的用户</div>
        </div>
      </template>
      
      <crud-table
        :columns="columns"
        :query="query"
        :data="records"
        :total="total"
        :fetch="load"
        :loading="loading"
        @reset="reset"
      >
        <template #search-fields>
          <el-form-item label="任务名称">
            <el-input v-model="query.taskName" placeholder="输入任务名搜索" clearable style="width: 200px" />
          </el-form-item>
        </template>
      </crud-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import CrudTable from '@/components/CrudTable.vue';
import { fetchStatisticPage } from '@/api/statistic';
import type { TaskStatistic } from '@/typings/backend';
import { formatDateTime } from '@/utils/format';

const query = ref({
  current: 1,
  size: 10,
  taskName: ''
});

const columns = [
  { prop: 'taskName', label: '任务名称', minWidth: 150 },
  { prop: 'userName', label: '未完成用户', minWidth: 120 },
  { prop: 'deadLine', label: '截止时间', minWidth: 160 },
];

const records = ref<TaskStatistic[]>([]);
const total = ref(0);
const loading = ref(false);

const load = async () => {
  loading.value = true;
  try {
    const res = await fetchStatisticPage(query.value);
    records.value = res.records?.map(r => ({
      ...r,
      deadLine: formatDateTime(r.deadLine)
    })) || [];
    total.value = res.total;
  } finally {
    loading.value = false;
  }
};

const reset = () => {
  query.value = { current: 1, size: 10, taskName: '' };
  load();
};
</script>

<style scoped>
.statistic-container {
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
  display: flex;
  flex-direction: column;
}

.card-title {
  font-size: 20px;
  font-weight: 700;
  color: #1e293b;
  position: relative;
  padding-left: 15px;
}

.card-title::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 5px;
  height: 20px;
  background: #ef4444;
  border-radius: 3px;
}

.header-desc {
  font-size: 13px;
  color: #94a3b8;
  margin-top: 4px;
  padding-left: 15px;
}

::v-deep(.el-table) {
  --el-table-header-bg-color: #f8fafc;
  border-radius: 12px;
  overflow: hidden;
}
</style>
