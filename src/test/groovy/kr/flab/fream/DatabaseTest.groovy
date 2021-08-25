package kr.flab.fream

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import spock.lang.Shared
import spock.lang.Specification

class DatabaseTest extends Specification {

    @Shared
    static final def MYSQL = new MySQLContainer("mysql:8")
            .withDatabaseName("fream")

    static {
        MYSQL.start()
    }

    @DynamicPropertySource
    static def databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl)
        registry.add("spring.datasource.username", MYSQL::getUsername)
        registry.add("spring.datasource.password", MYSQL::getPassword)
    }

}
