package academy.devdojo.springboot2essentials.util;

import academy.devdojo.springboot2essentials.requests.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {
    public static AnimePutRequestBody createAnimePutRequestBody(){
        return AnimePutRequestBody.Builder.newBuilder()
        .id(AnimeCreator.createValidUpdatedAnime().getId())
        .name(AnimeCreator.createValidUpdatedAnime().getName()).build();
    }
}
