package vn.edu.leading.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.leading.shop.models.CategoryModel;
import vn.edu.leading.shop.services.CategoryService;
import vn.edu.leading.shop.services.ProductService;
import vn.edu.leading.shop.services.SupplierService;

import java.util.Optional;

@Controller
public class HomeController {

    public final CategoryService categoryService;

    private final ProductService productService;

    private final SupplierService supplierService;

    public HomeController(CategoryService categoryService, ProductService productService, SupplierService supplierService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.supplierService = supplierService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("suppliers", supplierService.findAll());
        return "admin/home/shop";
    }

//    @GetMapping("/shop/{id}/show")
//    public String showProduct(@PathVariable Long id, RedirectAttributes redirect, Model model) {
//       Optional<CategoryModel> categoryModel =categoryService.findById(id);
//          model.addAttribute("products",categoryModel.get().getProducts());
//          return  "admin/home/shop";
//    }

    @GetMapping("/productdetail/{id}")
    public String product(@PathVariable("id") Long id,Model model) {
        model.addAttribute("productModel", productService.findById(id));

        return "admin/home/product-details";
    }

    @GetMapping("/add-to-cart")
    public String addToCart() {
        return "admin/home/cart";
    }
}
