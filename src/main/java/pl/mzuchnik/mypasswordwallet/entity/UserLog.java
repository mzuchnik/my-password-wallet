package pl.mzuchnik.mypasswordwallet.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "userLogs")
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp timestamp;

    private String result;

    private String ipAddress;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    public UserLog() {
    }

    public UserLog(Timestamp timestamp, String result, String ipAddress) {
        this.timestamp = timestamp;
        this.result = result;
        this.ipAddress = ipAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
