# commons-parent
## Version Graph
```
2.2.2       2023-12-11      queryTotal调整为getCount，getPage的返回对象调整为Page，增加getById方法返回DTO对象，入参为Object
2.2.1       2023-11-26      pom文件的配置调整，增加distributionManagement，删除repository
2.2.0       2023-11-18  pom文件的配置调整，优化引用关系，规范groupId名称
                        调整db包内文件路径，优化部分工具方法的代码
2.1.1       2023-10-07  pom文件的parent内增加relativePath配置
2.1.0       2023-09-28  删除版权注释信息，增加ResponseResult、RequestAdvice文件

2.0.0       2023-06-30  调整包路径，对包打包层级进行重新规划调整

1.3.3       2022-02-22  Long主键生成位数调整，16位
1.3.2       2022-02-08  随机生成Long主键有重复，改用雪花算法
1.3.1       2021-12-12  增加Base64Utils，增加Base64Decoder和Base64Encoder
                                    ClassUtils内增加isNormalClass方法
                                    增加FileReader，IoUtils内新增readBytes，wirteBytes方法，增加toMarkSupportStream方法
                                    StringUtils内增加lowerFirst和upperFirst方法

1.3.0       2021-12-09  调整增加部分方法，为删除部分工具类，增加方法


1.2.2       2021-08-19  调整StringUtils内的方法，增加对空的处理
1.2.1       2020-06-15  增加高并发的Long类型数字处理，修改randomLid方法，升级版本，
                                    优化方法，采用1000位随机，增加随机性

1.2.0       2019-10-15  对JsonDeUtils内的方法进行更新

1.1.1       2019-06-10
                        增加ThreadPool类，修改DataUtils工具类方法
                        增加Page和QueryPage，为分页功能进行简化操作
                        将org.json和Gson的方法，合并统一使用fastjson的方法

1.1.0       2019-01-15
                        对StringUtils的方法进行修改，增加isEmpty等常用方法
                        增加DbException，捕获数据库操作的异常
                        修改decode方法
1.0.0       2018-03-01
                        初始化版本，增加annotation内的相关信息
                        初始化版本，增加初始相关信息

mvn clean deploy
mvn clean install
mvn clean install -Dcheckstyle.skip  

```
