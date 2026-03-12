<template>
  <div class="crud-table">
    <el-card shadow="hover" class="table-card">
      <template #header>
        <div class="card-header">
          <span class="card-title">
            <slot name="header-title">数据列表</slot>
          </span>
          <div class="header-actions">
            <slot name="header-actions" />
          </div>
        </div>
      </template>

      <slot name="search">
        <el-collapse v-model="searchCollapse" class="search-collapse">
          <el-collapse-item name="search" :title="null">
            <template #title>
              <div class="search-title">
                <el-icon><Search /></el-icon>
                <span>搜索条件</span>
                <el-icon class="collapse-icon" :class="{ 'is-active': searchCollapse.includes('search') }">
                  <ArrowDown />
                </el-icon>
              </div>
            </template>
            <el-form :inline="true" :model="query" class="search-form" label-width="100px">
              <slot name="search-fields" />
              <el-form-item>
                <el-button type="primary" :icon="Search" @click="onSearch">查询</el-button>
                <el-button :icon="Refresh" @click="onReset">重置</el-button>
              </el-form-item>
            </el-form>
          </el-collapse-item>
        </el-collapse>
      </slot>

      <div class="actions">
        <slot name="actions" />
      </div>

      <el-table
        :data="data"
        v-loading="loading"
        stripe
        style="width: 100%"
        class="data-table"
        :header-cell-style="{ background: '#f5f7fa', color: '#606266', fontWeight: '600' }"
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
            <slot name="row-actions" :row="scope.row" />
          </template>
        </el-table-column>
      </el-table>
      <div class="pager">
        <el-pagination
          background
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          v-model:current-page="query.current"
          v-model:page-size="query.size"
          :page-sizes="[10, 20, 50, 100]"
          @current-change="onSearch"
          @size-change="onSearch"
        />
      </div>
    </el-card>
    <slot name="modals" />
  </div>
</template>

<script setup lang="ts">
import { watch, onMounted, ref } from 'vue';
import { Search, Refresh, ArrowDown } from '@element-plus/icons-vue';

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

const searchCollapse = ref<string[]>([]);
const actionWidth = props.actionWidth || 280;

const onSearch = () => {
  props.fetch();
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
  padding: 20px;
}

.table-card {
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  border: none;
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(4px);
}

.table-card ::v-deep(.el-card__header) {
  padding: 20px 24px;
  border-bottom: 1px solid #f0f2f5;
}

.table-card ::v-deep(.el-card__body) {
  padding: 24px;
}

.card-header .card-title {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  position: relative;
  padding-left: 12px;
}

.card-header .card-title::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 18px;
  background: #409eff;
  border-radius: 2px;
}

.search-collapse {
  margin-bottom: 24px;
  border: none;
  background: #f8fafc;
  border-radius: 12px;
  overflow: hidden;
}

.search-collapse ::v-deep(.el-collapse-item__header) {
  padding: 0 20px;
  height: 52px;
  background: #f8fafc;
  border-bottom: none;
  font-size: 15px;
}

.search-collapse ::v-deep(.el-collapse-item__wrap) {
  background: #f8fafc;
  border-bottom: none;
}

.search-collapse ::v-deep(.el-input__wrapper) {
  border-radius: 8px;
  box-shadow: 0 0 0 1px #dcdfe6 inset;
}

.search-collapse ::v-deep(.el-button) {
  border-radius: 8px;
}

.data-table {
  border-radius: 12px;
  overflow: hidden;
}

.data-table ::v-deep(.el-table__inner-wrapper::before) {
  display: none;
}

.pager {
  margin-top: 24px;
  padding-top: 20px;
}
</style>


