package guru.springframework.springmvccourse.controllers;

import guru.springframework.springmvccourse.domain.Product;
import guru.springframework.springmvccourse.services.ProductService;
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
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"/products", "/"})
    public String getAll(Model model) {
        model.addAttribute("products", productService.getAll());
        return "product/products";
    }

    @GetMapping("/show/{id}")
    public String get(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.get(id));
        return "product/show";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        return "product/productform";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.get(id));
        return "product/productform";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        productService.delete(id);
        return "redirect:/product/products";
    }

    @PostMapping
    public String saveOrUpdate(Product product) {
        Product saveOrUpdateProduct = productService.saveOrUpdate(product);
        return "redirect:/product/show/"+saveOrUpdateProduct.getId();
    }
}
