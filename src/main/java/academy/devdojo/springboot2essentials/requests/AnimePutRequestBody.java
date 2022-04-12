package academy.devdojo.springboot2essentials.requests;

public class AnimePutRequestBody {
    private Long id;
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public AnimePutRequestBody(String name){
        this.name = name;
    }

    public AnimePutRequestBody(){

    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        
        final AnimePutRequestBody other = (AnimePutRequestBody) o;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AnimePutRequestBody " +
        ", id='" +id + '\'' +
		", name='" +name + '\'' +
		'}';
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

        public AnimePutRequestBody build(){
            AnimePutRequestBody anime = new AnimePutRequestBody();
            anime.setId(id);
            anime.setName(name);
            return anime;
        }
    }
}
