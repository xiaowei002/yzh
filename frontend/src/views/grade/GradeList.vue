<template>
  <crud-table
    :columns="columns"
    :query="query"
    :data="records"
    :total="total"
    :fetch="load"
    :loading="loading"
    :action-width="180"
    @reset="reset"
  >
    <template #header-title>班级管理</template>
    <template #header-description>查看和管理全校班级架构信息</template>
    <template #search-fields>
      <el-form-item label="学院">
        <el-input v-model="query.college" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="年级">
        <el-input v-model="query.gradeName" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="班级">
        <el-input v-model="query.className" placeholder="请输入" clearable />
      </el-form-item>
    </template>
    <template #header-actions>
      <el-button type="primary" :icon="Plus" @click="handleAdd">新增</el-button>
    </template>

    <template #row-actions="{ row }">
      <el-button link type="primary" @click="handleEdit(row)">
        编辑
      </el-button>
      <el-popconfirm title="确认删除该班级?" @confirm="remove(row)" width="200">
        <template #reference>
          <el-button link type="danger">删除</el-button>
        </template>
      </el-popconfirm>
    </template>

    <template #modals>
      <el-dialog
        v-model="dialogVisible"
        :title="form.id ? '编辑班级' : '新增班级'"
        width="480px"
        class="custom-dialog"
        :close-on-click-modal="false"
      >
        <el-form :model="form" label-width="80px" class="modal-form">
          <el-form-item label="学院" required>
            <el-input v-model="form.college" placeholder="请输入学院" />
          </el-form-item>
          <el-form-item label="年级" required>
            <el-input v-model="form.gradeName" placeholder="请输入年级" />
          </el-form-item>
          <el-form-item label="班级" required>
            <el-input v-model="form.className" placeholder="请输入班级" />
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="save" :loading="saving">确认</el-button>
          </div>
        </template>
      </el-dialog>
    </template>
  </crud-table>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import { Plus } from '@element-plus/icons-vue';
import CrudTable from '@/components/CrudTable.vue';
import { fetchGradePage, deleteGrade, createGrade, updateGrade } from '@/api/grade';
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

const dialogVisible = ref(false);
const saving = ref(false);
const form = ref<Partial<Grade>>({
  college: '',
  gradeName: '',
  className: ''
});

const handleAdd = () => {
  form.value = { college: '', gradeName: '', className: '' };
  dialogVisible.value = true;
};

const handleEdit = (row: Grade) => {
  form.value = { ...row };
  dialogVisible.value = true;
};

const save = async () => {
  if (!form.value.college || !form.value.gradeName || !form.value.className) {
    ElMessage.warning('请填写完整信息');
    return;
  }
  saving.value = true;
  try {
    if (form.value.id) {
      await updateGrade(form.value as Grade);
      ElMessage.success('编辑成功');
    } else {
      await createGrade(form.value as Grade);
      ElMessage.success('新增成功');
    }
    dialogVisible.value = false;
    load();
  } finally {
    saving.value = false;
  }
};
</script>

<style scoped>
.modal-form {
  padding: 10px 20px 0;
}

.modal-form ::v-deep(.el-form-item) {
  margin-bottom: 24px;
}

.modal-form ::v-deep(.el-form-item__label) {
  font-weight: 500;
  color: #666;
}

.modal-form ::v-deep(.el-input__wrapper) {
  background-color: #f2f3f5 !important;
  box-shadow: none !important;
  border: none !important;
  border-radius: 4px;
  height: 40px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  padding: 0 20px 10px;
}

.dialog-footer .el-button {
  padding: 0 24px;
  height: 36px;
  border-radius: 4px;
  font-weight: 500;
}

.dialog-footer .el-button--primary {
  background-color: #1677ff !important;
}

.dialog-footer .el-button:not(.el-button--primary) {
  background-color: #f5f5f5;
  border-color: #f0f0f0;
  color: #666;
}
</style>


