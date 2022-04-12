package academy.devdojo.springboot2essentials.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.requests.AnimePostRequestBody;
import academy.devdojo.springboot2essentials.requests.AnimePutRequestBody;
import academy.devdojo.springboot2essentials.service.AnimeService;
import academy.devdojo.springboot2essentials.util.AnimeCreator;
import academy.devdojo.springboot2essentials.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2essentials.util.AnimePutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class AnimeControllerTest {
    
    @InjectMocks
    private AnimeController animeController;
    @Mock
    private AnimeService animeServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
        .thenReturn(animePage);

        BDDMockito.when(animeServiceMock.listAllNonPageable())
        .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.findByIdOrThrowsBadRequestException(ArgumentMatchers.anyLong()))
        .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
        .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
        .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));
        
        BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("List returns list of anime inside page object when sucessful")
    void list_ReturnsListOfAnimesInsidePageObect_WhenSucessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeController.list(null).getBody();

        Assertions.assertNotNull(animePage);

        Assertions.assertFalse(animePage.toList().isEmpty());

        Assertions.assertEquals(1, animePage.toList().size());

        Assertions.assertEquals(expectedName, animePage.toList().get(0).getName());
    }

    @Test
    @DisplayName("List returns list of anime when sucessful")
    void listAll_ReturnsListOfAnimes_WhenSucessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animes = animeController.listAll().getBody();

        Assertions.assertNotNull(animes);

        Assertions.assertFalse(animes.isEmpty());

        Assertions.assertEquals(1, animes.size());

        Assertions.assertEquals(expectedName, animes.get(0).getName());
    }

    @Test
    @DisplayName("findById returns anime when sucessful")
    void findById_ReturnsAnime_WhenSucessful() {
        Anime expectedAnime = AnimeCreator.createValidAnime();
        Anime animes = animeController.findById(1).getBody();

        Assertions.assertNotNull(animes);

        Assertions.assertEquals(expectedAnime.getId(), animes.getId());

        Assertions.assertEquals(expectedAnime.getName(), animes.getName());
    }

    @Test
    @DisplayName("findByName returns a list of anime when sucessful")
    void findByName_ReturnsLisOfAnime_WhenSucessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animes = animeController.findByName("").getBody();

        Assertions.assertNotNull(animes);

        Assertions.assertFalse(animes.isEmpty());

        Assertions.assertEquals(1, animes.size());

        Assertions.assertEquals(expectedName, animes.get(0).getName());
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
        .thenReturn(List.of());

        List<Anime> animes = animeController.findByName("").getBody();

        Assertions.assertNotNull(animes);

        Assertions.assertTrue(animes.isEmpty());
    }

    @Test
    @DisplayName("save returns anime when sucessful")
    void save_ReturnsAnime_WhenSucessful() {
        AnimePostRequestBody expectedAnime = AnimePostRequestBodyCreator.createAnimePostRequestBody();
        Anime anime = animeController.save(expectedAnime).getBody();

        Assertions.assertNotNull(anime);

        Assertions.assertEquals(expectedAnime, AnimePostRequestBodyCreator.createAnimePostRequestBody());

        Assertions.assertEquals(expectedAnime.getName(), anime.getName());
    }

    @Test
    @DisplayName("replace updates anime when sucessful")
    void replace_UpdatesAnime_WhenSucessful() {
        AnimePutRequestBody expectedAnime = AnimePutRequestBodyCreator.createAnimePutRequestBody();
        Assertions.assertDoesNotThrow(() -> animeController.replace(expectedAnime));

        ResponseEntity<Void> entity = animeController.replace(expectedAnime);
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
    }

    @Test
    @DisplayName("delete removes anime when sucessful")
    void delete_RemovesAnime_WhenSucessful() {
        Assertions.assertDoesNotThrow(() -> animeController.deleteById(1L));

        ResponseEntity<Void> entity = animeController.deleteById(1L);
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
    }
}
