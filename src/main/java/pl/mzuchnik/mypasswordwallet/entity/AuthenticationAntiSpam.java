package pl.mzuchnik.mypasswordwallet.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
public class AuthenticationAntiSpam {

    @Id
    private String ipAddress;

    private int countOfFails;

    private Timestamp blockTime;

    private boolean isPermBanned;

    private boolean isTimeLocked;

    private Timestamp lastFailAuthTime;

    public AuthenticationAntiSpam() {
    }

    public AuthenticationAntiSpam(String ipAddress) {
        this.ipAddress = ipAddress;
        this.countOfFails = 0;
        this.isPermBanned = false;
        this.isTimeLocked = false;
        this.lastFailAuthTime = Timestamp.from(Instant.now());
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getCountOfFails() {
        return countOfFails;
    }

    public void setCountOfFails(int countOfFails) {
        this.countOfFails = countOfFails;
    }

    public Timestamp getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(Timestamp blockTime) {
        this.blockTime = blockTime;
    }

    public boolean isPermBanned() {
        return isPermBanned;
    }

    public void setPermBanned(boolean permBanned) {
        isPermBanned = permBanned;
    }

    public boolean isTimeLocked() {
        return isTimeLocked;
    }

    public void setTimeLocked(boolean timeLocked) {
        isTimeLocked = timeLocked;
    }

    public Timestamp getLastFailAuthTime() {
        return lastFailAuthTime;
    }

    public void setLastFailAuthTime(Timestamp lastFailAuthTime) {
        this.lastFailAuthTime = lastFailAuthTime;
    }
}
