package estudoSpring.demo.Services;
import estudoSpring.demo.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<Product> listAll(Pageable pageable);
    Product create(Product product);
    Product update(Product product);

    void  delete(Long id);
}
