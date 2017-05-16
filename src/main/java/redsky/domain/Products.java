package redsky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.ResourceSupport;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Products extends ResourceSupport{
    @JsonProperty(value = "id")
    private Long pid;
    private String name;
    @JsonProperty(value = "current_price")
    private ProductPrice productPrice;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long id) {
        this.pid = id;
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
}
