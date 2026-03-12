<template>
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
      <el-form-item label="学院">
        <el-input v-model="query.college" placeholder="学院" clearable />
      </el-form-item>
      <el-form-item label="年级">
        <el-input v-model="query.gradeName" placeholder="年级" clearable />
      </el-form-item>
      <el-form-item label="班级">
        <el-input v-model="query.className" placeholder="班级" clearable />
      </el-form-item>
    </template>
    <template #header-actions>
      <el-button type="primary" :icon="Plus" @click="$router.push('/grade/create')">新增班级</el-button>
    </template>
    <template #actions>
      <el-button type="primary" :icon="Plus" @click="$router.push('/grade/create')">新增班级</el-button>
    </template>
    <template #row-actions="{ row }">
      <el-button size="small" :icon="View" @click="$router.push({ path: `/grade/detail/${row.id}`, state: row })">
        详情
      </el-button>
      <el-button size="small" type="primary" :icon="Edit" @click="$router.push({ path: `/grade/edit/${row.id}`, state: row })">
        编辑
      </el-button>
      <el-popconfirm title="确认删除该班级?" @confirm="remove(row)" width="200">
        <template #reference>
          <el-button size="small" type="danger" :icon="Delete">删除</el-button>
        </template>
      </el-popconfirm>
    </template>
  </crud-table>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import { Plus, View, Edit, Delete } from '@element-plus/icons-vue';
import CrudTable from '@/components/CrudTable.vue';
import { fetchGradePage, deleteGrade } from '@/api/grade';
import type { Grade } from '@/typings/backend';
import { formatDateTime } from '@/utils/format';

const query = ref({
  current: 1,
  size: 10,
  college: '',
  gradeName: '',
  className: ''
});

const columns = [
  { prop: 'college', label: '学院', minWidth: 120 },
  { prop: 'gradeName', label: '年级', minWidth: 100 },
  { prop: 'className', label: '班级', minWidth: 100 },
  { prop: 'createdAt', label: '创建时间', minWidth: 160 }
];

const records = ref<Grade[]>([]);
const total = ref(0);
const loading = ref(false);

const load = async () => {
  loading.value = true;
  try {
    const res = await fetchGradePage(query.value);
    records.value = res.records?.map((r) => ({
      ...r,
      createdAt: formatDateTime(r.createdAt)
    })) || [];
    total.value = res.total;
  } finally {
    loading.value = false;
  }
};

const reset = () => {
  query.value = { current: 1, size: 10, college: '', gradeName: '', className: '' };
  load();
};

const remove = async (row: Grade) => {
  await deleteGrade(String(row.id));
  ElMessage.success('删除成功');
  load();
};
</script>


