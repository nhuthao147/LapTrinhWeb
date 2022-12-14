package com.edu.laptrinhweb.nhom4.controller;

import com.edu.laptrinhweb.nhom4.dto.ProductDTO;
import com.edu.laptrinhweb.nhom4.dto.UserDTO;
import com.edu.laptrinhweb.nhom4.global.GlobalData;
import com.edu.laptrinhweb.nhom4.model.*;
import com.edu.laptrinhweb.nhom4.service.*;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AdminController {
    public static String uploadDir = System.getProperty("user.dir") + "/media/";
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    BillService billService;
    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }//page admin home

    //Accounts
    @GetMapping("/admin/users")
    public String getAcc(Model model,
                         @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size){
        int count = (int) userService.count();
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage -1, pageSize, Sort.by("id"));
        Page<User> resultPage = null;
        resultPage = userService.findAll(pageable);

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

        model.addAttribute("userPage", resultPage);

//        model.addAttribute("products", productService.getAllProduct());
        //model.addAttribute("roles", roleService.getAllRole());
        return "users";
    }
    @GetMapping("/admin/users/add")
    public String getUserAdd(Model model){
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("roles",roleService.getAllRole());
        return "usersAdd";
    }
    @PostMapping("/admin/users/add")
    public String postUserAdd(@ModelAttribute("userDTO") UserDTO userDTO) {
        //convert dto > entity
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        List<Role> roles = new ArrayList<>();
        for (Integer item: userDTO.getRoleIds()) {
            roles.add(roleService.findRoleById(item).get());
        }
        user.setRoles(roles);

        userService.updateUser(user);
        return "redirect:/admin/users";
    }
    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable int id){
        userService.removeUserById(id);
        return "redirect:/admin/users";
    }//delete 1 user

    @GetMapping("/admin/users/update/{id}")
    public String updateUser(@PathVariable int id, Model model){
        Optional<User> opUser = userService.getUserById(id);
        if (opUser.isPresent()){
            User user = opUser.get();
            //convert entity > dto
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword("");
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            List<Integer> roleIds = new ArrayList<>();
            for (Role item:user.getRoles()) {
                roleIds.add(item.getId());
            }

            model.addAttribute("userDTO", userDTO);
            model.addAttribute("roles", roleService.getAllRole());
            return "usersAdd";
        }else {
            return "404";
        }

    }

    //Categories session
    @GetMapping("/admin/categories")
    public String getCat(Model model,
                         @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size){
        int count = (int) categoryService.count();
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage -1, pageSize, Sort.by("id"));
        Page<Category> resultPage = null;
        resultPage = categoryService.findAll(pageable);

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

        model.addAttribute("categoryPage", resultPage);

//        model.addAttribute("products", productService.getAllProduct());
        //model.addAttribute("roles", roleService.getAllRole());
        return "categories";
    }//view all categories

    @GetMapping("/admin/categories/add")
    public String getCatAdd(Model model){
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }//form add new category

    @PostMapping("/admin/categories/add")
    public String postCatAdd(@ModelAttribute("category") Category category){
        categoryService.updateCategory(category);
        return "redirect:/admin/categories";
    }//form add new category > do add

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCat(@PathVariable int id){
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }//delete 1 category

    @GetMapping("/admin/categories/update/{id}")
    public String updateCat(@PathVariable int id, Model model){
        Optional<Category> category = categoryService.getCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        }else {
            return "404";
        }
    }//form edit category, fill old data into form

    //Products session
    @RequestMapping("/admin/products")
    public String getPro(Model model,
                         @RequestParam(name="name",required = false) String name,
                         @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size){
        int count = (int) productService.count();
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

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
        return "products";
    }//view all products


    @GetMapping("/admin/products/add")
    public String getProAdd(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }// form add new product

    @PostMapping("/admin/products/add")
    public String postProAdd(Model model, @ModelAttribute("productDTO") ProductDTO productDTO,
                             @RequestParam("productImage") MultipartFile fileProductImage) throws IOException {
        //convert dto > entity
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());

        String filename = Paths.get(fileProductImage.getOriginalFilename()).toString();
        int index = filename.lastIndexOf(".");
        String ext = filename.substring(index + 1);
        filename = System.currentTimeMillis() + "." + ext;

        Path fileNameAndPath = Paths.get(uploadDir, filename);
        Files.write(fileNameAndPath, fileProductImage.getBytes());
        product.setImageName(filename);

        productService.updateProduct(product);
        return "redirect:/admin/products";
    }//form add new product > do add

    @GetMapping("/admin/products/delete/{id}")
    public String deletePro(@PathVariable long id){
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }//delete 1 product

    @GetMapping("/admin/products/update/{id}")
    public String updatePro(@PathVariable long id, Model model){
        Optional<Product> opProduct = productService.getProductById(id);
        if (opProduct.isPresent()){
            Product product = opProduct.get();
            //convert entity > dto
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setCategoryId(product.getCategory().getId());
            productDTO.setPrice(product.getPrice());
            productDTO.setWeight(product.getWeight());
            productDTO.setDescription(product.getDescription());
            productDTO.setImageName(product.getImageName());

            model.addAttribute("productDTO", productDTO);
            model.addAttribute("categories", categoryService.getAllCategory());
            return "productsAdd";
        }else {
            return "404";
        }

    }//form edit product, fill old data into form



    //Bill session
    @GetMapping("/admin/bill")
    public String getBill(Model model){
        model.addAttribute("categories", billService.getAllBill());
        return "checkout";
    }//view all categories

    @GetMapping("/bill/add")
    public String getBillAdd(Model model){
        Bill bill = new Bill();
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
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

        bill.setFirstName(currentUser.getFirstName());
        bill.setLastName(currentUser.getLastName());
        bill.setEmail(currentUser.getEmail());

        model.addAttribute("bill", bill);
        return "checkout";
    }//form add new category

    @PostMapping("/bill/add")
    public String postBillAdd(@ModelAttribute("bill") Bill bill){
        bill.setTotal(GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        bill.setUser(userService.getUserByEmail(bill.getEmail()).get());
//        Set<Product> productSet = GlobalData.cart.;
//        List<Product> products = GlobalData.cart;
//        products.forEach(p->bill.setProducts(p));
        billService.saveSill(bill, GlobalData.cart);
        GlobalData.cart = new ArrayList<>();
        return "redirect:/";
    }//form add new category > do add

    @GetMapping("/admin/bill/delete/{id}")
    public String deleteBill(@PathVariable Long id){
        billService.removeBillById(id);
        return "redirect:/admin/bill";
    }//delete 1 category

    @GetMapping("/admin/bill/update/{id}")
    public String updateBill(@PathVariable Long id, Model model){
        Optional<Bill> bill = billService.getBillById(id);
        if(bill.isPresent()){
            model.addAttribute("bill", bill.get());
            return "billsAdd";
        }else {
            return "404";
        }
    }//form edit category, fill old data into form
}
