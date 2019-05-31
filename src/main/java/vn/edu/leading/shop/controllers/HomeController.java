package vn.edu.leading.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.leading.shop.services.CategoryService;
import vn.edu.leading.shop.services.ProductService;
import vn.edu.leading.shop.services.SupplierService;

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

    @GetMapping("/shop/{id}/show")
    public String showProduct(@PathVariable Long id, RedirectAttributes redirect, Model model) {
        if (categoryService.delete(id)) {
            redirect.addFlashAttribute("successMessage", "Deleted category successfully!");
            model.addAttribute("categories", categoryService.findAll());
            return "admin/pages/categories";
        } else {
            redirect.addFlashAttribute("successMessage", "Not found!!!");
            model.addAttribute("categories", categoryService.findAll());
            return "admin/pages/categories";
        }
    }

//    @GetMapping("/shop")
//    public String shop() {
//        return "admin/home/shop";
//    }



}
