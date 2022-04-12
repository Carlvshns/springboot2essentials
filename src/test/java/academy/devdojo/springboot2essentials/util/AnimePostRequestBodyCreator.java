package academy.devdojo.springboot2essentials.util;

import academy.devdojo.springboot2essentials.requests.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {
    
    public static AnimePostRequestBody createAnimePostRequestBody(){
        return AnimePostRequestBody.Builder.newBuilder()
        .name(AnimeCreator.createAnimeToBeSaved().getName()).build();
    }
}
