# java-core-utils
## 介绍
JAVA代码核心工具类，包括核心、缓存、系统、脚本处理，数据处理等通用工具方法

## 版本历史
```

2.4.0     2024-03-21    取消版本的变量形式，直接调整为具体数字
                        调整为一级子包模式，删除commons-parent和datas-parent目录

2.3.2     2024-03-21    将全部的版本统一，由于编译错误，升级版本
2.3.1     2024-03-21    将全部的版本统一

2.3.1     2024-03-21    将全部的版本统一

2.3.0       2024-03-20  更新注释和说明部分，为具体应用进行规范化调整

2.2.2       2023-12-11  queryTotal调整为getCount，getPage的返回对象调整为Page，增加getById方法返回DTO对象，入参为Object
2.2.1       2023-11-26  pom文件的配置调整，增加distributionManagement，删除repository
2.2.0       2023-11-18  pom文件的配置调整，优化引用关系，规范groupId名称
                        调整db包内文件路径，优化部分工具方法的代码
2.1.1       2023-10-07  pom文件的parent内增加relativePath配置
2.1.0       2023-09-28  删除版权注释信息，增加ResponseResult、RequestAdvice文件
                        更新注释和说明部分，为具体应用进行规范化调整

2.0.0       2023-06-30  调整包路径，对包打包层级进行重新规划调整
                        pom文件的配置调整，增加distributionManagement，删除repository

1.3.3       2022-02-22  Long主键生成位数调整，16位
                        pom文件的配置调整，优化引用关系，规范groupId名称
1.3.2       2022-02-08  随机生成Long主键有重复，改用雪花算法
                        pom文件的parent内增加relativePath配置
1.3.1       2021-12-12  增加Base64Utils，增加Base64Decoder和Base64Encoder
                        ClassUtils内增加isNormalClass方法
                        增加FileReader，IoUtils内新增readBytes，wirteBytes方法，增加toMarkSupportStream方法
                        StringUtils内增加lowerFirst和upperFirst方法
                        规范化版权注释信息，调整ehcache缓存，不再使用
1.3.0       2021-12-09  调整增加部分方法，为删除部分工具类，增加方法
                        规范化版权注释信息
1.2.2       2021-08-19  调整StringUtils内的方法，增加对空的处理
1.2.1       2020-06-15  增加高并发的Long类型数字处理，修改randomLid方法，升级版本，
                        优化方法，采用1000位随机，增加随机性

1.2.0       2019-10-15  对JsonDeUtils内的方法进行更新
                        进行工程路径迁移，调整父引用的版本

1.1.1       2019-06-10
                        增加ThreadPool类，修改DataUtils工具类方法
                        增加Page和QueryPage，为分页功能进行简化操作
                        将org.json和Gson的方法，合并统一使用fastjson的方法
                        统一对StringUtils的引用
1.1.0       2019-01-15
                        对StringUtils的方法进行修改，增加isEmpty等常用方法
                        增加DbException，捕获数据库操作的异常
                        修改decode方法
                        将缺省的view名称修改为linlan_view
1.0.0       2018-03-01
                        初始化版本，增加annotation内的相关信息
                        初始化版本，增加初始相关信息
                        调整ProviderName为DataProviderName，修改DatasourceParameter为DataSourceParameter，QueryParameter修改为DataQueryParameter，修改包名称路径为datas，修改f_tofloat方法为f_todouble
                        对getColumn方法进行修改，增加reload；对kylin的内容进行较大调整
                        初始化版本
```

## 子包简介
1. commons-core: 公共的工具核心子包
2. commons-db: 公共的数据库资源类子包
3. commons-script: 公共的JSON工具类子包
4. commons-cache: 公共的缓存工具类子包
5. commons-evn: 公共的环境工具类子包
6. datas-parent:公共的数据处理核心，多数据源链接等工具类子包

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
git tag -a 2.4.0 -m "create union version and bug"
查看
git tag
提交
git push origin --tags
删除
git tag -d 2.4.0

本地编译：mvn clean install
发布到github：mvn clean deploy

```
6. 其他
