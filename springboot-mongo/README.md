# Spring Boot MongoDB Sample

### Configuration
* 实现config.mongo中的各个db的config, 如AccountConfig(重点是多个urls的配置注册)
* 实现entities里的各个具体的collecion类，如User，Seller等
* 实现对应的repositories，如UserRepository等, 实现findUserById等接口
* 实现Operator类，具体就是调用对应的repository的接口

### CRUD  
#### Create
* 可以通过repositories调用save来创建
* 通过MongoTemplate来调用insert来创建  

#### Read
* 可以通过定义的repositories里的接口来实现read操作,如find/findOne/count/findAll/exist等
* 或者直接通过MongoTemplate来调用find(new Query, entityClass)的方式查询
* 查找embedded document的方式可以通过如下两种方式：  
    ```repositories的findParent_Embedded()的方式查询```
    ```MongoTemplate来调用findOne(new Query(where("parent.embedded").is())的方式查询```


#### Update  
* 通过MongoTemplate来调用updateMulti/updateFirst来更新

#### Delete
* 通过MongoTemplate来调用remove来删除
* 通过repositories来调用delete(entity)/deleteById(id)/deleteAll来删除

### Annotations usage
* @Id 主键，不可重复，自带索引
* @Document 标注在实体类上，把一个java类声明为mongodb的文档，可以通过collection参数指定这个类对应的文档
* @Indexed 声明该字段需要加索引，加索引后以该字段为条件检索将大大提高速度; 唯一索引是@Indexed(unique = true)  
* @CompoundIndex 复合索引，加复合索引后通过复合索引字段查询将大大提高速度
* @Field 代表一个字段，可以不加，不加的话默认以参数名为字段名
* @DBRef 关联另一个document对象. 类似于mysql的表关联，但并不一样，mongo不会做级联的操作
 
