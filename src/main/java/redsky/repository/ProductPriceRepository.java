
package redsky.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import redsky.domain.ProductPrice;

@RepositoryRestResource(collectionResourceRel = "productPrice", path = "productPrice")
public interface ProductPriceRepository extends MongoRepository<ProductPrice, String> {
}
