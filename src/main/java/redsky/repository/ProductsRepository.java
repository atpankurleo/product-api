
package redsky.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import redsky.domain.Products;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductsRepository extends MongoRepository<Products, String> {
}
