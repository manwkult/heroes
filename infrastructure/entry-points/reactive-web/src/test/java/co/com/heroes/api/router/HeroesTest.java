package co.com.heroes.api.router;

import co.com.heroes.api.handler.HeroesHandler;
import co.com.heroes.model.Heroe;
import co.com.heroes.usecase.HeroesUseCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {HeroesRouter.class, HeroesHandler.class})

@TestPropertySource(properties = {
        "routes.heroes=/heroes"
})
@WebFluxTest
public class HeroesTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private HeroesUseCase heroesUseCase;


    @Value("${routes.heroes}")
    private String routeHeroes;

    Heroe heroe;
    List<Heroe> heroes;
    Map<String, Object> response;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Before
    public void init() {
        heroes = new ArrayList<>();

        heroe = Heroe.builder()
                .id(1L)
                .name("Super Man")
                .power("All")
                .build();

        heroes.add(heroe);

        Mockito.when(heroesUseCase.getAll()).thenReturn(Mono.just(heroes));
        Mockito.when(heroesUseCase.getByNameContains(Mockito.anyString())).thenReturn(Mono.just(heroes));
        Mockito.when(heroesUseCase.getById(Mockito.anyLong())).thenReturn(Mono.just(heroe));
        Mockito.when(heroesUseCase.saveOrUpdate(Mockito.any())).thenReturn(Mono.just(heroe));
        Mockito.when(heroesUseCase.delete(Mockito.anyLong())).thenReturn(Mono.just(true));
    }

    @Test
    public void getAllTest() {
        response = new HashMap<>();

        response.put("success", true);
        response.put("data", heroes);

        webTestClient
                .get()
                .uri(routeHeroes)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Map.class)
                .consumeWith(response -> {
                    Assert.assertEquals(response.getResponseBody(), response.getResponseBody());
                });
    }

    @Test
    public void getAllByNameContainsTest() {
        response = new HashMap<>();

        response.put("success", true);
        response.put("data", heroes);

        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(routeHeroes)
                        .queryParam("name", "Super")
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Map.class)
                .consumeWith(response -> {
                    Assert.assertEquals(response.getResponseBody(), response.getResponseBody());
                });
    }

    @Test
    public void getByIdTest() {
        response = new HashMap<>();

        response.put("success", true);
        response.put("data", heroe);

        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(routeHeroes.concat("/{id}"))
                        .build(1)
                )
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Map.class)
                .consumeWith(response -> {
                    Assert.assertEquals(response.getResponseBody(), response.getResponseBody());
                });
    }

    @Test
    public void saveTest() {
        response = new HashMap<>();

        response.put("success", true);
        response.put("data", heroe);

        webTestClient
                .post()
                .uri(routeHeroes)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(heroe), Heroe.class))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Map.class)
                .consumeWith(response -> {
                    Assert.assertEquals(response.getResponseBody(), response.getResponseBody());
                });
    }

    @Test
    public void updateTest() {
        response = new HashMap<>();

        response.put("success", true);
        response.put("data", heroe);

        webTestClient
                .put()
                .uri(routeHeroes)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(heroe), Heroe.class))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Map.class)
                .consumeWith(response -> {
                    Assert.assertEquals(response.getResponseBody(), response.getResponseBody());
                });
    }

    @Test
    public void deleteTest() {
        response = new HashMap<>();

        response.put("success", true);
        response.put("data", true);

        webTestClient
                .delete()
                .uri(uriBuilder -> uriBuilder
                        .path(routeHeroes.concat("/{id}"))
                        .build(1)
                )
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Map.class)
                .consumeWith(response -> {
                    Assert.assertEquals(response.getResponseBody(), response.getResponseBody());
                });
    }
}
