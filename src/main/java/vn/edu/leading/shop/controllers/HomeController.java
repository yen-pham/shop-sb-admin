package vn.edu.leading.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.edu.leading.shop.configs.IAuthenticationFacade;
import vn.edu.leading.shop.models.UserModel;
import vn.edu.leading.shop.services.CategoryService;
import vn.edu.leading.shop.services.ProductService;
import vn.edu.leading.shop.services.UserService;

@Controller
public class HomeController {

    public final CategoryService categoryService;

    private final ProductService productService;
    private final UserService userService;

    private final IAuthenticationFacade authentication;

    public HomeController(CategoryService categoryService, ProductService productService, UserService userService, IAuthenticationFacade authentication) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
        this.authentication = authentication;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        UserModel userModel = userService.findByUsername(authentication.getAuthentication().getName()).orElse(new UserModel());
        model.addAttribute("userModel", userModel);
        return "admin/home/shop";
    }

//    @GetMapping("/shop/{id}/show")
//    public String showProduct(@PathVariable Long id, RedirectAttributes redirect, Model model) {
//       Optional<CategoryModel> categoryModel =categoryService.findById(id);
//          model.addAttribute("products",categoryModel.get().getProducts());
//          return  "admin/home/shop";
//    }

    @GetMapping("/product-detail/{id}/{str}")
    public String product(@PathVariable("id") Long id, Model model, @PathVariable String str) {
        model.addAttribute("productModel", productService.findById(id));
        model.addAttribute("metaname",str);

        return "admin/home/product-details";
    }

    @GetMapping("/add-to-cart")
    public String addToCart() {
        return "admin/home/cart";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "admin/home/checkout";
    }
}
