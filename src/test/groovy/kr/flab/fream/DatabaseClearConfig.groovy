package kr.flab.fream

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseClearConfig {

    @Bean
    FlywayMigrationStrategy clean() {
        return (flyway) -> {
            flyway.clean()
            flyway.migrate()
        }
    }

}
