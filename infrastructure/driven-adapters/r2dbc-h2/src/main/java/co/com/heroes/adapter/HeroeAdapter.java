package co.com.heroes.adapter;

import co.com.heroes.entity.HeroeEntity;
import co.com.heroes.model.Heroe;
import co.com.heroes.model.gateway.HeroesGateway;
import co.com.heroes.services.HeroeService;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class HeroeAdapter implements HeroesGateway {

    @Autowired
    private HeroeService heroeService;

    /**
     * Method Get All
     * @return Mono<List<Heroe>>
     */
    @Override
    public Mono<List<Heroe>> getAll() {
        return heroeService
                .getAll()
                .switchIfEmpty(Mono.empty())
                .transform(this::buildResponse)
                .collectList();
    }

    /**
     * Method Get All by Name
     * @param name Name of Heroe
     * @return Mono<List<Heroe>>
     */
    @Override
    public Mono<List<Heroe>> getByNameContains(String name) {
        return heroeService
                .getByNameContains(name)
                .switchIfEmpty(Mono.empty())
                .transform(this::buildResponse)
                .collectList();
    }

    /**
     * Method Get by Id
     * @param id Unique Identifier of Heroe
     * @return Mono<Heroe>
     */
    @Override
    public Mono<Heroe> getById(Long id) {
        return heroeService
                .getById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(response -> {
                    Heroe data = new Heroe();
                    BeanUtils.copyProperties(response, data);

                    return Mono.just(data);
                });
    }

    /**
     * Method Save of Update
     * @param heroe Model of Heroe
     * @return Mono<Heroe>
     */
    @Override
    public Mono<Heroe> saveOrUpdate(Heroe heroe) {
        HeroeEntity heroeEntity = new HeroeEntity();
        BeanUtils.copyProperties(heroe, heroeEntity);

        return heroeService
                .saveOrUpdate(heroeEntity)
                .switchIfEmpty(Mono.empty())
                .flatMap(response -> {
                    Heroe data = new Heroe();
                    BeanUtils.copyProperties(response, data);

                    return Mono.just(data);
                });
    }

    /**
     * Method Delete
     * @param id Unique Identifier of Heroe
     * @return Mono<Boolean>
     */
    @Override
    public Mono<Boolean> delete(Long id) {
        return heroeService.delete(id);
    }


    @SneakyThrows
    private Flux<Heroe> buildResponse(Flux<HeroeEntity> data) {
        List<Heroe> heroes = new ArrayList<>();

        data.collectList().toFuture().get().forEach(item -> {
            Heroe heroe = new Heroe();
            BeanUtils.copyProperties(item, heroe);

            heroes.add(heroe);
        });

        return Mono.just(heroes).flatMapIterable(list -> list);
    }
}
