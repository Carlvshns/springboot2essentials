package academy.devdojo.springboot2essentials.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class DevDojoUser implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "The User's name cannot be empty")
    private String name;

    private String username;
    private String password;
    private String authorities;

    public DevDojoUser (Long id, String name){
        this.id = id;
        this.name = name;
    }

    public DevDojoUser (String name){
        this.name = name;
    }

    public DevDojoUser (){

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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "DevDojoUser " +
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
        
        final DevDojoUser other = (DevDojoUser) o;
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
        private String username;
        private String password;
        private String authorities;

        private Builder (){
        }

        public static Builder newBuilder(){
            return new Builder();
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder username(String username){
            this.username = username;
            return this;
        }

        public Builder authorities(String authorities){
            this.authorities = authorities;
            return this;
        }

        public Builder password(String password){
            this.password = password;
            return this;
        }

        public Builder id(long id){
            this.id = id;
            return this;
        }

        public DevDojoUser build(){
            DevDojoUser devDojoUser = new DevDojoUser();
            devDojoUser.setId(id);
            devDojoUser.setName(name);
            devDojoUser.setUsername(username);
            devDojoUser.setPassword(password);
            devDojoUser.setAuthorities(authorities);
            return devDojoUser;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(authorities.split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
