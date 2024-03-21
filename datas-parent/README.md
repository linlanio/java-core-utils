# datas-parent
## Version Graph
```
2.3.1     2024-03-21     将全部的版本统一

2.1.0     2024-03-20        更新注释和说明部分，为具体应用进行规范化调整

2.0.0       2023-11-26      pom文件的配置调整，增加distributionManagement，删除repository

1.4.1       2023-11-18      pom文件的配置调整，优化引用关系，规范groupId名称
1.4.0       2023-10-07      pom文件的parent内增加relativePath配置

1.3.1       2023-09-28      规范化版权注释信息，调整ehcache缓存，不再使用
1.3.0       2023-07-28      规范化版权注释信息

1.2.0       2021-12-09      进行工程路径迁移，调整父引用的版本

1.1.1       2020-10-30      统一对StringUtils的引用

1.1.0       2019-09-30      将缺省的view名称修改为howide_view

1.0.0       2019-06-30    
            调整ProviderName为DataProviderName，修改DatasourceParameter为DataSourceParameter，QueryParameter修改为DataQueryParameter，修改包名称路径为datas，修改f_tofloat方法为f_todouble
            对getColumn方法进行修改，增加reload；对kylin的内容进行较大调整
            初始化版本

mvn clean install -Dcheckstyle.skip  
mvn deploy -Dcheckstyle.skip  

```