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
import vn.edu.leading.shop.models.EmployeeModel;
import vn.edu.leading.shop.services.EmployeeService;

import javax.validation.Valid;

    @Controller
    public class EmployeeController {

        private final EmployeeService employeeService;

        public EmployeeController(EmployeeService employeeService) {
            this.employeeService = employeeService;
        }

        @GetMapping("/admin/employees")
        public String employees(Model model) {
            model.addAttribute("employees", employeeService.findAll());
            return "admin/pages/employees";
        }

        @PostMapping("admin/employees")
        public String save(@Valid EmployeeModel employee, Model model) {
            employeeService.save(employee);
            model.addAttribute("employees", employeeService.findAll());
            return "admin/pages/employees";
        }

    @GetMapping("/employees/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect,Model model) {
        if (employeeService.delete(id)) {
            redirect.addFlashAttribute("successMessage", "Deleted employee successfully!");
            model.addAttribute("employees", employeeService.findAll());

            return "admin/pages/employees";
        } else {
            redirect.addFlashAttribute("successMessage", "Not found!!!");
            model.addAttribute("employees", employeeService.findAll());

            return "admin/pages/employees";
        }
    }
}
