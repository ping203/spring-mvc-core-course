package guru.springframework.springmvccourse.controllers;

import guru.springframework.springmvccourse.domain.Customer;
import guru.springframework.springmvccourse.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yriyMitsiuk on 30.05.2018.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping({"/customers", "/"})
    public String getAll(Model model) {
        model.addAttribute("customers", service.getAll());
        return "customer/customers";
    }

    @GetMapping("/view/{id}")
    public String get(@PathVariable Integer id, Model model) {
        model.addAttribute("customer", service.get(id));
        return "customer/view";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/customerform";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("customer", service.get(id));
        return "customer/customerform";
    }

    @PostMapping
    public String saveOrUpdate(Customer customer) {
        Customer saveOrUpdateCustomer = service.saveOrUpdate(customer);
        return "redirect:/customer/view/"+saveOrUpdateCustomer.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/customer/customers";
    }
}
