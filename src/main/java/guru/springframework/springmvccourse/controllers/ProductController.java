package guru.springframework.springmvccourse.controllers;

import guru.springframework.springmvccourse.domain.Product;
import guru.springframework.springmvccourse.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by yriyMitsiuk on 30.05.2018.
 */
@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getAll(Model model) {
        model.addAttribute("products", productService.getAll());
        return "products";
    }

    @GetMapping("/product/{id}")
    public String get(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.get(id));
        return "product";
    }

    @GetMapping("/product/new")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        return "productform";
    }

    @GetMapping("/product/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.get(id));
        return "productform";
    }

    @GetMapping("/product/delete/{id}")
    public String delete(@PathVariable Integer id) {
        productService.delete(id);
        return "redirect:/products";
    }

    @PostMapping("/product")
    public String saveOrUpdate(Product product) {
        Product saveOrUpdateProduct = productService.saveOrUpdate(product);
        return "redirect:/product/"+saveOrUpdateProduct.getId();
    }
}
