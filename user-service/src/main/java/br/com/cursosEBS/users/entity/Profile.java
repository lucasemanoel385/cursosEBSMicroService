package br.com.cursosEBS.users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
@Table(name = "profile")
@AllArgsConstructor
@NoArgsConstructor
public class Profile implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Profile(String profilee) {
        this.name = profilee;
    }

    public Profile(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return "ROLE_"+ this.name;
    }
}
