package com.edu.laptrinhweb.nhom4.controller;

import com.edu.laptrinhweb.nhom4.dto.UserDTO;
import com.edu.laptrinhweb.nhom4.global.GlobalData;
import com.edu.laptrinhweb.nhom4.model.Product;
import com.edu.laptrinhweb.nhom4.model.Role;
import com.edu.laptrinhweb.nhom4.model.User;
import com.edu.laptrinhweb.nhom4.service.CategoryService;
import com.edu.laptrinhweb.nhom4.service.ProductService;
import com.edu.laptrinhweb.nhom4.service.RoleService;
import com.edu.laptrinhweb.nhom4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class HomeController {
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @GetMapping({"/", "/home"})
    public String home(Model model,
                       @RequestParam(name="name",required = false) String name,
                       @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size){
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProduct());

        int count = (int) productService.count();
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(6);

        Pageable pageable = PageRequest.of(currentPage -1, pageSize, Sort.by("id"));
        Page<Product> resultPage = null;
        if(StringUtils.hasText(name)) {
            resultPage = productService.findByProductNameContaining(name, pageable);
            model.addAttribute("name", name);
        }
        else {
            resultPage = productService.findAll(pageable);
        }

        int totalPages = resultPage.getTotalPages();
        if(totalPages > 0) {
            int start = Math.max(1, currentPage-2);
            int end = Math.min(currentPage+2, totalPages);
            if(totalPages > count) {
                if(end == totalPages) start = end - count;
                else if(start == 1) end = start + count;
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("productPage", resultPage);

//        model.addAttribute("products", productService.getAllProduct());
        return "index";
    } //index
    @GetMapping("/users/add")
    public String updateUser(Model model){
        UserDTO currentUser = new UserDTO();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails && ((UserDetails) principal).getUsername() != null) {
            String currentUsername = ((UserDetails)principal).getUsername();
            User user = userService.getUserByEmail(currentUsername).get();
            currentUser.setId(user.getId());
            currentUser.setEmail(user.getEmail());
            currentUser.setPassword("");
            currentUser.setFirstName(user.getFirstName());
            currentUser.setLastName(user.getLastName());
            List<Integer> roleIds = new ArrayList<>();
            for (Role item:user.getRoles()) {
                roleIds.add(item.getId());
            }
            currentUser.setRoleIds(roleIds);
        }//get current User runtime

        model.addAttribute("userDTO", currentUser);
        return "userRoleAdd";
    }
    @PostMapping("/users/add")
    public String postUserAdd(@ModelAttribute("userDTO") UserDTO userDTO) {
        //convert dto > entity
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        List<Role> roles = userService.getUserById(user.getId()).get().getRoles();
        user.setRoles(roles);

        userService.updateUser(user);
        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProduct());
        return "shop";
    } //view All Products

    @GetMapping("/shop/category/{id}")
    public String shopByCat(@PathVariable int id, Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProductByCategoryId(id));
        return "shop";
    } //view Products By Category

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(@PathVariable long id, Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("product", productService.getProductById(id).get());
        model.addAttribute("products", productService.getRandomListProducts(6));
        return "viewProduct";
    } //view product Details


}
