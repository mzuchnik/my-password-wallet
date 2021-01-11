package pl.mzuchnik.mypasswordwallet.service;


import pl.mzuchnik.mypasswordwallet.entity.AuthenticationAntiSpam;

import java.sql.Timestamp;
import java.time.Instant;

public class AuthenticationAntiSpamHelper {

    private AuthenticationAntiSpam antiSpam;

    public AuthenticationAntiSpamHelper(AuthenticationAntiSpam antiSpam) {
        this.antiSpam = antiSpam;
    }

    public boolean isPermBanned(){
        return antiSpam.isPermBanned();
    }

    public boolean isTimeLocked(){
        return antiSpam.isTimeLocked();
    }

    public int getNumberOfFailAuth(){
        return antiSpam.getCountOfFails();
    }

    public long getTimeOfLockInSeconds(){
        return (antiSpam.getBlockTime().getTime()
                - antiSpam.getLastFailAuthTime().getTime()) / 1000;
    }

    public void incrementNumberOfFailAuth()
    {
        antiSpam.setCountOfFails(antiSpam.getCountOfFails() + 1);
    }

    public void updateTimeOfLockBySeconds(long lockTimeSeconds){
        antiSpam.setTimeLocked(true);
        antiSpam.setBlockTime(Timestamp.from(
                antiSpam.getLastFailAuthTime()
                        .toInstant().plusSeconds(lockTimeSeconds)));
    }

    public void updateLastTimeFailAuth()
    {
        antiSpam.setLastFailAuthTime(Timestamp.from(Instant.now()));
    }
}
