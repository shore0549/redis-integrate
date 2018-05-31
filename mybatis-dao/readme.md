# dao

主要介绍如何使用mybatis，来实现db的增删改查，通常mybatis一般是结合spring来使用，因此我们也不脱离这个大环境

主要内容将包括以下：

- 环境配置相关
- Dao文件与xml的映射（接口绑定有两种，xml和注解方式，这里以xml方式进行说明）
- 增删改查的写法
- 常用命令  choose, if, set, ....
- #，$ 两种方式的区别


## I. 前提准备

在开始之前，先得准备好对应的环境，首先建立一个可有效运行的环境

依赖配置

```xml
<!-- mybatis 依赖-->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.4.5</version>
</dependency>
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>1.3.2</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.35</version>
</dependency>


<!-- druid数据源 -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.6</version>
</dependency>


<!-- spring 依赖相关-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.0.2.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.0.2.RELEASE</version>
</dependency>


<!-- 单测相关 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>5.0.2.RELEASE</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
```


这里采用druid来进行数据源的管理，目前仅作为一个使用工具，不深入探究

接下来就是xml的配置，如我们常见的jdbc链接相关的配置信息，一个demo如下


```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">


    <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="locations">
            <value>classpath*:jdbc.properties</value>
        </property>
    </bean>


    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
        <property name="driverClassName" value="${driver}"/>
        <property name="url" value="${url}"/>
        <property name="username" value="${username}"/>
        <property name="password" value="${password}"/>

        <property name="filters" value="stat"/>

        <property name="maxActive" value="20"/>
        <property name="initialSize" value="1"/>
        <property name="maxWait" value="60000"/>
        <property name="minIdle" value="1"/>

        <property name="timeBetweenEvictionRunsMillis" value="60000"/>
        <property name="minEvictableIdleTimeMillis" value="300000"/>

        <property name="validationQuery" value="SELECT 'x'"/>
        <property name="testWhileIdle" value="true"/>
        <property name="testOnBorrow" value="false"/>
        <property name="testOnReturn" value="false"/>

        <property name="poolPreparedStatements" value="true"/>
        <property name="maxPoolPreparedStatementPerConnectionSize" value="50"/>
    </bean>


    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!-- 指定mapper文件 -->
        <property name="mapperLocations" value="classpath*:mapper/*.xml"/>
    </bean>


    <!-- 指定扫描dao -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.git.hui.demo.mybatis"/>
    </bean>

</beans>
```

注意上面的三个bean，一个dataSource, 主要配置一些db链接相关的参数，一个sqlSessionFactory, 属于bean工厂，用于创建一些Sql会话，里面一个非常重要的参数就是指定 mapperLocations


最终一个就是指定扫描dao的路径，这个不能忘，否则会发现无法注入dao


## II. 一个实例

以一个实际的例子，演示dao与mapper文件的映射关系，以及调用姿势，首先定义DB实体类对象

```java
@Data
public class PoetryEntity implements Serializable {
    private static final long serialVersionUID = 4888857290009801223L;

    private Long id;

    /**
     * 作者名
     */
    private String author;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 解释
     */
    private String explain;

    /**
     * 诗词的类型 0 成语，1 唐前诗词
     */
    private Integer type;


    /**
     * 标记，对应诗词的朝代
     */
    private Integer tag;


    /**
     * 诗词的题材，七言，五言等
     */
    private String theme;


    private Integer isDeleted;

    private Integer created;

    private Integer updated;

}
```

dao文件

```java
public interface PoetryDao {

    PoetryEntity queryPoetryById(@Param("id") Long id);
}
```


对应的xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.git.hui.demo.mybatis.mapper.PoetryDao">

    <sql id="poetryEntity">
        id, title, author, content, `explain`, `type`, `tag`, `theme`, `is_deleted` as isDeleted,
        UNIX_TIMESTAMP(`created_at`) as created,
        UNIX_TIMESTAMP(`updated_at`) as updated
    </sql>

    <select id="queryPoetryById" parameterType="long" resultType="com.git.hui.demo.mybatis.entity.PoetryEntity">
        select
        <include refid="poetryEntity"/>
        from
        poetry
        where
        id=#{id}
    </select>
</mapper>
```


测试case


```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/*.xml")
public class PoetryDaoTest {

    @Autowired
    private PoetryDao poetryDao;


    @Test
    public void testGet() {
        PoetryEntity entity = poetryDao.queryPoetryById(3L);
        System.out.println("query result: {}" + entity);
    }
}
```


看到上面自然就有一个疑问，定义的Dao接口，是如何和xml文件关联起来的呢？

- 注意xml中的namespace，为dao的全限定名
- 注意xml中的sql标签中的id，与dao层定义的接口名完全一致


大胆的猜测一下，整个过程应该如下：

- mybatis通过前面配置文件指定的mapperLocations，扫描指定路径下的所有xml文件（即写sql逻辑的xml）
- MapperScannerConfigurer 这个bean定义了哪些属于Dao层接口
- 对所有的dao接口，根据动态代理的方式，生成一个Proxy类，由这个proxy类，将dao接口的方法与xml中定义的sql标签关联起来
- dao接口的访问，实际由代理类执行，将xml定义的规则映射为对应的sql，然后交由底层封装好的jdbc来执行


实际上大致流程也是这样，从上面的描述，一个问题很容易抛出

- dao层接口，不支持重载（因为会导致dao层接口与xml中的关联不上的问题）


