package pl.mzuchnik.mypasswordwallet.service;

import pl.mzuchnik.mypasswordwallet.entity.SharedPassword;
import pl.mzuchnik.mypasswordwallet.entity.User;

import java.util.List;
import java.util.Optional;

public interface SharedPasswordService {

    List<SharedPassword> findByOwner(User user);

    List<SharedPassword> findByConsumer(User user);

    Optional<SharedPassword> findById(long id);

    void save(SharedPassword sharedPassword);

    void delete(SharedPassword sharedPassword);

    void deleteById(long id);

}
