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
      <el-form-item label="用户名">
        <el-input v-model="query.username" placeholder="用户名" clearable />
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="query.nickname" placeholder="昵称" clearable />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="query.status" placeholder="全部" clearable>
          <el-option :value="1" label="启用" />
          <el-option :value="0" label="禁用" />
        </el-select>
      </el-form-item>
    </template>
    <template #header-actions>
      <el-button type="primary" :icon="Plus" @click="$router.push('/user/create')">新增用户</el-button>
    </template>
    <template #actions>
      <el-button type="primary" :icon="Plus" @click="$router.push('/user/create')">新增用户</el-button>
    </template>
    <template #row-actions="{ row }">
      <el-button size="small" :icon="View" @click="$router.push(`/user/detail/${row.id}`)">详情</el-button>
      <el-button size="small" type="primary" :icon="Edit" @click="$router.push({ path: `/user/edit/${row.id}`, state: row })">
        编辑
      </el-button>
      <el-popconfirm title="确认删除该用户?" @confirm="remove(row)" width="200">
        <template #reference>
          <el-button size="small" type="danger" :icon="Delete">删除</el-button>
        </template>
      </el-popconfirm>
      <el-switch
        v-model="row.status"
        :active-value="1"
        :inactive-value="0"
        inline-prompt
        active-text="启用"
        inactive-text="禁用"
        @change="toggleStatus(row)"
      />
    </template>
  </crud-table>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import { Plus, View, Edit, Delete } from '@element-plus/icons-vue';
import CrudTable from '@/components/CrudTable.vue';
import { fetchUserPage, deleteUser, enableUser, forbidUser } from '@/api/user';
import type { User } from '@/typings/backend';
import { formatDateTime } from '@/utils/format';

const query = ref({
  current: 1,
  size: 10,
  username: '',
  nickname: '',
  status: undefined as number | undefined
});

const columns = [
  { prop: 'username', label: '用户名' },
  { prop: 'nickname', label: '昵称' },
  { prop: 'phone', label: '电话' },
  { prop: 'email', label: '邮箱' },
  { prop: 'role', label: '角色' },
  { prop: 'status', label: '状态' },
  { prop: 'createdAt', label: '创建时间' }
];

const records = ref<User[]>([]);
const total = ref(0);
const loading = ref(false);

const load = async () => {
  loading.value = true;
  try {
    const res = await fetchUserPage(query.value);
    records.value = res.records?.map((r) => ({
      ...r,
      createdAt: formatDateTime(r.createdAt),
      role: r.role === 1 ? '管理员' : '普通用户',
      status: r.status === 1 ? '启用' : '禁用'
    }));
    total.value = res.total;
  } finally {
    loading.value = false;
  }
};

const reset = () => {
  query.value = { current: 1, size: 10, username: '', nickname: '', status: undefined };
  load();
};

const remove = async (row: User) => {
  await deleteUser(String(row.id));
  ElMessage.success('删除成功');
  load();
};

const toggleStatus = async (row: User) => {
  try {
    if (row.status === 1) {
      await enableUser(String(row.id));
      ElMessage.success('已启用');
    } else {
      await forbidUser(String(row.id));
      ElMessage.success('已禁用');
    }
    load();
  } catch (e) {
    // 恢复状态
    row.status = row.status === 1 ? 0 : 1;
  }
};
</script>


