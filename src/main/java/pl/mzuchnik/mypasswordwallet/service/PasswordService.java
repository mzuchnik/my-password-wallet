package pl.mzuchnik.mypasswordwallet.service;

import pl.mzuchnik.mypasswordwallet.entity.Password;
import pl.mzuchnik.mypasswordwallet.entity.User;

import java.util.List;

public interface PasswordService {

    Password findById(Long id);

    void save(Password password);

    List<Password> findByUserId(Long id);

    void saveForUser(Password password, User user);

    boolean passwordIdExits(Long id);
}
