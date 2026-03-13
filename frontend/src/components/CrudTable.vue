<template>
  <div class="crud-table">
    <div class="page-meta">
      <div class="meta-left">
        <h2 class="main-title">
          <slot name="header-title">页面名称</slot>
        </h2>
        <span class="sub-title">
          <slot name="header-description">在此管理系统相关数据</slot>
        </span>
      </div>
    </div>

    <div class="search-section">
      <el-form :inline="true" :model="query" class="inline-search-form">
        <slot name="search-fields" />
        <el-form-item class="search-actions">
          <div class="search-btns-inner">
            <el-button type="primary" @click="onSearch">查询</el-button>
            <el-button @click="onReset">重置</el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-actions-row">
      <slot name="header-actions" />
    </div>

    <div class="table-wrapper">


      <el-table
        :data="data"
        v-loading="loading"
        style="width: 100%"
        height="100%"
        class="custom-table"
        header-row-class-name="table-header-row"
      >
        <el-table-column
          v-for="col in columns"
          :key="col.prop as string"
          :prop="col.prop"
          :label="col.label"
          :width="col.width"
          :min-width="col.minWidth || 120"
          show-overflow-tooltip
        />
        <el-table-column v-if="$slots['row-actions']" label="操作" :width="actionWidth" fixed="right">
          <template #default="scope">
             <div class="row-actions-container">
                <slot name="row-actions" :row="scope.row" />
             </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="custom-pagination">
        <div class="pagination-left">
          <span>共 {{ total }} 条记录，当前第 {{ query.current }} / {{ totalPages }} 页</span>
          <span class="size-info">
            每页
            <el-select v-model="query.size" @change="onSearch" class="size-select">
              <el-option v-for="s in [10, 20, 50, 100]" :key="s" :label="s" :value="s" />
            </el-select>
            条
          </span>
        </div>
        <div class="pagination-right">
          <span>跳至</span>
          <el-input v-model="jumpPageInput" class="jump-input" @keyup.enter="onJump" />
          <span>页</span>
          <el-button class="jump-btn" @click="onJump">跳转</el-button>
          <div class="nav-btns">
            <el-button :disabled="query.current <= 1" :icon="ArrowLeft" @click="onPrev" />
            <el-button :disabled="query.current >= totalPages" :icon="ArrowRight" @click="onNext" />
          </div>
        </div>
      </div>
    </div>
    <slot name="modals" />
  </div>
</template>

<script setup lang="ts">
import { watch, onMounted, ref, computed } from 'vue';
import { Search, Refresh, ArrowLeft, ArrowRight } from '@element-plus/icons-vue';

interface Column {
  prop: string;
  label: string;
  width?: string | number;
  minWidth?: string | number;
}

interface Query {
  current?: number;
  size?: number;
  [key: string]: any;
}

const props = defineProps<{
  columns: Column[];
  query: Query;
  data: any[];
  total: number;
  fetch: () => void;
  loading?: boolean;
  actionWidth?: string | number;
}>();

const emit = defineEmits<{
  (e: 'reset'): void;
}>();

const actionWidth = props.actionWidth || 200;

const totalPages = computed(() => Math.ceil(props.total / props.query.size) || 1);
const jumpPageInput = ref('1');

const onSearch = () => {
  props.fetch();
};

const onJump = () => {
  const page = parseInt(jumpPageInput.value);
  if (!isNaN(page) && page >= 1 && page <= totalPages.value) {
    props.query.current = page;
    onSearch();
  }
};

const onPrev = () => {
  if (props.query.current > 1) {
    props.query.current--;
    onSearch();
  }
};

const onNext = () => {
  if (props.query.current < totalPages.value) {
    props.query.current++;
    onSearch();
  }
};

const onReset = () => emit('reset');

onMounted(onSearch);
watch(
  () => [props.query.current, props.query.size],
  () => {
    props.fetch();
  }
);
</script>

<style scoped>
.crud-table {
  background-color: #fff;
  min-height: 100vh;
}

.page-meta {
  padding: 20px 24px;
  display: flex;
  align-items: center;
}

.meta-left {
  display: flex;
  align-items: baseline;
  gap: 16px;
}

