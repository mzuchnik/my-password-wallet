package pl.mzuchnik.mypasswordwallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.mzuchnik.mypasswordwallet.encoder.AesEncryptor;
import pl.mzuchnik.mypasswordwallet.entity.*;
import pl.mzuchnik.mypasswordwallet.form.SharedPasswordForm;
import pl.mzuchnik.mypasswordwallet.service.PasswordService;
import pl.mzuchnik.mypasswordwallet.service.SharedPasswordService;
import pl.mzuchnik.mypasswordwallet.service.UserLogService;
import pl.mzuchnik.mypasswordwallet.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    private UserService userService;
    private PasswordService passwordService;
    private UserLogService userLogService;
    private PasswordEncoder passwordEncoder;
    private SharedPasswordService sharedPasswordService;

    @Autowired
    public WalletController(UserService userService, PasswordService passwordService, UserLogService userLogService, PasswordEncoder passwordEncoder, SharedPasswordService sharedPasswordService) {
        this.userService = userService;
        this.passwordService = passwordService;
        this.userLogService = userLogService;
        this.passwordEncoder = passwordEncoder;
        this.sharedPasswordService = sharedPasswordService;
    }

    @GetMapping
    public String showUserWallet(Model model,
                                 Principal principal,
                                 @RequestParam(required = false) String encryptor) {
        User user = userService.findByLogin(principal.getName()).get();
        List<UserLog> userLogs = userLogService.findAllByUser(user);
        if (encryptor != null) {
            if (encryptor.equals("decrypt")) {
                user.getUserPasswords().forEach(
                        password -> {
                            password.setWebPassword(
                                    AesEncryptor.decrypt(
                                            password.getWebPassword(), user.getPassword()));
                        });
            }

        } else {
            user.getUserPasswords().forEach(p -> {
                p.setWebPassword("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            });
        }


        model.addAttribute("userPasswords", user.getUserPasswords());
        model.addAttribute("passwordForm", new Password());
        model.addAttribute("userLogs", userLogs);
        List<SharedPassword> byOwner = sharedPasswordService.findByOwner(user);
        byOwner.forEach(item -> {item.setPassword(AesEncryptor.decrypt(item.getPassword(),item.getOwner().getPassword()));});
        model.addAttribute("userSharedPasswords", byOwner);
        List<SharedPassword> byConsumer = sharedPasswordService.findByConsumer(user);
        byConsumer.forEach(item -> {item.setPassword(AesEncryptor.decrypt(item.getPassword(),item.getOwner().getPassword()));});
        model.addAttribute("sharedPasswordsForUser", byConsumer);

        if (!model.containsAttribute("sharedPasswordForm")) {
            model.addAttribute("sharedPasswordForm", new SharedPasswordForm());
        }
        if (!model.containsAttribute("passwordEditForm")){
            model.addAttribute("passwordEditForm", new Password());
        }

        return "my-wallet";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, RedirectAttributes redirectAttributes, Authentication authentication){
        Password passwordById = passwordService.findById(id);
        User user = userService.findByLogin(authentication.getName()).get();
        if(passwordById != null){
            passwordById.setWebPassword(AesEncryptor.decrypt(passwordById.getWebPassword(),user.getPassword()));
            redirectAttributes.addFlashAttribute("passwordEditForm", passwordById);
            return "redirect:/wallet?edit-password";
        }
        return "redirect:/wallet";
    }

    @PostMapping("/edit/process")
    public String processEditedPasswordFromWallet(@ModelAttribute Password passwordEditForm, Authentication authentication, HttpServletRequest request){
        User user = userService.findByLogin(authentication.getName()).get();
        UserLog userLog = new UserLog(Timestamp.from(Instant.now()),"Change Password", request.getRemoteAddr());
        userLog.setUser(user);
        userLog.setPasswordHistory(new PasswordHistory(passwordService.findById(passwordEditForm.getId())));
        userLogService.save(userLog);
        passwordService.saveForUser(passwordEditForm, user);
        return "redirect:/wallet";
    }


    @PostMapping("/add")
    public String showFormToAddPassword(@ModelAttribute Password password, Principal principal) {
        passwordService.saveForUser(password, userService.findByLogin(principal.getName()).get());
        return "redirect:/wallet";
    }

    @PostMapping("delete/{id}")
    public String processDeletePasswordElement(@PathVariable long id){
        sharedPasswordService.deleteById(id);
        return "redirect:/wallet";
    }

    @PostMapping("/share-password")
    public String processSharePassword(@ModelAttribute("sharedPasswordForm") @Valid SharedPasswordForm sharedPasswordForm, BindingResult result, Authentication authentication, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.sharedPasswordForm", result);
            redirectAttributes.addFlashAttribute("sharedPasswordForm", sharedPasswordForm);
            return "redirect:/wallet?error-share-password";
        }

        Optional<User> byLogin = userService.findByLogin(authentication.getName());
        User owner = null;
        if (byLogin.isPresent()) {
            owner = byLogin.get();
        }

        User consumer = userService.findByLogin(sharedPasswordForm.getConsumerLogin()).get();

        Password passwordFromWallet = passwordService.findById(sharedPasswordForm.getPasswordId());

        SharedPassword sharedPassword = new SharedPassword(owner, consumer, false, passwordFromWallet.getWebAddress(), passwordFromWallet.getWebDescription(), passwordFromWallet.getWebLogin(), passwordFromWallet.getWebPassword(), "Empty note");
        sharedPasswordService.save(sharedPassword);

        return "redirect:/wallet";
    }

    @PostMapping("/rollback/{id}")
    public String processRollback(@PathVariable Long id, Authentication authentication){
        User user = userService.findByLogin(authentication.getName()).get();
        UserLog userLog = userLogService.findById(id).get();
        PasswordHistory ph = userLog.getPasswordHistory();
        Password password = new Password(ph.getWebAddress(),ph.getWebLogin(),ph.getWebPassword(),ph.getWebDescription());
        password.setId(ph.getPasswordId());
        password.setUser(user);
        passwordService.save(password);
        return "redirect:/wallet";
    }

}
