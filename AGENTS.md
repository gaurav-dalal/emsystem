# AGENTS.md

## Cursor Cloud specific instructions

### Overview

Spring Boot 3.3.5 REST API (Java 17, Maven, MySQL) for employee/department/project management. Single-service app with four CRUD resources.

### Running services

**MySQL** must be running before the application starts. In the cloud VM, MySQL is installed but its systemd post-install fails. Start it manually:

```bash
sudo mkdir -p /var/run/mysqld && sudo chown mysql:mysql /var/run/mysqld
sudo mysqld --user=mysql --datadir=/var/lib/mysql --socket=/var/run/mysqld/mysqld.sock --pid-file=/var/run/mysqld/mysqld.pid &
sleep 3
```

The app connects to `localhost:3306` as `root`/`root`. The root password is set via:

```bash
sudo mysql -u root -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root'; FLUSH PRIVILEGES;"
```

### Common commands

| Task | Command |
|------|---------|
| Run tests | `mvn test` (uses H2 in-memory DB; no MySQL needed) |
| Build | `mvn package -DskipTests` |
| Run app | `mvn spring-boot:run` (requires MySQL on port 3306) |

`JAVA_HOME` must be set to `/usr/lib/jvm/java-17-openjdk-amd64` (Java 17 is required; Java 21 is the default on the VM).

### Gotchas

- The app seeds the database on first run (60 employees, 6 departments, 9 projects, 40 assignments). Subsequent runs do not re-seed because Hibernate `ddl-auto=update` preserves existing data.
- The `application.properties` has `createDatabaseIfNotExist=true`, so no manual DB creation is needed.
- Controller tests use `@WebMvcTest` with mocked services; service tests use plain Mockito. Both run without MySQL.
- No lint tool is configured in the project; `mvn compile` serves as the closest static check.
