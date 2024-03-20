# java-core-utils
## 介绍
JAVA代码核心工具类，包括核心、缓存、系统、脚本处理，数据处理等通用工具方法

## 版本历史
```
命令行：
mvn clean install

5     2024-03-20     更新注释和说明部分，为具体应用进行规范化调整
4     2023-11-26     更新包路径，调整distributionManagement配置方式
                     配置github工程中packages配置，实现发布及使用联通
3     2023-11-22     调整配置，修改md文件，优化链接，验证两个子包代码
2     2023-09-29     pom文件的parent内增加relativePath配置
1     2019-06-18     创建版本，进行结构划分，打包验证及本地发布

```

## 子包简介
1. commons-parent:公共的工具核心、JSON工具、缓存工具类等子包，版本：2.3
   [版本更新链接](./commons-parent/README.md)
2. datas-parent:公共的数据处理核心，多数据源链接等工具类，版本：2.1
   [版本更新链接](./datas-parent/README.md)

## 技术路径
### 软件架构
```
Springboot
```
### 安装教程
1. mvn clean install
2. mvn clean deploy

### 使用说明
1. 通过依赖使用
```
   <dependency>
      <groupId>${groupId}</groupId>
      <artifactId>${artifactId}</artifactId>
      <version>${project.version}</version>
      <scope>test</scope>
   </dependency>
```
2. 直接拷贝打包后的jar包

## 参与贡献
1.  Fork 本仓库
2.  新建 Feat_1.0.0 分支
3.  提交代码
```
git config user.name linlaninfo
git config user.email linlanio@qq.com
git config --global --list
git config --list
```
4. 新建 Pull Request
5. 创建和提交tag
```
创建
git tag -a v4 -m "create new tag v4"
查看
git tag
提交
git push origin --tags
删除
git tag -d v4

```
6. 其他
