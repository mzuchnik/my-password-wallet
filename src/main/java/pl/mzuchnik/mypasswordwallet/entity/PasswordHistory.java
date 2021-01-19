package pl.mzuchnik.mypasswordwallet.entity;


import javax.persistence.*;

@Entity
@Table(name = "password_history")
public class PasswordHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long passwordId;

    @Column(name = "address")
    private String webAddress;

    @Column(name = "login")
    private String webLogin;

    @Column(name = "password")
    private String webPassword;

    @Column(name = "description")
    private String webDescription;


    public PasswordHistory() {
    }

    public PasswordHistory(Password password){
        this.passwordId = password.getId();
        this.webAddress = password.getWebAddress();
        this.webLogin = password.getWebLogin();
        this.webPassword = password.getWebPassword();
        this.webDescription = password.getWebDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getWebLogin() {
        return webLogin;
    }

    public void setWebLogin(String webLogin) {
        this.webLogin = webLogin;
    }

    public String getWebPassword() {
        return webPassword;
    }

    public void setWebPassword(String webPassword) {
        this.webPassword = webPassword;
    }

    public String getWebDescription() {
        return webDescription;
    }

    public void setWebDescription(String webDescription) {
        this.webDescription = webDescription;
    }

    public Long getPasswordId() {
        return passwordId;
    }

    public void setPasswordId(Long passwordId) {
        this.passwordId = passwordId;
    }
}
