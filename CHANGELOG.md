CHANGELOG
=========


## 1.0.3-SNAPSHOT

* changed from Java 1.7 to 1.8
* switched from HSQLDB to H2
* added and am using the HikariCP connection pool, which depends on Java 1.8 and replaced the original dataSource
implmentation org.springframework.jdbc.datasource.DriverManagerDataSource with com.zaxxer.hikari.HikariDataSource
* added the bonecp connection pool but am not using it
* upgraded cucumber-jvm to 1.2.4 -- this realigns cucumber with Spring so that Spring test transaction
rollbacks are supported, the database will be in a clean state after the tests run
* Removed all @EnableLoadTimeWeaving annotations, they weren't being used
* upgraded most plugins and dependencies
* Added badge from versioneye.com which reports on the currentness of the project's dependencies