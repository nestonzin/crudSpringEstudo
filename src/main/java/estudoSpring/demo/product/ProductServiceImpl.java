package estudoSpring.demo.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl  implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public Page<Product> listAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }


    @Override
    public Product create(Product product) {
        if(product.getId() != null){
            throw new RuntimeException("Produto com id ja cadastrado!");
        }
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        if(product.getId() == null){
            throw new RuntimeException("Voce precisa de um id pra atualizar esse registro");
        }
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
    productRepository.deleteById(id);
    }
}
