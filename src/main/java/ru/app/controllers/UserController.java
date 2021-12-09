package ru.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.app.components.UserValidator;
import ru.app.models.Role;
import ru.app.models.User;
import ru.app.services.RoleService;
import ru.app.services.SecurityService;
import ru.app.services.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        var usr = new User();
        usr.setRoles(Set.of(roleService.getUserRole().get()));
        model.addAttribute("userForm", usr);

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            var usr = new User();
            usr.setRoles(Set.of(roleService.getUserRole().get()));
            model.addAttribute("userForm", usr);
            return "registration";
        }
        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        if (error != null)
            model.addAttribute("error", "Ваш логин и/или пароль не верны.");

        if (logout != null)
            model.addAttribute("message", "Вы успешно вышли из аккаунта.");

        return "login";
    }

    @GetMapping("/me")
    public String getUser(Model model) {
        var user = userService.getUser();
        model.addAttribute("userRole",
                user.getRoles().stream().map(Role::getName).collect(Collectors.joining(", ")));
        return "user";
    }
}
