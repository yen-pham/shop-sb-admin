package vn.edu.leading.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.leading.shop.models.CategoryModel;
import vn.edu.leading.shop.services.CategoryService;

import javax.validation.Valid;

@Controller
public class CategoryController  {

   public final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String list(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories/list";
    }

    @GetMapping("/admin/categories")
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/pages/categories";
    }

    @PostMapping("admin/categories")
    public String save(@Valid CategoryModel category, Model model) {
        categoryService.save(category);
        model.addAttribute("categories", categoryService.findAll());
        return "admin/pages/categories";
    }

    @GetMapping("categories/search")
    public String search(@RequestParam("term") String term, Model model) {
        if (StringUtils.isEmpty(term)) {
            return "redirect:/categories";
        }
        model.addAttribute("categories", categoryService.search(term));
        return "categories/list";
    }

    @GetMapping("/categories/add")
    public String add(Model model) {
        model.addAttribute("categoryModel", new CategoryModel());
        return "categories/form";
    }

    @GetMapping("/categories/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("categoryModel", categoryService.findById(id));
        return "categories/form";
    }

    @PostMapping("/categories/save")
    public String save(@Valid CategoryModel category, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "categories/form";
        }
        categoryService.save(category);
        redirect.addFlashAttribute("successMessage", "Saved category successfully!");
        return "redirect:/categories";
    }

    @GetMapping("/admin/categories/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect,Model model) {
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
}
