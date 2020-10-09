## 认识pom.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <!--父依赖，其中它主要是依赖一个父项目，主要是管理项目的资源过滤及插件！-->
    <!--点进去，发现还有一个父依赖，这里的父依赖才是真正管理SpringBoot应用里面所有依赖版本的地方，SpringBoot的版本控制中心；-->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.4.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <groupId>com.Ryan</groupId>
    <artifactId>springboot</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springboot</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <!--spring-boot-start-XXX 就是 spring-boot的场景启动器；
         SpringBoot将所有的功能场景都抽取出来，做成一个个的starter （启动器），
         只需要在项目中引入这些starter即可，所有相关的依赖都会导入进来 ， 
         我们要用什么功能就导入什么样的场景启动器即可 ；我们未来也可以自己自定义 starter-->
        <!--帮我们导入了web模块正常运行所依赖的组件-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!--帮我们导入了test模块正常运行所依赖的组件-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```


## 主启动程序

```java
package com.ryan.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication 
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
```
- @SpringBootApplication 声明一个类是主程序类

   作用： 声明在一个类上，注明这是一个springboot的主配置类，springboot应该运行这个类的main方法来作为整个程序的入口。
   
- 进入@SpringBootApplication注解

  ```java
  /*
   * Copyright 2012-2020 the original author or authors.
   *
   * Licensed under the Apache License, Version 2.0 (the "License");
   * you may not use this file except in compliance with the License.
   * You may obtain a copy of the License at
   *
   *      https://www.apache.org/licenses/LICENSE-2.0
   *
   * Unless required by applicable law or agreed to in writing, software
   * distributed under the License is distributed on an "AS IS" BASIS,
   * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   * See the License for the specific language governing permissions and
   * limitations under the License.
   */
  
  package org.springframework.boot.autoconfigure;
  
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @Inherited
  @SpringBootConfiguration
  @EnableAutoConfiguration
  @ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
  		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
  public @interface SpringBootApplication {
  
 
  	@AliasFor(annotation = EnableAutoConfiguration.class)
  	Class<?>[] exclude() default {};
  
 
  	@AliasFor(annotation = EnableAutoConfiguration.class)
  	String[] excludeName() default {};

  	@AliasFor(annotation = ComponentScan.class, attribute = "basePackages")
  	String[] scanBasePackages() default {};
  
  	@AliasFor(annotation = ComponentScan.class, attribute = "basePackageClasses")
  	Class<?>[] scanBasePackageClasses() default {};
	  
  	@AliasFor(annotation = ComponentScan.class, attribute = "nameGenerator")
  	Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;
  
  	@AliasFor(annotation = Configuration.class)
  	boolean proxyBeanMethods() default true;
  
  }
  ```
  
  **@ComponentScan** 
  
    说明：声明在一个类上，对应XML配置中的元素
  
    作用：自动扫描并加载符合条件的组件或者bean ， 将这个bean定义加载到IOC容器中
    
  **@EnableAutoConfiguration**
   
    作用：开启自动配置功能
    
    说明：以前我们需要自己配置的东西，而现在SpringBoot可以自动帮我们配置 ；@EnableAutoConfiguration告诉SpringBoot开启自动配置功能，这样自动配置才能生效；
    
    点击进入注解查看
     
    ```java
    package org.springframework.boot.autoconfigure;
    
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Inherited
    @AutoConfigurationPackage
    @Import(AutoConfigurationImportSelector.class)
    public @interface EnableAutoConfiguration {
    
    	String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration";
    	  
    	Class<?>[] exclude() default {};
 
    	String[] excludeName() default {};
    
    }

    ```
  @AutoConfigurationPackage 
  
    作用：自动配置包
    
    ```java
    package org.springframework.boot.autoconfigure;  
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Inherited
    @Import(AutoConfigurationPackages.Registrar.class)
    public @interface AutoConfigurationPackage {  
  
    	String[] basePackages() default {};
    	  
    	Class<?>[] basePackageClasses() default {};
    
    }
    ```
  **@Import**
    
    说明：Spring底层注解@import 
    
    作用：给容器中导入一个组件
    
  **Registrar.class**
   
    作用：将主启动类的所在包及包下面所有子包里面的所有组件扫描到Spring容器 
    
  **@SpringBootConfiguration** 
  
    说明：声明这是一个SpringBoot配置类
  
    进去@SpringBootConfiguration查看
    
    ```java
    package org.springframework.boot;
    
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Configuration
    public @interface SpringBootConfiguration {
          
    	@AliasFor(annotation = Configuration.class)
    	boolean proxyBeanMethods() default true;
    
    }
    ```
  
  @Configuration
  
    说明：声明是一个配置类 ，配置类就是对应Spring的xml配置文件；

## spring.factories文件

说明： 自动配置文件

```text
# AutoConfigureCache auto-configuration imports
org.springframework.boot.test.autoconfigure.core.AutoConfigureCache=\
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration

