package com.ecom.controller.admin;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.model.UserDtls;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;
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
public class ProductController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

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
        String imageName=(!multipartFile.isEmpty())? multipartFile.getOriginalFilename() : "default.jpg";
        product.setImage(imageName);
        product.setDiscount(0);
        product.setDiscountPrice(product.getDiscountPrice());
        Product saveProduct = productService.saveProduct(product);


        if (!ObjectUtils.isEmpty(saveProduct)) {

            File saveFile = new ClassPathResource("static/img").getFile();

            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
                    + multipartFile.getOriginalFilename());

            // System.out.println(path);
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            session.setAttribute("succMsg", "Product Saved Success");
        } else {
            session.setAttribute("errorMsg", "something wrong on server");
        }

        return "/admin/success";
    }

    @GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable int id, Model m) {
        Product product = productService.getProductById(id);
        m.addAttribute("product", product);
        return "admin/edit_Product";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product,@RequestParam("file")MultipartFile multipartFile, HttpSession session) {
        if (product.getDiscount() < 0 || product.getDiscount() > 100) {
            session.setAttribute("errorMsg", "invalid Discount");
        }else{
            Product updateProduct = productService.updateProduct(product, multipartFile);
            if (!ObjectUtils.isEmpty(updateProduct)) {
                session.setAttribute("succMsg", "Product update success");
            } else {
                session.setAttribute("errorMsg", "Something wrong on server");
                return "redirect:/admin/errorr";
            }
        }
        return "admin/success";
       // return "redirect:/admin/editProduct/" + product.getId();
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
