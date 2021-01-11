package pl.mzuchnik.mypasswordwallet.service;

import pl.mzuchnik.mypasswordwallet.entity.User;

import java.util.Optional;


public interface UserService {

    Optional<User> findByLogin(String login);

    void save(User user);

    void saveUserWithPasswordEncoder(User user, String passwordEncoder);

    boolean exitsByLogin(String login);

}
