# Git 仓库同时推送到 Gitee 和 GitHub 配置说明

## 1. 目标

- 保留原有 Gitee 远程仓库
- 新增 GitHub 仓库同步
- 本地执行一次 `git push` 时，同时推送到 Gitee 和 GitHub
- 忽略 IDE 和构建产物目录，避免无关文件进入版本库

## 2. 本次仓库信息

- 本地仓库路径：`G:\Documents\JavaProject\java-study`
- Gitee 仓库：`https://gitee.com/guangxikejidaxue/java-study.git`
- GitHub 仓库：`git@github.com:747897928/java-study.git`
- 当前主分支：`master`

## 3. 先处理忽略规则

为了避免 IDE 配置和 Maven/Java 构建产物进入仓库，在根目录 `.gitignore` 中增加了以下规则：

```gitignore
.idea/
target/
```

说明：

- `.idea/`：IntelliJ IDEA 工程配置目录
- `target/`：Maven 默认构建输出目录

## 4. GitHub 建仓注意事项

在 GitHub 新建仓库时，建议创建空仓库，不要勾选初始化内容。

建议设置：

- Repository name：`java-study`
- Add README：关闭
- Add .gitignore：不添加
- Add license：不添加

原因：

- 本地仓库已经存在完整历史
- 远程空仓库最适合直接推送本地已有项目
- 避免因为远程默认文件导致额外合并

## 5. 新增 GitHub 远程

最开始新增 GitHub 远程时使用的是 HTTPS：

```powershell
git remote add github https://github.com/747897928/java-study.git
```

然后将 `origin` 配成双推送：

```powershell
git remote set-url --add --push origin https://gitee.com/guangxikejidaxue/java-study.git
git remote set-url --add --push origin https://github.com/747897928/java-study.git
```

这样配置后：

- `origin` 的 fetch 仍然来自 Gitee
- `origin` 的 push 会同时推到 Gitee 和 GitHub

## 6. HTTPS 推送失败原因

执行 `git push` 时出现了 GitHub `403`：

```text
remote: Permission to 747897928/java-study.git denied to 747897928.
fatal: unable to access 'https://github.com/747897928/java-study.git/': The requested URL returned error: 403
```

排查结果：

- Gitee 推送正常
- GitHub 远程地址正确
- 问题出在 GitHub 的 HTTPS 凭据认证
- 本机使用的是 Git Credential Manager
- Windows 中缓存的 GitHub 凭据无写权限或已失效

## 7. 改用 SSH 解决 GitHub 推送问题

检查本机 SSH 配置后，发现已有可用密钥：

- `C:\Users\{用户名}\.ssh\id_rsa`
- `C:\Users\{用户名}\.ssh\id_rsa.pub`

并确认 SSH 可成功登录 GitHub：

```powershell
ssh -T git@github.com
```

返回类似：

```text
Hi 747897928! You've successfully authenticated, but GitHub does not provide shell access.
```

说明 GitHub SSH 认证已可用，因此将 GitHub 远程改为 SSH。

### 7.1 修改 `github` 远程

```powershell
git remote set-url github git@github.com:747897928/java-study.git
```

### 7.2 修改 `origin` 的双推送地址

先删除原来的 GitHub HTTPS push 地址，再加入 SSH push 地址：

```powershell
git remote set-url --delete --push origin https://github.com/747897928/java-study.git
git remote set-url --add --push origin git@github.com:747897928/java-study.git
```

## 8. 最终远程配置

最终配置如下：

```text
github  git@github.com:747897928/java-study.git (fetch)
github  git@github.com:747897928/java-study.git (push)
origin  https://gitee.com/guangxikejidaxue/java-study.git (fetch)
origin  https://gitee.com/guangxikejidaxue/java-study.git (push)
origin  git@github.com:747897928/java-study.git (push)
```

可用以下命令检查：

```powershell
git remote -v
git remote get-url --push --all origin
```

## 9. 实际推送命令

配置完成后，执行：

```powershell
git push origin master
```

该命令的效果：

- 推送到 Gitee：`https://gitee.com/guangxikejidaxue/java-study.git`
- 推送到 GitHub：`git@github.com:747897928/java-study.git`

后续如果仍使用 `master` 分支，继续执行上面这条命令即可实现双推送。

## 10. 日常使用建议

### 10.1 提交并双推送

```powershell
git add -A
git commit -m "你的提交说明"
git push origin master
```

### 10.2 查看双推送是否仍然生效

```powershell
git remote get-url --push --all origin
```

### 10.3 测试 GitHub SSH 是否正常

```powershell
ssh -T git@github.com
```

## 11. 如果以后再次遇到 GitHub 推送失败

优先按以下顺序排查：

1. 检查远程地址是否仍是 SSH：`git remote -v`
2. 检查 SSH 登录是否正常：`ssh -T git@github.com`
3. 检查当前分支名称是否正确：`git branch --show-current`
4. 检查本地是否有未提交内容：`git status`
5. 再执行推送：`git push origin master`

## 12. 总结

本仓库最终采用的是以下方案：

- Gitee 保持为主 fetch 远程
- GitHub 使用 SSH 作为推送远程
- `origin` 配置双 push URL
- 通过一次 `git push origin master` 同步到两个平台
- `.idea/` 和 `target/` 已加入忽略规则

这是当前仓库最稳妥、最省事的双推送方式。
