package it.polimi.biblioteca.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Utente")
@Table(
        name = "utente",
        uniqueConstraints = @UniqueConstraint(
                name = "username_unique",
                columnNames = "username"))
public class Utente implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1)
    @GeneratedValue(
            generator = "user_sequence",
            strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "username", nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String username;

    @Column(name = "nome", nullable = false, columnDefinition = "VARCHAR(50)")
    private String nome;

    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(100)")
    private String email;

    @Column(name = "telefono", nullable = false, columnDefinition = "VARCHAR(15)")
    private String telefono;

    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(100)")
    private String password;

    @Column
    private String comunita;

    @Column
    private boolean notifica;

    @Column
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    @OneToMany(mappedBy = "mittente", fetch = FetchType.LAZY)
    private List<Messaggio> messaggiInviati;

    @OneToMany(mappedBy = "destinatario", fetch = FetchType.LAZY)
    private List<Messaggio> messaggiRicevuti;

    @OneToMany(mappedBy = "proprietario", fetch = FetchType.LAZY)
    private List<Libro> libri;

    @OneToMany(mappedBy = "utente", fetch = FetchType.LAZY)
    private List<Notifica> notifiche;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Genere> generiPreferiti;

    @OneToMany(mappedBy = "proprietario", fetch = FetchType.LAZY)
    private List<Scambio> scambiProposti;

    @OneToMany(mappedBy = "richiedente", fetch = FetchType.LAZY)
    private List<Scambio> scambiRichiesti;

    @OneToMany(mappedBy = "utente", fetch = FetchType.LAZY)
    private List<Recensione> recensioni;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + ruolo.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
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
