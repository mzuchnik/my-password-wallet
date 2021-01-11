package pl.mzuchnik.mypasswordwallet.entity;


import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "shared_password")
public class SharedPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "consumer")
    private User consumer;

    private Timestamp date;

    private boolean editableBidirectional;

    private String address;

    private String description;

    private String login;

    private String password;

    @Column(name = "owner_note")
    private String ownerNote;

    public SharedPassword() {
    }

    public SharedPassword(User owner, User consumer, boolean editableBidirectional, String address, String description, String login, String password) {
        this.owner = owner;
        this.consumer = consumer;
        this.editableBidirectional = editableBidirectional;
        this.address = address;
        this.description = description;
        this.login = login;
        this.password = password;
        this.date = Timestamp.from(Instant.now());
    }

    public SharedPassword(User owner, User consumer, boolean editableBidirectional, String address, String description, String login, String password, String ownerNote) {
        this(owner,consumer,editableBidirectional,address,description,login,password);
        this.ownerNote = ownerNote;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getConsumer() {
        return consumer;
    }

    public void setConsumer(User consumer) {
        this.consumer = consumer;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public boolean isEditableBidirectional() {
        return editableBidirectional;
    }

    public void setEditableBidirectional(boolean editableBidirectional) {
        this.editableBidirectional = editableBidirectional;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getOwnerNote() {
        return ownerNote;
    }

    public void setOwnerNote(String ownerNote) {
        this.ownerNote = ownerNote;
    }

    @Override
    public String toString() {
        return "SharedPassword{" +
                "id=" + id +
                ", owner=" + owner.getLogin() +
                ", consumer=" + consumer.getLogin() +
                ", date=" + date +
                ", editableBidirectional=" + editableBidirectional +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", ownerNote='" + ownerNote + '\'' +
                '}';
    }
}
