package academy.devdojo.springboot2essentials.util;

import academy.devdojo.springboot2essentials.domain.Anime;

public class AnimeCreator {
    
    public static Anime createAnimeToBeSaved(){
        return Anime.Builder.newBuilder().name("Hajime no Ippo").build();
    }

    public static Anime createValidAnime(){
        return Anime.Builder.newBuilder().name("Hajime no Ippo").id(1L).build();
    }

    public static Anime createValidUpdatedAnime(){
        return Anime.Builder.newBuilder().name("Hajime no Ippo 2").id(1L).build();
    }
}
