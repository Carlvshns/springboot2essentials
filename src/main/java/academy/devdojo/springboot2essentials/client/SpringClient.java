package academy.devdojo.springboot2essentials.client;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import academy.devdojo.springboot2essentials.domain.Anime;

public class SpringClient {
    public static void main(String [] main){
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/2", Anime.class);
        System.out.println(entity);

       Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 2);
       System.out.println(object);

       Anime [] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
       System.out.println(animes.toString());

       ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange
       ("http://localhost:8080/animes/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>(){});
       System.out.println(exchange.getBody());

//     Anime kingdom = Anime.Builder.newBuilder().name("kingdom").build();
//     Anime kingdomSaved = new RestTemplate().postForObject("https://localhost:8080/animes/", kingdom, Anime.class);
//     System.out.println("saved anime:" +kingdomSaved);

        Anime samuraiChamploo = Anime.Builder.newBuilder().name("Samurai Champloo").build();
        ResponseEntity<Anime> samuraiChamplooSaved = new RestTemplate().exchange
        ("https://localhost:8080/animes/", HttpMethod.POST, new HttpEntity<>(samuraiChamploo, createJsonHeader()), Anime.class);
        System.out.println("saved anime:" +samuraiChamplooSaved);

        Anime animeToBeUpdate = samuraiChamplooSaved.getBody();
        animeToBeUpdate.setName("Samurai Champloo 2");
        ResponseEntity<Void> samuraiChamplooUpdate = new RestTemplate().exchange
        ("https://localhost:8080/animes/", HttpMethod.PUT, new HttpEntity<>(animeToBeUpdate, createJsonHeader()), Void.class);
        System.out.println("updated anime:" +samuraiChamplooUpdate);

        ResponseEntity<Void> samuraiChamplooDeleted = new RestTemplate().exchange
        ("https://localhost:8080/animes/{id}", HttpMethod.DELETE, null, Void.class, animeToBeUpdate.getId());
        System.out.println("updated anime:" +samuraiChamplooDeleted);
    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
