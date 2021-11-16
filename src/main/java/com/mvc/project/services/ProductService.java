package com.mvc.project.services;

import com.mvc.project.models.Product;
import com.mvc.project.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    
    public Product getSingleProduct(Long id) throws Exception {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new Exception("Não é possível achar o produto");
        }
        return product.get();
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
