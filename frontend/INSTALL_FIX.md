# 修复图标库安装问题

## 问题
Rollup 无法解析 `@element-plus/icons-vue`，npm 安装也遇到错误。

## 解决方案

### 方法 1：使用 PowerShell 脚本（推荐）

```powershell
cd frontend
.\reinstall-icons.ps1
```

### 方法 2：手动重新安装

```powershell
cd frontend

# 删除损坏的安装
Remove-Item -Recurse -Force node_modules\@element-plus\icons-vue -ErrorAction SilentlyContinue

# 清除缓存
npm cache clean --force

# 重新安装（使用固定版本）
npm install @element-plus/icons-vue@2.3.1 --save
```

### 方法 3：完全重新安装所有依赖

```powershell
cd frontend

# 删除所有依赖
Remove-Item -Recurse -Force node_modules
Remove-Item -Force package-lock.json -ErrorAction SilentlyContinue

# 清除缓存
npm cache clean --force

# 重新安装
npm install
```

### 方法 4：使用 yarn（如果 npm 持续有问题）

```powershell
cd frontend

# 安装 yarn（如果没有）
npm install -g yarn

# 使用 yarn 安装
yarn install
```

### 方法 5：使用 cnpm（中国用户）

```powershell
# 安装 cnpm
npm install -g cnpm --registry=https://registry.npmmirror.com

cd frontend
cnpm install
```

## 验证安装

安装完成后，验证图标库是否正确安装：

```powershell
# 检查文件是否存在
Test-Path node_modules\@element-plus\icons-vue\package.json

# 应该返回 True

# 或者检查版本
npm list @element-plus/icons-vue
```

## 如果还是不行

如果以上方法都不行，可以尝试：

1. 更新 npm 到最新版本：`npm install -g npm@latest`
2. 检查网络连接和代理设置
3. 使用不同的包管理器（yarn 或 pnpm）

