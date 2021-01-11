package pl.mzuchnik.mypasswordwallet.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mzuchnik.mypasswordwallet.entity.AuthenticationAntiSpam;

import java.util.Optional;

@Repository
public interface AuthenticationAntiSpamRepo extends JpaRepository<AuthenticationAntiSpam, String> {

    Optional<AuthenticationAntiSpam> findByIpAddress(String ipAddress);

}
