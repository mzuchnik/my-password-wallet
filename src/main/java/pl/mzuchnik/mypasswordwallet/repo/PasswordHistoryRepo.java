package pl.mzuchnik.mypasswordwallet.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mzuchnik.mypasswordwallet.entity.PasswordHistory;

@Repository
public interface PasswordHistoryRepo extends JpaRepository<PasswordHistory, Long> {
}
