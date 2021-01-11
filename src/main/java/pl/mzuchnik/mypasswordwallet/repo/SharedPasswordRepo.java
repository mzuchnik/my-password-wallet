package pl.mzuchnik.mypasswordwallet.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mzuchnik.mypasswordwallet.entity.SharedPassword;
import pl.mzuchnik.mypasswordwallet.entity.User;

import java.util.List;

@Repository
public interface SharedPasswordRepo extends JpaRepository<SharedPassword, Long > {

    List<SharedPassword> findByOwner(User user);

    List<SharedPassword> findByConsumer(User user);

}
