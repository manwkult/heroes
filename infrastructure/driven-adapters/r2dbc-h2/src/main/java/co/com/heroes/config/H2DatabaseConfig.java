package co.com.heroes.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
public class H2DatabaseConfig {

    @Bean
    ApplicationRunner init(DatabaseClient client) {
        return args -> {
            client.sql("create table IF NOT EXISTS heroe" +
                            "(id SERIAL PRIMARY KEY, name varchar (255) not null, power varchar (255) not null);")
                    .fetch()
                    .first()
                    .subscribe();

            client.sql("DELETE FROM heroe;")
                    .fetch()
                    .first()
                    .subscribe();
        };
    }
}
