package estudoSpring.demo.product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ProductService {
    Page<Product> listAll(Pageable pageable);
    Product create(Product product);
    Product update(Product product);

    void  delete(Long id);
}
