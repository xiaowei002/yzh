# 安装 MD5 加密库

已替换为 `js-md5` 库，请执行以下命令安装：

```powershell
cd frontend
npm install js-md5
```

或者如果 npm 安装有问题，可以从 store 复制（如果存在）：

```powershell
cd frontend

# 检查 store 中是否有 js-md5
Get-ChildItem node_modules\.store -Filter '*js-md5*' -Directory

# 如果找到，可以手动安装或使用其他包管理器
```

安装完成后，重新构建：

```powershell
npm run build
```