.main-title {
  font-size: 20px;
  font-weight: 600;
  color: #1a1a1a;
  margin: 0;
}

.sub-title {
  font-size: 14px;
  color: #999;
}

.search-section {
  padding: 0 24px 20px;
  border-bottom: 1px solid #f0f0f0;
}

.search-btns-inner {
  display: flex;
  gap: 8px;
}

.inline-search-form {
  display: flex;
  align-items: center;
  flex-wrap: nowrap;
}

.inline-search-form ::v-deep(.el-form-item) {
  margin-right: 32px;
  margin-bottom: 0px;
}

.inline-search-form ::v-deep(.el-form-item__label) {
  color: #666;
  font-weight: 400;
  padding-right: 12px;
  white-space: nowrap;
}

.inline-search-form ::v-deep(.el-input__wrapper),
.inline-search-form ::v-deep(.el-select__wrapper),
.inline-search-form ::v-deep(.el-range-editor.el-input__wrapper) {
  background-color: #f2f3f5 !important;
  box-shadow: none !important;
  border: none !important;
  border-radius: 4px;
}

.search-actions {
  margin-left: auto !important;
  margin-right: 0 !important;
}

.search-actions .el-button {
  padding: 0 20px;
  height: 32px;
  border-radius: 4px;
  font-weight: 500;
  border: none;
}

.search-actions .el-button--primary {
  background-color: #1677ff !important;
}

.search-actions .el-button:not(.el-button--primary) {
  background-color: #f0f0f0 !important;
  color: #666 !important;
}

.search-actions .el-button:hover {
  opacity: 0.85;
}

.table-actions-row {
  padding: 16px 24px;
  display: flex;
  justify-content: flex-end;
}

.table-actions-row ::v-deep(.el-button--primary) {
  background-color: #1677ff;
  border-color: #1677ff;
  padding: 0 16px;
  height: 32px;
  border-radius: 4px;
  font-size: 14px;
}

.table-actions ::v-deep(.el-icon) {
  margin-right: 4px;
}

.table-wrapper {
  padding: 0 24px 24px;
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 400px;
}

.custom-table {
  flex: 1;
}

.custom-table ::v-deep(.table-header-row th) {
  background-color: #f7f8fa !important;
  color: #333;
  font-weight: 500;
  height: 44px;
}

.custom-table ::v-deep(.el-table__row) {
  height: 54px;
}

.row-actions-container ::v-deep(.el-button--text),
.row-actions-container ::v-deep(.el-button.is-text) {
  padding: 0;
  color: #0066ff;
  font-weight: 400;
}

.row-actions-container ::v-deep(.el-button--danger.is-text) {
  color: #ff4d4f;
}

.custom-pagination {
  margin-top: 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 24px;
  background: #fdfdfd;
  border: 1px solid #f0f0f0;
  border-radius: 8px;
  font-size: 13px;
  color: #666;
}

.pagination-left {
  display: flex;
  align-items: center;
  gap: 24px;
}

.size-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.size-select {
  width: 80px;
}

.size-select ::v-deep(.el-select__wrapper) {
  height: 28px;
  background: transparent !important;
  border: 1px solid #e0e0e0 !important;
}

.pagination-right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.jump-input {
  width: 44px;
}

.jump-input ::v-deep(.el-input__wrapper) {
  height: 28px;
  padding: 0 8px;
  text-align: center;
  background: transparent !important;
  border: 1px solid #e0e0e0 !important;
}

.jump-input ::v-deep(input) {
  text-align: center;
}

.jump-btn {
  height: 28px;
  padding: 0 12px;
  background: #f5f5f5 !important;
  border: 1px solid #e0e0e0 !important;
  color: #333 !important;
  font-size: 12px;
}

.nav-btns {
  display: flex;
  gap: 8px;
  margin-left: 12px;
}

.nav-btns .el-button {
  width: 32px;
  height: 32px;
  padding: 0;
  background: #f5f5f5 !important;
  border: 1px solid #e0e0e0 !important;
  color: #666 !important;
}

.nav-btns .el-button.is-disabled {
  opacity: 0.5;
}


</style>


