package vn.edu.leading.shop.controllers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.edu.leading.shop.models.OrderDetailModel;
import vn.edu.leading.shop.models.ShipperModel;
import vn.edu.leading.shop.models.UserModel;
import vn.edu.leading.shop.securities.CurrentUser;
import vn.edu.leading.shop.services.CategoryService;
import vn.edu.leading.shop.services.ProductService;
import vn.edu.leading.shop.services.ShipperService;
import vn.edu.leading.shop.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

    public final CategoryService categoryService;

    private final ProductService productService;

    private final UserService userService;

    private final ShipperService shipperService;

    public HomeController(CategoryService categoryService, ProductService productService, UserService userService, ShipperService shipperService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
        this.shipperService = shipperService;
    }

    @GetMapping("/")
    public String home(@CurrentUser AuthenticationManager authentication, Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        UserModel userModel = userService.findByUsername(authentication.getClass().getName()).orElse(new UserModel());
        model.addAttribute("userModel", userModel);
        return "admin/home/shop";
    }

    @GetMapping("/product-detail/{id}/{str}")
    public String product(@PathVariable("id") Long id, Model model, @PathVariable String str) {
        model.addAttribute("productModel", productService.findById(id));
        model.addAttribute("metaname", str);

        return "admin/home/product-details";
    }

    @GetMapping("/add-to-cart")
    public String addToCart(@CurrentUser AuthenticationManager authentication, Model model) {
        List<ShipperModel> shipperModelList = shipperService.findAll();
        UserModel userModel = userService.findByUsername(authentication.getClass().getName()).orElse(new UserModel());
        model.addAttribute("userModel", userModel);
        model.addAttribute("shippers", shipperModelList);
        Set<OrderDetailModel> orderDetails = new HashSet<>();
        return "admin/home/cart";
    }

    @GetMapping("/checkout")
    public String checkout() {

        return "admin/home/checkout";
    }
}
