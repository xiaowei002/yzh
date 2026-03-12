# 修复依赖问题

如果遇到 `@element-plus/icons-vue` 找不到的问题，请按以下步骤操作：

## 方法 1：清除缓存并重新安装

```bash
cd frontend
rm -rf node_modules
rm -rf package-lock.json
npm cache clean --force
npm install
```

## 方法 2：单独安装图标库

```bash
cd frontend
npm install @element-plus/icons-vue@^2.3.1
```

## 方法 3：如果 npm 有问题，使用 yarn

```bash
cd frontend
yarn install
```

## 方法 4：使用 cnpm（如果在中国）

```bash
cd frontend
cnpm install
```

安装完成后，重新运行：
```bash
npm run dev
```

