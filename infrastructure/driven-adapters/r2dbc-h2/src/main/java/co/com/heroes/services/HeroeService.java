package co.com.heroes.services;

import co.com.heroes.dao.HeroeReactiveRepository;
import co.com.heroes.entity.HeroeEntity;
import co.com.heroes.model.Heroe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HeroeService {

    @Autowired
    private HeroeReactiveRepository heroeReactiveRepository;

    /**
     * Method Get All
     * @return Flux<HeroeEntity>
     */
    public Flux<HeroeEntity> getAll() {
        return heroeReactiveRepository
                .findAll();
    }

    /**
     * Method Get All by Name
     * @param name Name of Heroe
     * @return Flux<HeroeEntity>
     */
    public Flux<HeroeEntity> getByNameContains(String name) {
        return heroeReactiveRepository
                .findByNameContainsIgnoreCase(name);
    }

    /**
     * Method Get by Id
     * @param id Unique Identifier of Heroe
     * @return Mono<HeroeEntity>
     */
    public Mono<HeroeEntity> getById(Long id) {
        return heroeReactiveRepository.findById(id);
    }

    /**
     * Method Save of Update
     * @param heroe Model of Heroe
     * @return Mono<HeroeEntity>
     */
    public Mono<HeroeEntity> saveOrUpdate(HeroeEntity heroe) {
        return heroeReactiveRepository.save(heroe);
    }

    /**
     * Method Delete
     * @param id Unique Identifier of Heroe
     * @return Mono<Boolean>
     */
    public Mono<Boolean> delete(Long id) {
        return heroeReactiveRepository
                .deleteById(id)
                .thenReturn(Boolean.TRUE);
    }
}
