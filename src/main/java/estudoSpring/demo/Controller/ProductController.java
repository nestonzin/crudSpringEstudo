package estudoSpring.demo.Controller;


import estudoSpring.demo.Model.Product;
import estudoSpring.demo.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
     private final ProductService productService;

    @PreAuthorize("hasRole('user')")
    @GetMapping
    public Page<Product> listAll(Pageable pageable) {
        return (Page<Product>) productService.listAll(pageable);
    }



    @PreAuthorize("hasRole('user')")
    @PostMapping
    public  Product create(@RequestBody Product product){
        return productService.create(product);
    }


    @PreAuthorize("hasRole('user')")
    @PutMapping
    public Product update(@RequestBody Product product){
        return productService.update(product);
    }
    @PreAuthorize("hasRole('user')")
    @DeleteMapping
    public void delete(@RequestParam("id") Long id){
        productService.delete(id);
    }

}
