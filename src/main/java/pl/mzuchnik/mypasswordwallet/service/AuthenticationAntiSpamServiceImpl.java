package pl.mzuchnik.mypasswordwallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mzuchnik.mypasswordwallet.entity.AuthenticationAntiSpam;
import pl.mzuchnik.mypasswordwallet.repo.AuthenticationAntiSpamRepo;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class AuthenticationAntiSpamServiceImpl implements AuthenticationAntiSpamService {

    private AuthenticationAntiSpamRepo antiSpamRepo;

    @Autowired
    public AuthenticationAntiSpamServiceImpl(AuthenticationAntiSpamRepo antiSpamRepo) {
        this.antiSpamRepo = antiSpamRepo;
    }

    @Override
    public void save(AuthenticationAntiSpam antiSpam) {
        antiSpamRepo.save(antiSpam);
    }

    @Override
    public void delete(AuthenticationAntiSpam antiSpam) {
        antiSpamRepo.delete(antiSpam);
    }

    @Override
    public Optional<AuthenticationAntiSpam> findByIpAddress(String ipAddress) {
        return antiSpamRepo.findByIpAddress(ipAddress);
    }
}
