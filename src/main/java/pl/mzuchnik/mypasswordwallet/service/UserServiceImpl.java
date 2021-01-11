package pl.mzuchnik.mypasswordwallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mzuchnik.mypasswordwallet.encoder.AesEncryptor;
import pl.mzuchnik.mypasswordwallet.encoder.HmacPasswordEncoder;
import pl.mzuchnik.mypasswordwallet.entity.Password;
import pl.mzuchnik.mypasswordwallet.entity.User;
import pl.mzuchnik.mypasswordwallet.repo.UserRepo;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public void saveUserWithPasswordEncoder(User user, String passwordEncoder) {
        PasswordEncoder encoder = getPasswordEncoder(passwordEncoder);
        user.setPassword("{"+passwordEncoder+"}" + encoder.encode(user.getPassword()));

        if(user.getUserPasswords() != null)
            for (Password userPassword : user.getUserPasswords()) {
                userPassword.setWebPassword(new AesEncryptor().encrypt(userPassword.getWebPassword(), user.getPassword()));
            }
        userRepo.save(user);
    }

    private PasswordEncoder getPasswordEncoder(String passwordEncoder) {
        switch (passwordEncoder)
        {
            case "bcrypt": return new BCryptPasswordEncoder();
            case "hmac": return new HmacPasswordEncoder();
            default: return null;
        }
    }

    @Override
    public boolean exitsByLogin(String login) {
        return userRepo.existsByLogin(login);
    }
}
