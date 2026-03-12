# 重新安装图标库的脚本
Write-Host "正在修复 @element-plus/icons-vue 安装问题..." -ForegroundColor Yellow

# 删除损坏的安装
$iconPath = "node_modules\@element-plus\icons-vue"
if (Test-Path $iconPath) {
    Write-Host "删除损坏的图标库..." -ForegroundColor Yellow
    Remove-Item -Recurse -Force $iconPath -ErrorAction SilentlyContinue
}

# 清除 npm 缓存
Write-Host "清除 npm 缓存..." -ForegroundColor Yellow
npm cache clean --force

# 重新安装
Write-Host "重新安装图标库..." -ForegroundColor Yellow
npm install @element-plus/icons-vue@2.3.1 --save

Write-Host "完成！请运行 npm run dev 测试" -ForegroundColor Green