# AutoConfigureDataJdbc auto-configuration imports
org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc=\
org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration

# AutoConfigureDataJpa auto-configuration imports
org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa=\
org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration

# AutoConfigureDataLdap auto-configuration imports
org.springframework.boot.test.autoconfigure.data.ldap.AutoConfigureDataLdap=\
org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration,\
org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration

# AutoConfigureDataMongo auto-configuration imports
org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo=\
org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration

# AutoConfigureDataNeo4j auto-configuration imports
org.springframework.boot.test.autoconfigure.data.neo4j.AutoConfigureDataNeo4j=\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration

# AutoConfigureDataR2dbc auto-configuration imports
org.springframework.boot.test.autoconfigure.data.r2dbc.AutoConfigureDataR2dbc=\
org.springframework.boot.autoconfigure.data.r2dbc.R2dbcRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.r2dbc.R2dbcTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration,\
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration

# AutoConfigureDataRedis auto-configuration imports
org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis=\
org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration

# AutoConfigureJdbc auto-configuration imports
org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc=\
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration

# AutoConfigureTestDatabase auto-configuration imports
org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase=\
org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration

# AutoConfigureJooq auto-configuration imports
org.springframework.boot.test.autoconfigure.jooq.AutoConfigureJooq=\
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration,\
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration

# AutoConfigureJson auto-configuration imports
org.springframework.boot.test.autoconfigure.json.AutoConfigureJson=\
org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration

# AutoConfigureJsonTesters auto-configuration imports
org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters=\
org.springframework.boot.test.autoconfigure.json.JsonTestersAutoConfiguration,\
org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration

# AutoConfigureWebClient auto-configuration imports
org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient=\
org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration,\
org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration,\
org.springframework.boot.test.autoconfigure.web.reactive.WebTestClientAutoConfiguration

# AutoConfigureWebFlux auto-configuration imports
org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux=\
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration,\
org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration,\
org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration,\
org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration,\
org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration

# AutoConfigureMockMvc auto-configuration imports
org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc=\
org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration,\
org.springframework.boot.test.autoconfigure.web.servlet.MockMvcWebClientAutoConfiguration,\
org.springframework.boot.test.autoconfigure.web.servlet.MockMvcWebDriverAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration,\
org.springframework.boot.test.autoconfigure.web.servlet.MockMvcSecurityConfiguration

# AutoConfigureMockRestServiceServer
org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer=\
org.springframework.boot.test.autoconfigure.web.client.MockRestServiceServerAutoConfiguration

# AutoConfigureRestDocs auto-configuration imports
org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs=\
org.springframework.boot.test.autoconfigure.restdocs.RestDocsAutoConfiguration

# AutoConfigureTestEntityManager auto-configuration imports
org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager=\
org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManagerAutoConfiguration

# AutoConfigureWebClient auto-configuration imports
org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient=\
org.springframework.boot.test.autoconfigure.web.client.WebClientRestTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration,\
org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration,\
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration,\
org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration

# AutoConfigureWebMvc auto-configuration imports
org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc=\
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration,\
org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration,\
org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration,\
org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration,\
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration,\
org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration,\
org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration,\
org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration,\
org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration

# AutoConfigureWebServiceClient
org.springframework.boot.test.autoconfigure.webservices.client.AutoConfigureWebServiceClient=\
org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration

# AutoConfigureMockWebServiceServer
org.springframework.boot.test.autoconfigure.webservices.client.AutoConfigureMockWebServiceServer=\
org.springframework.boot.test.autoconfigure.webservices.client.MockWebServiceServerAutoConfiguration

