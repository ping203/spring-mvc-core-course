package guru.springframework.springmvccourse.controllers;

import guru.springframework.springmvccourse.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/products")
    public String getAll(Model model) {
        model.addAttribute("products", productService.getAll());
        return "products";
    }

}
