package co.com.heroes.dao;

import co.com.heroes.entity.HeroeEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@Repository
public interface HeroeReactiveRepository extends ReactiveCrudRepository<HeroeEntity, Long> {

    Flux<HeroeEntity> findByNameContainsIgnoreCase(String name);
}
