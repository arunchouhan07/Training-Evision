package com.ecom.controller.admin;

import com.ecom.entity.Category;
import com.ecom.entity.Product;
import com.ecom.entity.UserDtls;
import com.ecom.service.CategoryService;
import com.ecom.service.ImageService;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/products")
    public String product(Model m, HttpSession session) {
            m.addAttribute("products", productService.getAllProducts());
            m.addAttribute("succMsg", session.getAttribute("succMsg"));
            m.addAttribute("errorMsg", session.getAttribute("errorMsg"));
            session.removeAttribute("succMsg");
            session.removeAttribute("errorMsg");
            return "admin/products";
    }

    @GetMapping("/loadAddProduct")
    public String loadAddProduct(Model m) {
        List<Category> categories = categoryService.getAllCategory();
        m.addAttribute("categories", categories);
        return "admin/add_product";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute Product product, HttpSession session, @RequestParam("file")MultipartFile multipartFile) throws IOException {
        String uploadImageUrl = imageService.upload(multipartFile);
        product.setImage(uploadImageUrl);
        product.setDiscount(0);
        product.setDiscountPrice(product.getPrice());
        Product saveProduct = productService.saveProduct(product);

        if (!ObjectUtils.isEmpty(saveProduct)) {
            session.setAttribute("succMsg", "Product Saved Success");
        } else {
            session.setAttribute("errorMsg", "something wrong on server");
            return "admin/errorProduct";
        }

        return "/admin/success";
    }

    @GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable int id, Model m) {
        Product product = productService.getProductById(id);
        m.addAttribute("product", product);
        return "admin/edit_product";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product, @RequestParam("file")MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
        if (product.getDiscount() < 0 || product.getDiscount() > 100) {
            return "admin/errorProduct";
        }else{
            Product updateProduct = productService.updateProduct(product, multipartFile);
            if (!ObjectUtils.isEmpty(updateProduct)) {
                redirectAttributes.addFlashAttribute("succMsg", "Product Updated Successfully");
            } else {
                return "admin/errorProduct";
            }
        }
        return "redirect:/admin/formSuccess";
    }

    @GetMapping("/formSuccess")
    public String showSuccessPage() {
        return "admin/success";
    }


    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable int id, HttpSession session) {
        Boolean deleteProduct = productService.deleteProduct(id);
        if(deleteProduct){
            session.setAttribute("succcMsg", "Product Deleted Success");
        }else{
            session.setAttribute("errorMsg", "Something wrong on server");
        }
        return "/admin/products";
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
