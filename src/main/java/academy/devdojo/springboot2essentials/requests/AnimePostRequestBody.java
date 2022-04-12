package academy.devdojo.springboot2essentials.requests;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;

public class AnimePostRequestBody {
    
    @NotEmpty(message = "The anime name cannot be empty")
    @Schema(description = "The is the anime Anime's name", example = "Tensei Shitarra Slime Datta Ken")
    private String name;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public AnimePostRequestBody(String name){
        this.name = name;
    }

    public AnimePostRequestBody(){

    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        
        final AnimePostRequestBody other = (AnimePostRequestBody) o;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AnimePostRequestBody " +
		", name='" +name + '\'' +
		'}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    public static final class Builder{
        private String name;

        private Builder (){
        }

        public static Builder newBuilder(){
            return new Builder();
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }


        public AnimePostRequestBody build(){
            AnimePostRequestBody anime = new AnimePostRequestBody();
            anime.setName(name);
            return anime;
        }
    }
}
