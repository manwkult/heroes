package co.com.heroes.config;

import co.com.heroes.model.gateway.HeroesGateway;
import co.com.heroes.usecase.HeroesUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
public class UseCasesConfig {

        @Bean
        public HeroesUseCase getHeroesUseCase(HeroesGateway heroesGateway) {
                return new HeroesUseCase(heroesGateway);
        }
}
