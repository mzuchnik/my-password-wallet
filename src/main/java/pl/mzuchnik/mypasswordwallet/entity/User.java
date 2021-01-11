package pl.mzuchnik.mypasswordwallet.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy = "user")
    private List<Password> userPasswords;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<SharedPassword> ownerSharedPassword;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "consumer")
    private List<SharedPassword> userSharedPassword;

    private String login;
    private String password;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, List<Password> userPasswords) {
        this.login = login;
        this.password = password;
        this.userPasswords = userPasswords;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Password> getUserPasswords() {
        return userPasswords;
    }

    public void setUserPasswords(List<Password> userPasswords) {
        this.userPasswords = userPasswords;
    }

    public List<SharedPassword> getOwnerSharedPassword() {
        return ownerSharedPassword;
    }

    public void setOwnerSharedPassword(List<SharedPassword> ownerSharedPassword) {
        this.ownerSharedPassword = ownerSharedPassword;
    }

    public List<SharedPassword> getUserSharedPassword() {
        return userSharedPassword;
    }

    public void setUserSharedPassword(List<SharedPassword> userSharedPassword) {
        this.userSharedPassword = userSharedPassword;
    }
}
