package co.com.heroes.model.gateway;

import co.com.heroes.model.Heroe;
import reactor.core.publisher.Mono;

import java.util.List;

public interface HeroesGateway {
    Mono<List<Heroe>> getAll();
    Mono<List<Heroe>> getByNameContains(String name);
    Mono<Heroe> getById(Long id);
    Mono<Heroe> saveOrUpdate(Heroe heroe);
    Mono<Boolean>delete(Long id);
}
