package guru.springframework.springmvccourse.controllers;

import guru.springframework.springmvccourse.domain.User;
import guru.springframework.springmvccourse.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yriyMitsiuk on 05.06.2018.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping({"/users", "/"})
    public String getAll(Model model) {
        model.addAttribute("users", service.getAll());
        return "user/list";
    }

    @GetMapping("/view/{id}")
    public String get(@PathVariable Integer id, Model model) {
        model.addAttribute("user", service.get(id));
        return "user/view";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("user", new User());
        return "user/userform";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("user", service.get(id));
        return "user/userform";
    }

    @PostMapping
    public String saveOrUpdate(User user) {
        User saveOrUpdateUser = service.saveOrUpdate(user);
        return "redirect:/user/view/"+saveOrUpdateUser.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/user/users";
    }
}
