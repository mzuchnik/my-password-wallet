package pl.mzuchnik.mypasswordwallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mzuchnik.mypasswordwallet.encoder.AesEncryptor;
import pl.mzuchnik.mypasswordwallet.encoder.HmacPasswordEncoder;
import pl.mzuchnik.mypasswordwallet.entity.Password;
import pl.mzuchnik.mypasswordwallet.entity.User;
import pl.mzuchnik.mypasswordwallet.service.PasswordService;
import pl.mzuchnik.mypasswordwallet.service.UserService;

import java.util.List;

@SpringBootApplication
public class MyPasswordWalletApplication {


    public static void main(String[] args) {
        SpringApplication.run(MyPasswordWalletApplication.class, args);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        User admin = createAdmin();
        userService.save(admin);

        Password password = new Password("facebook","facebookLogin","facebookHaslo");
        Password password2 = new Password("facebook","facebookLogin","facebookHaslo123");
        passwordService.saveForUser(password, admin);
        passwordService.saveForUser(password2, admin);


    }

    public User basicUser(String encoding, String number, PasswordEncoder passwordEncoder) {
        return new User("mateusz"+number, "{"+encoding+"}" + passwordEncoder.encode("mateusz123"),
                List.of(new Password("youtube"+number, "youtube_login"+number, "youtube_password"+number),
                        new Password("facebook"+number, "facebook_login"+number, "facebook_password"+number)));
    }

    public User createAdmin(){
        return new User("admin","{bcrypt}" + new BCryptPasswordEncoder().encode("admin"));
    }

}
