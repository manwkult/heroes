package co.com.heroes.api.handler;

import co.com.heroes.model.Heroe;
import co.com.heroes.usecase.HeroesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HeroesHandler {
    private final HeroesUseCase heroesUseCase;

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        return serverRequest.queryParam("name")
                .map(heroesUseCase::getByNameContains)
                .orElseGet(heroesUseCase::getAll)
                .flatMap(this::buildResponse);
    }

    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        Long id = Long.valueOf(serverRequest.pathVariable("id"));

        return heroesUseCase
                .getById(id)
                .flatMap(this::buildResponse);
    }

    public Mono<ServerResponse> saveOrUpdate(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(Heroe.class)
                .single()
                .flatMap(heroesUseCase::saveOrUpdate)
                .flatMap(this::buildResponse);
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        Long id = Long.valueOf(serverRequest.pathVariable("id"));

        return heroesUseCase
                .delete(id)
                .flatMap(this::buildResponse);
    }

    private Mono<ServerResponse> buildResponse(Object data) {
        Map<String, Object> response = new HashMap<>();

        response.put("success", data != null);
        response.put("data", data);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(response);
    }
}
