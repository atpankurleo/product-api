package redsky.Resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import redsky.domain.ProductPrice;
import redsky.domain.Products;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductsResource extends ResourceSupport{
    @JsonProperty(value = "id")
    private Long pid;
    private String name;
    @JsonProperty(value = "current_price")
    private ProductPrice productPrice;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }

    public Products toProducts() {
        Products products = new Products();
        products.setId(pid);
        products.setName(name);
        products.setProductPrice(productPrice);
        return products;
    }
}
