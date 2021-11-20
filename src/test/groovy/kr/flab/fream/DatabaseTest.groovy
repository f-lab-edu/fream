package kr.flab.fream

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import spock.lang.Shared
import spock.lang.Specification

class DatabaseTest extends Specification {

    @Shared
    static final def MYSQL = new MySQLContainer('mysql:8')
        .withConfigurationOverride('db/mysql/conf.d')
        .withDatabaseName('fream')

    static String activeProfile

    static {
        activeProfile = System.getenv('SPRING_PROFILES_ACTIVE');

        if (activeProfile != null && activeProfile != 'local') {
            MYSQL.start()
        }
    }

    @DynamicPropertySource
    static def databaseProperties(DynamicPropertyRegistry registry) {
        if (activeProfile != null && activeProfile != 'local') {
            registry.add("spring.datasource.url", MYSQL::getJdbcUrl)
            registry.add("spring.datasource.username", MYSQL::getUsername)
            registry.add("spring.datasource.password", MYSQL::getPassword)
        }
    }

}
