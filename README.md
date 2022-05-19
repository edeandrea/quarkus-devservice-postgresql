I can reproduce this issue pretty regularly on a 2021 MacBook M1Pro. I even [created a 2-minute video](Dev-Services-postgres-failing.mov) showing this. I've been able to reproduce this as far back as Quarkus 2.7.5.Final, although I didn't try it on any versions before that.

The Java version doesn't seem to matter. I've tried both 11 & 17. 

The project was created with this command:

```shell
quarkus create app -x jdbc-postgresql,resteasy-reactive-jackson,hibernate-orm-panache quarkus-devservice-postgresql
```

After creation, the [`Fruit` class](src/main/java/org/acme/Fruit.java) was created as a simple JPA Entity class so that hibernate would kick in at startup.

Then running `./mvnw clean quarkus:dev` I get this:

```
2022-05-19 07:59:06,087 INFO  [io.qua.dat.dep.dev.DevServicesDatasourceProcessor] (build-45) Dev Services for the default datasource (postgresql) started.
2022-05-19 07:59:06,089 INFO  [io.qua.hib.orm.dep.HibernateOrmProcessor] (build-50) Setting quarkus.hibernate-orm.database.generation=drop-and-create to initialize Dev Services managed database
__  ____  __  _____   ___  __ ____  ______ 
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/ 
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \   
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/   
2022-05-19 07:59:06,513 WARN  [io.agr.pool] (agroal-11) Datasource '<default>': Connection to localhost:49153 refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections.

2022-05-19 07:59:06,513 WARN  [org.hib.eng.jdb.env.int.JdbcEnvironmentInitiator] (JPA Startup Thread: <default>) HHH000342: Could not obtain connection to query metadata: org.postgresql.util.PSQLException: Connection to localhost:49153 refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections.
        at org.postgresql.core.v3.ConnectionFactoryImpl.openConnectionImpl(ConnectionFactoryImpl.java:319)
        at org.postgresql.core.ConnectionFactory.openConnection(ConnectionFactory.java:49)
        at org.postgresql.jdbc.PgConnection.<init>(PgConnection.java:223)
        at org.postgresql.Driver.makeConnection(Driver.java:400)
        at org.postgresql.Driver.connect(Driver.java:259)
        at io.agroal.pool.ConnectionFactory.createConnection(ConnectionFactory.java:226)
        at io.agroal.pool.ConnectionPool$CreateConnectionTask.call(ConnectionPool.java:535)
        at io.agroal.pool.ConnectionPool$CreateConnectionTask.call(ConnectionPool.java:516)
        at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
        at io.agroal.pool.util.PriorityScheduledExecutor.beforeExecute(PriorityScheduledExecutor.java:75)
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1126)
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
        at java.base/java.lang.Thread.run(Thread.java:829)
Caused by: java.net.ConnectException: Connection refused (Connection refused)
        at java.base/java.net.PlainSocketImpl.socketConnect(Native Method)
        at java.base/java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:412)
        at java.base/java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:255)
        at java.base/java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:237)
        at java.base/java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392)
        at java.base/java.net.Socket.connect(Socket.java:615)
        at org.postgresql.core.PGStream.createSocket(PGStream.java:241)
        at org.postgresql.core.PGStream.<init>(PGStream.java:98)
        at org.postgresql.core.v3.ConnectionFactoryImpl.tryConnect(ConnectionFactoryImpl.java:109)
        at org.postgresql.core.v3.ConnectionFactoryImpl.openConnectionImpl(ConnectionFactoryImpl.java:235)
        ... 12 more


2022-05-19 07:59:06,659 WARN  [io.agr.pool] (agroal-11) Datasource '<default>': Connection to localhost:49153 refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections.
2022-05-19 07:59:06,659 WARN  [org.hib.eng.jdb.spi.SqlExceptionHelper] (JPA Startup Thread: <default>) SQL Error: 0, SQLState: 08001
2022-05-19 07:59:06,660 ERROR [org.hib.eng.jdb.spi.SqlExceptionHelper] (JPA Startup Thread: <default>) Connection to localhost:49153 refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections.
2022-05-19 07:59:06,673 ERROR [io.qua.run.Application] (Quarkus Main Thread) Failed to start application (with profile dev): java.net.ConnectException: Connection refused (Connection refused)
        at java.base/java.net.PlainSocketImpl.socketConnect(Native Method)
        at java.base/java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:412)
        at java.base/java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:255)
        at java.base/java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:237)
        at java.base/java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392)
        at java.base/java.net.Socket.connect(Socket.java:615)
        at org.postgresql.core.PGStream.createSocket(PGStream.java:241)
        at org.postgresql.core.PGStream.<init>(PGStream.java:98)
        at org.postgresql.core.v3.ConnectionFactoryImpl.tryConnect(ConnectionFactoryImpl.java:109)
        at org.postgresql.core.v3.ConnectionFactoryImpl.openConnectionImpl(ConnectionFactoryImpl.java:235)
        at org.postgresql.core.ConnectionFactory.openConnection(ConnectionFactory.java:49)
        at org.postgresql.jdbc.PgConnection.<init>(PgConnection.java:223)
        at org.postgresql.Driver.makeConnection(Driver.java:400)
        at org.postgresql.Driver.connect(Driver.java:259)
        at io.agroal.pool.ConnectionFactory.createConnection(ConnectionFactory.java:226)
        at io.agroal.pool.ConnectionPool$CreateConnectionTask.call(ConnectionPool.java:535)
        at io.agroal.pool.ConnectionPool$CreateConnectionTask.call(ConnectionPool.java:516)
        at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
        at io.agroal.pool.util.PriorityScheduledExecutor.beforeExecute(PriorityScheduledExecutor.java:75)
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1126)
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
        at java.base/java.lang.Thread.run(Thread.java:829)


2022-05-19 07:59:06,674 INFO  [io.qua.dep.dev.IsolatedDevModeMain] (main) Attempting to start live reload endpoint to recover from previous Quarkus startup failure
```

