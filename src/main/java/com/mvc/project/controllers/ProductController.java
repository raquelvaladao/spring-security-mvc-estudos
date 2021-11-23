package com.mvc.project.controllers;


import com.mvc.project.models.Product;
import com.mvc.project.models.Usuario;
import com.mvc.project.services.ProductService;
import com.mvc.project.services.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UsuarioDetailsService usuarioService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listProducts(@RequestParam(name = "success", required = false) String successLogin, HttpServletRequest httpServletRequest){
        ModelAndView mv = new ModelAndView("/allProducts.html");
        List<Product> list = productService.getAllProducts();

        if(successLogin.equals("true")){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            //autenticou
            //pega login, pega authorities, e manda email.
            Usuario verSeScrum = usuarioService.buscaUsuario(currentPrincipalName).get();
            if(verSeScrum.getRole().getName().equals("ROLE_USER")){
                //scrum!
                //send email....
            }
        }

            mv.addObject("list", list);
        return mv;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/new")
    public ModelAndView createProduct() {
        ModelAndView mv = new ModelAndView("/form.html");
        Product product = new Product();

        mv.addObject("product", product);
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, path = "new")
    public ModelAndView saveProduct(@Valid Product product, BindingResult errors, RedirectAttributes redirect) {
        ModelAndView redirectView = new ModelAndView("redirect:/products");
        ModelAndView mv = new ModelAndView("/form.html");

        if(errors.hasErrors()){
            mv.addObject("product", product);
            return mv;
        }
        mv.addObject("product", new Product());
        //redirect.addFlashAttribute("message", "Produto criado!");
        productService.saveProduct(product);
        return redirectView;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/edit")
    public ModelAndView editProduct(@RequestParam(name = "id") Long id, RedirectAttributes redirect) throws Exception {
        ModelAndView mv = new ModelAndView("/form.html");
        ModelAndView notFoundPage = new ModelAndView("/errors/notFound.html");
        Product product;

        try{
            product = productService.getSingleProduct(id);
        } catch(Exception e){
            product = new Product();
            //add msg em Form?
            redirect.addFlashAttribute("message", e.getMessage());
            return notFoundPage;
        }

        mv.addObject("product", product);
        return mv;
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.DELETE}, path = "/delete")
    public ModelAndView deleteProduct(@RequestParam(name = "id") Long id, RedirectAttributes redirect) throws Exception {
        ModelAndView notFoundPage = new ModelAndView("/errors/notFound.html");
        ModelAndView mv = new ModelAndView("redirect:/products");

        Product product;

        try{
            product = productService.getSingleProduct(id);
        } catch(Exception e){
            product = new Product();
            //add msg em Form?
            redirect.addFlashAttribute("message", e.getMessage());
            return notFoundPage;
        }

        productService.deleteProduct(id);
        return mv;
    }

}
