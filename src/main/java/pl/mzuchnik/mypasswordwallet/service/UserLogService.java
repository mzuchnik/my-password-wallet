package pl.mzuchnik.mypasswordwallet.service;

import pl.mzuchnik.mypasswordwallet.entity.User;
import pl.mzuchnik.mypasswordwallet.entity.UserLog;

import java.util.List;

public interface UserLogService {

    List<UserLog> findAllByUser(User user);

    void save(UserLog userLog);
}
