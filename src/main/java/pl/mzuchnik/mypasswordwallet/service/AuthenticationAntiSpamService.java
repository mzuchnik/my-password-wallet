package pl.mzuchnik.mypasswordwallet.service;

import com.fasterxml.jackson.annotation.OptBoolean;
import pl.mzuchnik.mypasswordwallet.entity.AuthenticationAntiSpam;

import java.util.Optional;

public interface AuthenticationAntiSpamService{

    void save(AuthenticationAntiSpam antiSpam);

    void delete(AuthenticationAntiSpam antiSpam);

    Optional<AuthenticationAntiSpam> findByIpAddress(String ipAddress);
}
