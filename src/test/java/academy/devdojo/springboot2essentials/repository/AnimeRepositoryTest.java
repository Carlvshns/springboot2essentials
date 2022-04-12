package academy.devdojo.springboot2essentials.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.util.AnimeCreator;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
public class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when Sucessful")
    void save_PersistAnime_WhenSucessful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        
        Assertions.assertNotNull(animeSaved);
        
        Assertions.assertNotNull(animeSaved.getId());
        
        Assertions.assertEquals(animeSaved.getName(), animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates anime when Sucessful")
    void save_UpdateAnime_WhenSucessful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        
        animeSaved.setName("Overlod");
       
        Anime animeUpdated = this.animeRepository.save(animeSaved);
        
        Assertions.assertNotNull(animeUpdated);
        
        Assertions.assertNotNull(animeUpdated.getId());

        Assertions.assertEquals(animeSaved.getName(), animeUpdated.getName());
    }

    @Test
    @DisplayName("Delete removes anime when Sucessful")
    void delete_RemovesAnime_WhenSucessful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        
        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeDeleted = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertTrue(animeDeleted.isEmpty());
    }

    @Test
    @DisplayName("Find By Name returns list of anime when Sucessful")
    void findByName_ReturnsListOfAnime_WhenSucessful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        
        String name = animeSaved.getName();

        List<Anime> animes = this.animeRepository.findByName(name);

        Assertions.assertFalse(animes.isEmpty());
    }

    @Test
    @DisplayName("Find By Name returns empty list of when no anime is found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound(){

        List<Anime> animes = this.animeRepository.findByName("AAAaaAAAAAAAA");

        Assertions.assertTrue(animes.isEmpty());
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_TrhowsConstraintViolationException_WhenNameIsEmpty(){
        Anime anime = new Anime();
        Assertions.assertThrows(ConstraintViolationException.class, () -> this.animeRepository.save(anime));
    }
}