If I then press `s` in the dev console to force a restart everything then works fine.

I also see the same error if I try to run `./mvnw verify`:

```
[INFO] --- maven-surefire-plugin:3.0.0-M5:test (default-test) @ quarkus-devservice-postgresql ---
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running org.acme.GreetingResourceTest
2022-05-19 08:00:44,264 INFO  [org.jbo.threads] (main) JBoss Threads version 3.4.2.Final
2022-05-19 08:00:44,498 INFO  [org.tes.doc.DockerClientProviderStrategy] (build-53) Loaded org.testcontainers.dockerclient.UnixSocketClientProviderStrategy from ~/.testcontainers.properties, will try it first
2022-05-19 08:00:54,791 INFO  [org.tes.doc.DockerClientProviderStrategy] (build-53) Found Docker environment with local Unix socket (unix:///var/run/docker.sock)
2022-05-19 08:00:54,793 INFO  [org.tes.DockerClientFactory] (build-53) Docker host IP address is localhost
2022-05-19 08:00:54,831 INFO  [org.tes.DockerClientFactory] (build-53) Connected to docker: 
  Server Version: 20.10.14
  API Version: 1.41
  Operating System: Alpine Linux v3.15
  Total Memory: 5925 MB
2022-05-19 08:00:54,834 INFO  [org.tes.DockerClientFactory] (build-53) Checking the system...
2022-05-19 08:00:54,835 INFO  [org.tes.DockerClientFactory] (build-53) ✔︎ Docker server version should be at least 1.6.0
2022-05-19 08:00:54,837 INFO  [org.tes.uti.ImageNameSubstitutor] (build-53) Image name substitution will be performed by: DefaultImageNameSubstitutor (composite of 'ConfigurationFileImageNameSubstitutor' and 'PrefixingImageNameSubstitutor')
2022-05-19 08:00:55,222 INFO  [org.tes.DockerClientFactory] (build-53) ✔︎ Docker environment should have more than 2GB free disk space
2022-05-19 08:00:55,495 INFO  [🐳 .io/.2]] (build-53) Creating container for image: docker.io/postgres:14.2
2022-05-19 08:00:55,516 INFO  [🐳 .io/.2]] (build-53) Container docker.io/postgres:14.2 is starting: bf030305263471bbe83efe9def53254a4cf85e92d636c5c0bf28c1b0edc00b33
2022-05-19 08:00:56,518 INFO  [🐳 .io/.2]] (build-53) Container docker.io/postgres:14.2 started in PT1.042902S
2022-05-19 08:00:56,518 INFO  [io.qua.dev.pos.dep.PostgresqlDevServicesProcessor] (build-53) Dev Services for PostgreSQL started.
2022-05-19 08:00:56,519 INFO  [io.qua.dat.dep.dev.DevServicesDatasourceProcessor] (build-53) Dev Services for the default datasource (postgresql) started.
2022-05-19 08:00:56,521 INFO  [io.qua.hib.orm.dep.HibernateOrmProcessor] (build-53) Setting quarkus.hibernate-orm.database.generation=drop-and-create to initialize Dev Services managed database
2022-05-19 08:00:57,029 WARN  [io.agr.pool] (agroal-11) Datasource '<default>': Connection to localhost:49154 refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections.
2022-05-19 08:00:57,030 WARN  [org.hib.eng.jdb.env.int.JdbcEnvironmentInitiator] (JPA Startup Thread: <default>) HHH000342: Could not obtain connection to query metadata: org.postgresql.util.PSQLException: Connection to localhost:49154 refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections.
        at org.postgresql.core.v3.ConnectionFactoryImpl.openConnectionImpl(ConnectionFactoryImpl.java:319)
        at org.postgresql.core.ConnectionFactory.openConnection(ConnectionFactory.java:49)
        at org.postgresql.jdbc.PgConnection.<init>(PgConnection.java:223)
        at org.postgresql.Driver.makeConnection(Driver.java:400)
        at org.postgresql.Driver.connect(Driver.java:259)
        at io.agroal.pool.ConnectionFactory.createConnection(ConnectionFactory.java:226)
        at io.agroal.pool.ConnectionPool$CreateConnectionTask.call(ConnectionPool.java:535)
        at io.agroal.pool.ConnectionPool$CreateConnectionTask.call(ConnectionPool.java:516)
        at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
        at io.agroal.pool.util.PriorityScheduledExecutor.beforeExecute(PriorityScheduledExecutor.java:75)
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1126)
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
        at java.base/java.lang.Thread.run(Thread.java:829)
Caused by: java.net.ConnectException: Connection refused (Connection refused)
        at java.base/java.net.PlainSocketImpl.socketConnect(Native Method)
        at java.base/java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:412)
        at java.base/java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:255)
        at java.base/java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:237)
        at java.base/java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392)
        at java.base/java.net.Socket.connect(Socket.java:615)
        at org.postgresql.core.PGStream.createSocket(PGStream.java:241)
        at org.postgresql.core.PGStream.<init>(PGStream.java:98)
        at org.postgresql.core.v3.ConnectionFactoryImpl.tryConnect(ConnectionFactoryImpl.java:109)
        at org.postgresql.core.v3.ConnectionFactoryImpl.openConnectionImpl(ConnectionFactoryImpl.java:235)
        ... 12 more

2022-05-19 08:00:57,138 WARN  [io.agr.pool] (agroal-11) Datasource '<default>': Connection to localhost:49154 refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections.
2022-05-19 08:00:57,139 WARN  [org.hib.eng.jdb.spi.SqlExceptionHelper] (JPA Startup Thread: <default>) SQL Error: 0, SQLState: 08001
2022-05-19 08:00:57,139 ERROR [org.hib.eng.jdb.spi.SqlExceptionHelper] (JPA Startup Thread: <default>) Connection to localhost:49154 refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections.
[ERROR] Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 14.297 s <<< FAILURE! - in org.acme.GreetingResourceTest
[ERROR] org.acme.GreetingResourceTest.testHelloEndpoint  Time elapsed: 0.001 s  <<< ERROR!
java.lang.RuntimeException: java.lang.RuntimeException: Failed to start quarkus
Caused by: java.lang.RuntimeException: Failed to start quarkus
Caused by: java.lang.RuntimeException: javax.persistence.PersistenceException: [PersistenceUnit: <default>] Unable to build Hibernate SessionFactory
Caused by: javax.persistence.PersistenceException: [PersistenceUnit: <default>] Unable to build Hibernate SessionFactory
Caused by: org.hibernate.exception.JDBCConnectionException: Unable to open JDBC Connection for DDL execution
Caused by: org.postgresql.util.PSQLException: Connection to localhost:49154 refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections.
Caused by: java.net.ConnectException: Connection refused (Connection refused)

[INFO] 
[INFO] Results:
[INFO] 
[ERROR] Errors: 
[ERROR]   GreetingResourceTest.testHelloEndpoint » Runtime java.lang.RuntimeException: F...
[INFO] 
[ERROR] Tests run: 1, Failures: 0, Errors: 1, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  17.250 s
[INFO] Finished at: 2022-05-19T08:00:57-04:00
[INFO] ------------------------------------------------------------------------
```

I've also tried switching the `quarkus-jdbc-postgresql` to `quarkus-jdbc-mysql`, `quarkus-jdbc-mariadb`, or `quarkus-jdbc-mssql` and the problem goes away.