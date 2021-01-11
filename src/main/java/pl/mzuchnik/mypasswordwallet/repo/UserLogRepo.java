package pl.mzuchnik.mypasswordwallet.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mzuchnik.mypasswordwallet.entity.User;
import pl.mzuchnik.mypasswordwallet.entity.UserLog;

import java.util.List;

@Repository
public interface UserLogRepo extends JpaRepository<UserLog, Long> {

    List<UserLog> findAllByUserOrderByTimestampDesc(User user);
}
