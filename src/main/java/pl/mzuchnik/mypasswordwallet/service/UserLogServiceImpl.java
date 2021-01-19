package pl.mzuchnik.mypasswordwallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mzuchnik.mypasswordwallet.entity.User;
import pl.mzuchnik.mypasswordwallet.entity.UserLog;
import pl.mzuchnik.mypasswordwallet.repo.UserLogRepo;

import java.util.List;
import java.util.Optional;

@Service
public class UserLogServiceImpl implements UserLogService{

    private UserLogRepo userLogRepo;

    @Autowired
    public UserLogServiceImpl(UserLogRepo userLogRepo) {
        this.userLogRepo = userLogRepo;
    }

    @Override
    public List<UserLog> findAllByUser(User user) {
        return userLogRepo.findAllByUserOrderByTimestampDesc(user);
    }

    @Override
    public void save(UserLog userLog) {
        userLogRepo.save(userLog);
    }

    @Override
    public Optional<UserLog> findById(long id) {
        return userLogRepo.findById(id);
    }
}
