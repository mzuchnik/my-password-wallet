package pl.mzuchnik.mypasswordwallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.mzuchnik.mypasswordwallet.encoder.AesEncryptor;
import pl.mzuchnik.mypasswordwallet.encoder.HmacPasswordEncoder;
import pl.mzuchnik.mypasswordwallet.entity.Password;
import pl.mzuchnik.mypasswordwallet.entity.User;
import pl.mzuchnik.mypasswordwallet.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class MainController {

    private UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String redirectToMainPage() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String showMainPage() {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage(@ModelAttribute(name = "lockTime") String lockTime, Model model) {
        model.addAttribute("lockTime", lockTime);
        return "login-page";
    }
    @PostMapping("/login-lock")
    public String loginPage(@RequestParam(name = "lockTime") String lockTime, RedirectAttributes redirectAttributes, HttpServletResponse response)
    {
        redirectAttributes.addFlashAttribute("lockTime", lockTime);
        return "redirect:/login";
    }

    @GetMapping("/sign-up")
    public String showSignUpPage(Model model) {
        model.addAttribute("user", new User());
        return "registration-page";
    }

    @PostMapping("/sign-up")
    public String processSignUpForm(@ModelAttribute User user, @RequestParam(name = "encrypt-type") String encryptType) {
        userService.saveUserWithPasswordEncoder(user, encryptType);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        user.getLogin(),
                        user.getPassword()
                )
        );
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String processLogout() {
        SecurityContextHolder.getContext().setAuthentication(null);

        return "redirect:/login";
    }

    @GetMapping("/changePassword")
    public String showChangePasswordView() {
        return "change-password";
    }

    @PostMapping("/changePassword")
    public String processChangePassword(Principal principal,
                                        @RequestParam(name = "oldPassword") String oldPassword,
                                        @RequestParam(name = "password") String password,
                                        @RequestParam(name = "encrypt") String encrypt) {
        User user = userService
                .findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user for login: " + principal.getName()));

        if (user.getPassword().equals("{bcrypt}" + new BCryptPasswordEncoder().encode(oldPassword)) ||
                user.getPassword().equals("{hmac}" + new HmacPasswordEncoder().encode(oldPassword))) {
            return "redirect:/changePassword?q=Wrong+user+password";
        }
        for (Password userPassword : user.getUserPasswords()) {
            userPassword.setWebPassword(new AesEncryptor().decrypt(userPassword.getWebPassword(), user.getPassword()));
        }

        user.setPassword(password);
        userService.saveUserWithPasswordEncoder(user, encrypt);
        return "redirect:/logout";
    }

}
