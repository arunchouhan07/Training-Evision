package com.ecom.controller.admin;

import com.ecom.entity.Category;
import com.ecom.entity.UserDtls;
import com.ecom.service.CategoryService;
import com.ecom.service.ImageService;
import com.ecom.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @GetMapping()
    public String index(){
        return "admin/index";
    }

    @GetMapping("/index")
    public String getIndex() {
        return "admin/index";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        String uploadImageUrl = imageService.upload(file);
        Boolean existCategory = categoryService.existCategory(category.getName());
        if(existCategory)
        {
            session.setAttribute("errorMessage", "Category Name Already Exists");
            return "/admin/errorCategory";
        }else{
            category.setImageUrl(uploadImageUrl);
            int saveCategory = categoryService.saveCategory(category);
            if(saveCategory!=1){
                session.setAttribute("errorMessage","Not Saved ! internal Server Error");
                return "/admin/errorCategory";
            }
        }
        session.setAttribute("Success","Saved successfully");
        return "admin/successCategory";
    }

    @GetMapping("/category")
    public String category(Model m, HttpSession session) {
        m.addAttribute("categorys", categoryService.getAllCategory());
        m.addAttribute("succMsg", session.getAttribute("succMsg"));
        m.addAttribute("errorMsg", session.getAttribute("errorMsg"));
        session.removeAttribute("succMsg");
        session.removeAttribute("errorMsg");
        return "admin/category";
    }

    @GetMapping("/loadEditCategory/{id}")
    public String editCategory(@PathVariable int id,Model m){
        Category category=categoryService.getCategoryById(id);
        m.addAttribute("category", category);
        return "admin/edit_category";
    }

    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile multipartFile, HttpSession session) throws IOException {
        Category c = categoryService.getCategoryById(category.getId());
        String uploadImageUrl = imageService.upload(multipartFile);

            c.setName(category.getName());
            c.setIsActive(category.getIsActive());
            c.setImageUrl(uploadImageUrl);

        int updateCategory=categoryService.updateCategory(c);

        if(!ObjectUtils.isEmpty(updateCategory)){
            session.setAttribute("SuccessMessage","Category update successfully");
        }else{
            session.setAttribute("ErrorMessage","Something went wrong");
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable int id, HttpSession session){
        Boolean deleteCategory = categoryService.deleteCategory(id);
        if(deleteCategory){
            session.setAttribute("succMsg","Category delete success");
        }else{
            session.setAttribute("errorMsg","Something went wrong");
        }
        return "redirect:/admin/category";
    }

    @ModelAttribute
    public void getUserDetails(Principal principal, Model model){
        if(principal != null){
            String email = principal.getName();
            UserDtls userByEmail = userService.getUserByEmail(email);
            model.addAttribute("user", userByEmail);
        }
        List<Category> allActiveCategory = categoryService.getAllActiveCategory();
        model.addAttribute("categories",allActiveCategory);
    }
}
