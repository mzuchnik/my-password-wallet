package pl.mzuchnik.mypasswordwallet.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mzuchnik.mypasswordwallet.entity.SharedPassword;
import pl.mzuchnik.mypasswordwallet.entity.User;
import pl.mzuchnik.mypasswordwallet.repo.SharedPasswordRepo;
import java.util.List;
import java.util.Optional;

@Service
public class SharedPasswordServiceImpl implements SharedPasswordService{

    private SharedPasswordRepo sharedPasswordRepo;

    @Autowired
    public SharedPasswordServiceImpl(SharedPasswordRepo sharedPasswordRepo) {
        this.sharedPasswordRepo = sharedPasswordRepo;
    }

    @Override
    public List<SharedPassword> findByOwner(User user) {
        return sharedPasswordRepo.findByOwner(user);
    }

    @Override
    public List<SharedPassword> findByConsumer(User user) {
        return sharedPasswordRepo.findByConsumer(user);
    }

    @Override
    public void save(SharedPassword sharedPassword) {
        sharedPasswordRepo.save(sharedPassword);
    }

    @Override
    public Optional<SharedPassword> findById(long id) {
        return sharedPasswordRepo.findById(id);
    }

    @Override
    public void delete(SharedPassword sharedPassword) {
        sharedPasswordRepo.delete(sharedPassword);
    }

    @Override
    public void deleteById(long id) {
        sharedPasswordRepo.deleteById(id);
    }
}
