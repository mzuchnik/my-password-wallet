package pl.mzuchnik.mypasswordwallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mzuchnik.mypasswordwallet.encoder.AesEncryptor;
import pl.mzuchnik.mypasswordwallet.entity.Password;
import pl.mzuchnik.mypasswordwallet.entity.User;
import pl.mzuchnik.mypasswordwallet.repo.PasswordRepo;

import java.util.List;

@Service
public class PasswordServiceImpl implements PasswordService {

    private PasswordRepo passwordRepo;

    @Autowired
    public PasswordServiceImpl(PasswordRepo passwordRepo) {
        this.passwordRepo = passwordRepo;
    }

    @Override
    public Password findById(Long id) {
        return passwordRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Cannot find password for id: " + id)
        );
    }

    @Override
    public void save(Password password) {
        passwordRepo.saveAndFlush(password);
    }

    @Override
    public List<Password> findByUserId(Long id) {
        return passwordRepo.findByUserId(id);
    }

    @Override
    public void saveForUser(Password password, User user) {
        password.setUser(user);

        try {
            password.setWebPassword(new AesEncryptor().encrypt(password.getWebPassword(), user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        passwordRepo.save(password);
    }

    @Override
    public boolean passwordIdExits(Long id) {
        return passwordRepo.existsById(id);
    }
}
