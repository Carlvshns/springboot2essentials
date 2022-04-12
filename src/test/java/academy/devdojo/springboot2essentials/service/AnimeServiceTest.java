package academy.devdojo.springboot2essentials.service;

import java.util.List;
import java.util.Optional;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.exception.BadRequestException;
import academy.devdojo.springboot2essentials.repository.AnimeRepository;
import academy.devdojo.springboot2essentials.requests.AnimePostRequestBody;
import academy.devdojo.springboot2essentials.requests.AnimePutRequestBody;
import academy.devdojo.springboot2essentials.util.AnimeCreator;
import academy.devdojo.springboot2essentials.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2essentials.util.AnimePutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class AnimeServiceTest {

    @InjectMocks
    private AnimeService animeService;
    @Mock
    private AnimeRepository animeRepositoryMock;

    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
        .thenReturn(animePage);

        BDDMockito.when(animeRepositoryMock.findAll())
        .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
        .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
        .thenReturn(AnimeCreator.createValidAnime());
        
        BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
    }

    @Test
    @DisplayName("ListAll returns list of anime inside page object when sucessful")
    void listAll_ReturnsListOfAnimesInsidePageObect_WhenSucessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeService.listAll(PageRequest.of(1, 1));

        Assertions.assertNotNull(animePage);

        Assertions.assertFalse(animePage.toList().isEmpty());

        Assertions.assertEquals(1, animePage.toList().size());

        Assertions.assertEquals(expectedName, animePage.toList().get(0).getName());
    }

    @Test
    @DisplayName("ListAllNonPageable returns list of anime when sucessful")
    void listAllNonPageable_ReturnsListOfAnimes_WhenSucessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animes = animeService.listAllNonPageable();

        Assertions.assertNotNull(animes);

        Assertions.assertFalse(animes.isEmpty());

        Assertions.assertEquals(1, animes.size());

        Assertions.assertEquals(expectedName, animes.get(0).getName());
    }

    @Test
    @DisplayName("findByIdOrThrowsBadRequestException returns anime when sucessful")
    void findByIdOrThrowsBadRequestException_ReturnsAnime_WhenSucessful() {
        Anime expectedAnime = AnimeCreator.createValidAnime();
        Anime animes = animeService.findByIdOrThrowsBadRequestException(1);

        Assertions.assertNotNull(animes);

        Assertions.assertEquals(expectedAnime.getId(), animes.getId());

        Assertions.assertEquals(expectedAnime.getName(), animes.getName());
    }

    @Test
    @DisplayName("findByIdOrThrowsBadRequestException throws BadRequestException when anime is not found")
    void findByIdOrThrowsBadRequestException_ThrowsBadRequestException_WhenAnimeIsNotFound() {
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.empty());

        Assertions.assertThrows(BadRequestException.class, () -> animeService.findByIdOrThrowsBadRequestException(1));
    }

    @Test
    @DisplayName("findByName returns a list of anime when sucessful")
    void findByName_ReturnsLisOfAnime_WhenSucessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animes = animeService.findByName("BBbbBBBBbbb");

        Assertions.assertNotNull(animes);

        Assertions.assertFalse(animes.isEmpty());

        Assertions.assertEquals(1, animes.size());

        Assertions.assertEquals(expectedName, animes.get(0).getName());
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
        .thenReturn(List.of());

        List<Anime> animes = animeService.findByName("CCCCccc");

        Assertions.assertNotNull(animes);

        Assertions.assertTrue(animes.isEmpty());
    }

    @Test
    @DisplayName("save returns anime when sucessful")
    void save_ReturnsAnime_WhenSucessful() {
        AnimePostRequestBody expectedAnime = AnimePostRequestBodyCreator.createAnimePostRequestBody();
        Anime anime = animeService.save(expectedAnime);

        Assertions.assertNotNull(anime);

        Assertions.assertEquals(expectedAnime, AnimePostRequestBodyCreator.createAnimePostRequestBody());

        Assertions.assertEquals(expectedAnime.getName(), anime.getName());
    }

    @Test
    @DisplayName("replace updates anime when sucessful")
    void replace_UpdatesAnime_WhenSucessful() {
        AnimePutRequestBody expectedAnime = AnimePutRequestBodyCreator.createAnimePutRequestBody();
        Assertions.assertDoesNotThrow(() -> animeService.replace(expectedAnime));
    }

    @Test
    @DisplayName("delete removes anime when sucessful")
    void delete_RemovesAnime_WhenSucessful() {
        Assertions.assertDoesNotThrow(() -> animeService.delete(1L));
    }
}
