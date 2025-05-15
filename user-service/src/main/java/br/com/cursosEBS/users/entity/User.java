package br.com.cursosEBS.users.entity;

import br.com.cursosEBS.users.dto.UserRegisterDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Profile> profile;

    public User(UserRegisterDTO user) {
        this.name = user.name();
        this.email = user.email();
        this.password = user.password();

    }

    public User(UserRegisterDTO user, Profile renew) {
        this.name = user.name();
        this.email = user.email();
        this.password = user.password();
        this.profile = List.of(renew);
    }

    public User(Long id) {
        this.id = id;
    }

    public User(User user) {
        this.id = user.id;
        this.name = user.name;
        this.email = user.email;
        this.password = user.password;
        this.profile = user.profile;
        this.createdAt = user.createdAt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.profile;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
