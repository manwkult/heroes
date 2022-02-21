package co.com.heroes.api.router;

import co.com.heroes.api.handler.HeroesHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class HeroesRouter {

    @Value("${routes.heroes}")
    private String routeHeroes;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(HeroesHandler handler) {
        return route(GET(routeHeroes), handler::getAll)
                .andRoute(GET(routeHeroes.concat("/{id}")), handler::getById)
                .andRoute(POST(routeHeroes), handler::saveOrUpdate)
                .andRoute(PUT(routeHeroes), handler::saveOrUpdate)
                .andRoute(DELETE(routeHeroes.concat("/{id}")), handler::delete);
    }
}