# DefaultTestExecutionListenersPostProcessors
org.springframework.boot.test.context.DefaultTestExecutionListenersPostProcessor=\
org.springframework.boot.test.autoconfigure.SpringBootDependencyInjectionTestExecutionListener$PostProcessor

# Spring Test ContextCustomizerFactories
org.springframework.test.context.ContextCustomizerFactory=\
org.springframework.boot.test.autoconfigure.OverrideAutoConfigurationContextCustomizerFactory,\
org.springframework.boot.test.autoconfigure.filter.TypeExcludeFiltersContextCustomizerFactory,\
org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizerFactory,\
org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizerFactory

# Test Execution Listeners
org.springframework.test.context.TestExecutionListener=\
org.springframework.boot.test.autoconfigure.restdocs.RestDocsTestExecutionListener,\
org.springframework.boot.test.autoconfigure.web.client.MockRestServiceServerResetTestExecutionListener,\
org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrintOnlyOnFailureTestExecutionListener,\
org.springframework.boot.test.autoconfigure.web.servlet.WebDriverTestExecutionListener,\
org.springframework.boot.test.autoconfigure.webservices.client.MockWebServiceServerTestExecutionListener


```

* 小结

自动配置真正实现是从classpath中搜寻所有的META-INF/spring.factories配置文件 
并将其中对应的 org.springframework.boot.autoconfigure. 包下的配置项，通过反射
实例化为对应标注了 @Configuration的JavaConfig形式的IOC容器配置类 ， 然后将这些都汇总成为一个实例并加载到IOC容器中。

- 总结

    - SpringBoot在启动的时候从类路径下的META-INF/spring.factories中获取EnableAutoConfiguration指定的值

    - 将这些值作为自动配置类导入容器 ， 自动配置类就生效 ， 帮我们进行自动配置工作；

    - 整个J2EE的整体解决方案和自动配置都在springboot-autoconfigure的jar包中；

    - 它会给容器中导入非常多的自动配置类 （xxxAutoConfiguration）, 就是给容器中导入这个场景需要的所有组件 ， 并配置好这些组件 ；

    - 有了自动配置类 ， 免去了我们手动编写配置注入功能组件等的工作；
    
## run方法执行流程分析

## yaml文件配置注入

- 全局配置文件

    说明：SpringBoot使用一个全局的配置文件 ， 配置文件名称是固定的
    
    ```text
    application.properties
    
    语法结构 ：key=value
    
    application.yml
    
    语法结构 ：key：空格 value
    ```
    
    作用 ：修改SpringBoot自动配置的默认值，因为SpringBoot在底层都给我们自动配置好了
    
    例子：修改Tomcat的端口
    
    ```yaml
    server
        port: 9090
    ```
    或者是
    ```properties
    server.port=9090
    ```
- yaml语法格式

    **说明**：
    
        1、空格不能省略
        
        2、以缩进来控制层级关系，只要是左边对齐的一列数据都是同一个层级的。
        
        3、属性和值的大小写都是十分敏感的。
        
        字面量：普通的值  [ 数字，布尔值，字符串  ]
        
        字面量直接写在后面就可以 ， 字符串默认不用加上双引号或者单引号；
    
    **注意**：
    
        “ ” 双引号，不会转义字符串里面的特殊字符 ， 特殊字符会作为本身想表示的意思；
        
        比如 ：name: "kuang \n shen"   输出 ：kuang  换行   shen
        
        '' 单引号，会转义特殊字符 ， 特殊字符最终会变成和普通字符一样输出
        
        比如 ：name: ‘kuang \n shen’   输出 ：kuang  \n   shen
    
    **对象、Map（键值对）**
    
        对象、Map格式
        k: 
            v1:
            v2:
        在下一行来写对象的属性和值得关系，注意缩进；比如：
        
        student:
            name: fuyi
            age: 23
        行内写法
        
        student: {name: fuyi,age: 23}
        
        数组（ List、set ）
        
        用 - 值表示数组中的一个元素,比如：
        
        pets:
         - cat
         - dog
         - pig
        行内写法
        
        pets: [cat,dog,pig]
        
- 注入

    说明：给实体类直接注入匹配值
    
    - 编写实体类
    
    ```java
    package com.ryan.main.config;
    
    import com.ryan.main.bean.School;
    import org.springframework.boot.context.properties.ConfigurationProperties;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    
    import java.util.List;
    
    @Configuration
    @ConfigurationProperties(prefix = "person")
    public class Person {
    
        private String name;
    
        private int age;
    
        private String address;
    
        private List hobby;
    
        private School school;
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public int getAge() {
            return age;
        }
    
        public void setAge(int age) {
            this.age = age;
        }
    
        public String getAddress() {
            return address;
        }
    
        public void setAddress(String address) {
            this.address = address;
        }
    
        public List getHobby() {
            return hobby;
        }
    
        public void setHobby(List hobby) {
            this.hobby = hobby;
        }
    
        public School getSchool() {
            return school;
        }
    
        public void setSchool(School school) {
            this.school = school;
        }
    
        public Person() {
        }
    
        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", address='" + address + '\'' +
                    ", hobby=" + hobby +
                    ", school=" + school +
                    '}';
        }
    
        @Bean
        public void testPersionConfiguration() {
            System.out.println(this.toString());
        }
    }
    ```
  
   School实体类
   
   ```java
   package com.ryan.main.bean;
   
   import org.springframework.stereotype.Component;
   
   @Component
   public class School {
   
       private String name;
   
       private String address;
   
       public String getName() {
           return name;
       }
   
       public void setName(String name) {
           this.name = name;
       }
   
       public String getAddress() {
           return address;
       }
   
       public void setAddress(String address) {
           this.address = address;
       }
   
       @Override
       public String toString() {
           return "School{" +
                   "name='" + name + '\'' +
                   ", address='" + address + '\'' +
                   '}';
       }
   }
   ```
  
  - application.yml配置文件
  
  ```yaml
  person:
    name: fuyi
    age: 23
    address: guangzhou
    hobby: [music, basketball, running]
    school:
      name: zqu
      address: zhaoqing
  ```
  
  - 运行程序测试结果打印
  
  ```text
  2020-10-09 14:17:04.513  INFO 22976 --- [           main] com.ryan.main.MainApplication            : Starting MainApplication on DESKTOP-PEVRKGP with PID 22976 (C:\Users\13121\Desktop\springboot\main\target\classes started by 13121 in C:\Users\13121\Desktop\springboot)
  2020-10-09 14:17:04.515  INFO 22976 --- [           main] com.ryan.main.MainApplication            : No active profile set, falling back to default profiles: default
  Person{name='fuyi', age=23, address='guangzhou', hobby=[music, basketball, running], school=School{name='zqu', address='zhaoqing'}}
  2020-10-09 14:17:04.886  INFO 22976 --- [           main] com.ryan.main.MainApplication            : Started MainApplication in 0.654 seconds (JVM running for 1.211)
  ```
  
  
    
    
    
     
     

## 加载指定的配置文件

  @PropertySource
      
  作用：加载指定的配置文件
      
  @configurationProperties
      
  作用：默认从全局配置文件中获取值

- 编写person2实体类

    ```java
    package com.ryan.main.config;
    
    import com.ryan.main.bean.School;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.boot.context.properties.ConfigurationProperties;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.context.annotation.PropertySource;
    import org.springframework.stereotype.Component;
    
    import java.util.List;
    
    @Component
    @PropertySource(value = "classpath:person.properties")
    public class Person2 {
    
        @Value("${name}")
        private String name;
        @Value("${age}")
        private int age;
        @Value("${address}")
        private String address;
        @Value("${hobby}")
        private List hobby;
    
        private School school;
    
        public Person2() {
        }
    
        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", address='" + address + '\'' +
                    ", hobby=" + hobby +
                    ", school=" + school +
                    '}';
        }
    
        @Bean
        public void testPersionConfiguration2() {
            System.out.println(this.toString());
        }
    }

    ```
  
- 在resources文件目录下新建properties文件

  ```properties
  name=fuyi
  age=23
  address=guangzhou
  hobby=[music, basketball, running]
  ```
  
- 测试运行 打印结果

  ```text
  Person{name='fuyi', age=23, address='guangzhou', hobby=[[music, basketball, running]], school=null}
  ```
 
- 总结

    - 配置yml和配置properties都可以获取到值 ， 强烈推荐 yml；

    - 如果我们在某个业务中，只需要获取配置文件中的某个值，可以使用一下 @value；

    - 如果说，我们专门编写了一个JavaBean来和配置文件进行一一映射，就直接@configurationProperties，不要犹豫！
  
  
## JSR303数据校验

说明：Springboot中可以用@validated来校验数据，如果数据异常则会统一抛出异常，方便异常中心统一处理。

- 例子

```java
package com.ryan.main.config;

