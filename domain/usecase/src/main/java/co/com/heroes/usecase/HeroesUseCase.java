package co.com.heroes.usecase;

import co.com.heroes.model.Heroe;
import co.com.heroes.model.gateway.HeroesGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class HeroesUseCase {
    private final HeroesGateway heroesGateway;

    /**
     * Method Get All
     * @return Mono<List<Heroe>
     */
    public Mono<List<Heroe>> getAll() {
        return heroesGateway.getAll();
    }

    /**
     * Method Get All by Name
     * @param name Name of Heroe
     * @return Mono<List<Heroe>>
     */
    public Mono<List<Heroe>> getByNameContains(String name) {
        return heroesGateway.getByNameContains(name);
    }

    /**
     * Method Get by Id
     * @param id Unique Identifier of Heroe
     * @return Mono<Heroe>
     */
    public Mono<Heroe> getById(Long id) {
        return heroesGateway.getById(id);
    }

    /**
     * Method Save of Update
     * @param heroe Model of Heroe
     * @return Mono<Heroe>
     */
    public Mono<Heroe> saveOrUpdate(Heroe heroe) {
        return heroesGateway
                .saveOrUpdate(heroe)
                .doOnError(error -> {
                    System.out.println(error.getMessage());
                });
    }

    /**
     * Method Delete
     * @param id Unique Identifier of Heroe
     * @return Mono<Boolean>
     */
    public Mono<Boolean> delete(Long id) {
        return heroesGateway.delete(id);
    }
}
