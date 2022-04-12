package academy.devdojo.springboot2essentials.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "The anime name cannot be empty")
    private String name;

    public Anime (Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Anime (String name){
        this.name = name;
    }

    public Anime (){

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Anime " +
		"id='" + id + '\'' +
		", name='" +name + '\'' +
		'}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        
        final Anime other = (Anime) o;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = (int) (53 * hash + this.id);
        return hash;
    }

    public static final class Builder{
        private long id;
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

        public Builder id(long id){
            this.id = id;
            return this;
        }

        public Anime build(){
            Anime anime = new Anime();
            anime.setId(id);
            anime.setName(name);
            return anime;
        }
    }
}