import com.ryan.main.bean.School;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "person")
@Validated            //JSR303数据校验注解
public class Person {

    @NotNull(message="名字不能为空")
    private String name;

    @Max(value=120,message="年龄最大不能超过120")
    private int age;

    @Email(message="邮箱格式错误")
    private String email;

    private String address;

    private List hobby;

    private School school;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List getHobby() {
        return hobby;
    }

    public void setHobby(List hobby) {
        this.hobby = hobby;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", hobby=" + hobby +
                ", school=" + school +
                '}';
    }

    @Bean
    public void testPersionConfiguration() {
        System.out.println(this.toString());
    }
}

```

配置文件

```yaml

person:
  name: fuyi
  age: 23
  address: guangzhou
  hobby: [music, basketball, running]
  school:
    name: zqu
    address: zhaoqing
  email: gg

```

运行结果并打印

```text
***************************
APPLICATION FAILED TO START
***************************

Description:

Binding to target org.springframework.boot.context.properties.bind.BindException: Failed to bind properties under 'person' to com.ryan.main.config.Person$$EnhancerBySpringCGLIB$$de36d88a failed:

    Property: person.email
    Value: gg
    Origin: class path resource [application.yml]:10:10
    Reason: 邮箱格式错误


Action:

Update your application's configuration
```

- 常用校验注解

        空检查
        @Null       验证对象是否为null
        @NotNull    验证对象是否不为null, 无法查检长度为0的字符串
        @NotBlank   检查约束字符串是不是Null还有被Trim的长度是否大于0,只对字符串,且会去掉前后空格.
        @NotEmpty   检查约束元素是否为NULL或者是EMPTY.
            
        Booelan检查
        @AssertTrue     验证 Boolean 对象是否为 true  
        @AssertFalse    验证 Boolean 对象是否为 false  
            
        长度检查
        @Size(min=, max=) 验证对象（Array,Collection,Map,String）长度是否在给定的范围之内  
        @Length(min=, max=) string is between min and max included.
        
        日期检查
        @Past       验证 Date 和 Calendar 对象是否在当前时间之前  
        @Future     验证 Date 和 Calendar 对象是否在当前时间之后  
        @Pattern    验证 String 对象是否符合正则表达式的规则
        
        。。等等
        除此以外，我们还可以自定义一些数据校验规则

## 多环境切换

说明：profile是Spring对不同环境提供不同配置功能的支持，可以通过激活不同的环境版本，实现快速切换环境

- 多配置文件

  命名格式
  
  ```text
  application-test.yml    # 测试环境
  application-prod.yml    # 生产环境
  application-dev.yml     # 开发环境
  application-local.yml   # 本地环境
  ```
  
- 环境切换

  说明：在application.yml文件进行配置实现环境切换
  
  ```yaml
  spring:
    profiles:
      active: test
  ```
  
- 配置文件加载位置

    说明：springboot 启动会扫描以下位置的application.properties或者application.yml文件作为Spring boot的默认配置文件：

    - 优先级1：项目路径下的config文件夹配置文件
    - 优先级2：项目路径下配置文件
    - 优先级3：资源路径下的config文件夹配置文件
    - 优先级4：资源路径下配置文件
    
    优先级由高到底，高优先级的配置会覆盖低优先级的配置；
    
    SpringBoot会从这四个位置全部加载主配置文件；互补配置；
    
## 自动配置原理

## 自定义启动器

   说明：启动器模块是一个 空 jar 文件，仅提供辅助性依赖管理，这些依赖可能用于自动装配或者其他类库
 
 
   命名约束：
 
   - 官方命名：
 
     前缀：spring-boot-starter-xxx
     
     比如：spring-boot-starter-web....
 
   - 自定义命名：
 
     xxx-spring-boot-starter
     
     比如：mybatis-spring-boot-starter
     
     
## SpringData

   说明：对于springboot来说，无论是SQL还是NoSQL底层统一使用Spring Data方式进行处理（Spring Data 也是 Spring 中与 Spring Boot、Spring Cloud 等齐名的知名项目）
   
   ![Spring Data官网](https://spring.io/projects/spring-data)
   
   
   
   
     
   
   








