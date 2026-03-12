# 手动修复图标库安装问题

## 问题
npm 安装遇到错误，图标库无法正确安装。

## 解决方案

### 方法 1：使用 pnpm（推荐，最稳定）

```powershell
# 安装 pnpm
npm install -g pnpm

cd frontend
pnpm install
```

### 方法 2：手动从 store 复制

如果图标库已经在 `.store` 中，可以手动创建符号链接：

```powershell
cd frontend

# 检查 store 中的版本
Get-ChildItem node_modules\.store -Filter '*icons-vue*' -Directory

# 如果找到，创建目录结构
New-Item -ItemType Directory -Force -Path "node_modules\@element-plus" | Out-Null

# 创建符号链接（需要管理员权限）
# 或者直接复制
$storePath = "node_modules\.store\@element-plus+icons-vue@2.3.2\node_modules\@element-plus\icons-vue"
$targetPath = "node_modules\@element-plus\icons-vue"

if (Test-Path $storePath) {
    Copy-Item -Path $storePath -Destination $targetPath -Recurse -Force
    Write-Host "已从 store 复制图标库" -ForegroundColor Green
}
```

### 方法 3：使用 cnpm（中国镜像）

```powershell
# 安装 cnpm
npm install -g cnpm --registry=https://registry.npmmirror.com

cd frontend
cnpm install
```

### 方法 4：完全重新安装（最后手段）

```powershell
cd frontend

# 删除所有
Remove-Item -Recurse -Force node_modules
Remove-Item -Force package-lock.json -ErrorAction SilentlyContinue

# 清除所有缓存
npm cache clean --force
Remove-Item -Recurse -Force "$env:APPDATA\npm-cache" -ErrorAction SilentlyContinue
Remove-Item -Recurse -Force "$env:LOCALAPPDATA\npm-cache" -ErrorAction SilentlyContinue

# 重新安装
npm install
```

### 方法 5：临时解决方案 - 移除图标使用

如果以上都不行，可以临时移除图标使用，让项目先运行起来：

1. 将所有 `import { Icon } from '@element-plus/icons-vue'` 改为使用 Element Plus 内置图标
2. 或者使用文本代替图标

## 验证安装

安装完成后验证：

```powershell
# 检查文件
Test-Path "node_modules\@element-plus\icons-vue\package.json"

# 检查版本
npm list @element-plus/icons-vue

# 测试构建
npm run build
```

