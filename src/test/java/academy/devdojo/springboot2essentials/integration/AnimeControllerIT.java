package academy.devdojo.springboot2essentials.integration;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.domain.DevDojoUser;
import academy.devdojo.springboot2essentials.repository.AnimeRepository;
import academy.devdojo.springboot2essentials.repository.DevDojoUserRepository;
import academy.devdojo.springboot2essentials.requests.AnimePostRequestBody;
import academy.devdojo.springboot2essentials.util.AnimeCreator;
import academy.devdojo.springboot2essentials.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2essentials.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimeControllerIT {
    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate  testRestTemplateUser;

    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate  testRestTemplateAdmin;

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private DevDojoUserRepository devDojoUserRepository;

    private static final DevDojoUser USER = DevDojoUser.Builder.newBuilder().name("william")
    .username("william").password("{bcrypt}$2a$10$iKIgWTlPacZ4CRTSbkS8ye/9DZU2V1SZ16UJqxmDPz775/2otWW5G")
    .authorities("ROLE_USER").build();

    private static final DevDojoUser ADMIN = DevDojoUser.Builder.newBuilder().name("isa")
    .username("isa").password("{bcrypt}$2a$10$iKIgWTlPacZ4CRTSbkS8ye/9DZU2V1SZ16UJqxmDPz775/2otWW5G")
    .authorities("ROLE_USER,ROLE_ADMIN").build();

    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value ("${local.server.port}") int port){
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
            .rootUri("http://localhost:"+port)
            .basicAuthentication("william", "rockblin0123");

            return new TestRestTemplate(restTemplateBuilder);
        }
        
        @Bean(name = "testRestTemplateRoleAdmin")
        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value ("${local.server.port}") int port){
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
            .rootUri("http://localhost:"+port)
            .basicAuthentication("isa", "rockblin0123");

            return new TestRestTemplate(restTemplateBuilder);
        }
    }
    @Test
    @DisplayName("List returns list of anime inside page object when sucessful")
    void list_ReturnsListOfAnimesInsidePageObect_WhenSucessful() {
        devDojoUserRepository.save(USER);
        
        Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        String expectedName = animeSaved.getName();
        
        PageableResponse<Anime> animePage = testRestTemplateUser.exchange("/animes", HttpMethod.GET, null, 
        new ParameterizedTypeReference<PageableResponse<Anime>>(){}).getBody();

       Assertions.assertNotNull(animePage);

       Assertions.assertFalse(animePage.toList().isEmpty());

       Assertions.assertEquals(1, animePage.toList().size());

       Assertions.assertEquals(expectedName, animePage.toList().get(0).getName());
    }

    @Test
    @DisplayName("List returns list of anime when sucessful")
    void listAll_ReturnsListOfAnimes_WhenSucessful() {
        devDojoUserRepository.save(USER);
        
        Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = animeSaved.getName();
        
        List<Anime> animes = testRestTemplateUser.exchange("/animes/all", HttpMethod.GET, null, 
        new ParameterizedTypeReference<List<Anime>>(){}).getBody();

       Assertions.assertNotNull(animes);

       Assertions.assertFalse(animes.isEmpty());

       Assertions.assertEquals(1, animes.size());

       Assertions.assertEquals(expectedName, animes.get(0).getName());
    }

    @Test
    @DisplayName("findById returns anime when sucessful")
    void findById_ReturnsAnime_WhenSucessful() {
        devDojoUserRepository.save(USER);
        
        Anime animeSaved = animeRepository.save(AnimeCreator.createValidAnime());
        Long expectedId = animeSaved.getId();
        
        Anime anime = testRestTemplateUser.getForObject("/anime/{id}", Anime.class, expectedId);

       Assertions.assertNotNull(anime);
    }

    @Test
    @DisplayName("findByName returns a list of anime when sucessful")
    void findByName_ReturnsLisOfAnime_WhenSucessful() {
        devDojoUserRepository.save(USER);
        
        Anime animeSaved = animeRepository.save(AnimeCreator.createValidAnime());
        String expectedName = animeSaved.getName();
        
        List<Anime> animes = testRestTemplateUser.exchange("/animes/find/{name}", HttpMethod.GET, null, 
        new ParameterizedTypeReference<List<Anime>>(){}, expectedName).getBody();

        Assertions.assertNotNull(animes);
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
        devDojoUserRepository.save(USER);
        
        List<Anime> animes = testRestTemplateUser.exchange("/animes/find/{name}", HttpMethod.GET, null, 
        new ParameterizedTypeReference<List<Anime>>(){}, "DBZ").getBody();

        Assertions.assertNotNull(animes);

        Assertions.assertTrue(animes.isEmpty());
    }

    @Test
    @DisplayName("save returns anime when sucessful")
    void save_ReturnsAnime_WhenSucessful() {
        devDojoUserRepository.save(ADMIN);
        
        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
        ResponseEntity<Anime> animeResponseEntity = testRestTemplateAdmin.postForEntity("/animes", animePostRequestBody, Anime.class);

        Assertions.assertNotNull(animeResponseEntity);

        Assertions.assertEquals(animeResponseEntity.getStatusCodeValue(), 201);

        Assertions.assertNotNull(animeResponseEntity.getBody());

        Assertions.assertNotNull(animeResponseEntity.getBody().getId());
    }

    @Test
    @DisplayName("replace updates anime when sucessful")
    void replace_UpdatesAnime_WhenSucessful() {
        devDojoUserRepository.save(USER);

        Anime animeSaved = animeRepository.save(AnimeCreator.createValidAnime());

        animeSaved.setName("new name");

        ResponseEntity<Void> animeResponseEntity = testRestTemplateUser.exchange("/animes", HttpMethod.PUT, new HttpEntity<>(animeSaved) , Void.class);

        Assertions.assertNotNull(animeResponseEntity);

        Assertions.assertEquals(animeResponseEntity.getStatusCodeValue(), 204);
    }

    @Test
    @DisplayName("delete removes anime when sucessful")
    void delete_RemovesAnime_WhenSucessful() {
        devDojoUserRepository.save(ADMIN);
        
        Anime animeSaved = animeRepository.save(AnimeCreator.createValidAnime());

        ResponseEntity<Void> animeResponseEntity = testRestTemplateAdmin.exchange("/animes/admin/{id}", HttpMethod.DELETE, null , Void.class, animeSaved.getId());

        Assertions.assertNotNull(animeResponseEntity);

        Assertions.assertEquals(animeResponseEntity.getStatusCodeValue(), 204);
    }

    @Test
    @DisplayName("delete return 403 when user is not admin")
    void delete_Return403_WhenUserIsNotAdmin() {
        devDojoUserRepository.save(USER);
        
        Anime animeSaved = animeRepository.save(AnimeCreator.createValidAnime());

        ResponseEntity<Void> animeResponseEntity = testRestTemplateUser.exchange("/animes/admin/{id}", HttpMethod.DELETE, null , Void.class, animeSaved.getId());

        Assertions.assertNotNull(animeResponseEntity);

        Assertions.assertEquals(animeResponseEntity.getStatusCodeValue(), 403);
    }
}
