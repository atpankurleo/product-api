package redsky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import redsky.AppProperties;
import redsky.domain.ProductDetail;
import redsky.domain.ProductPrice;
import redsky.domain.Products;
import redsky.exception.MyRetailException;
import redsky.repository.ProductPriceRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static redsky.exception.MyRetailException.ErrorCode.PRODUCT_NOT_FOUND;


@RestController
public class ProductsController {

    @Autowired
    ProductPriceRepository productPriceRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AppProperties appProperties;

    @RequestMapping(method = RequestMethod.GET, value = "/products/{id}")
    public @ResponseBody Products getProductsById(@PathVariable String id) {
        ProductPrice productPrice = productPriceRepository.findOne(id);
        Optional<ProductDetail> optional = Optional.ofNullable(restTemplate.getForObject(appProperties.getProductRedskyUrl(), ProductDetail.class));
        if(optional.isPresent() && optional.get().getProduct() != null) {
            Products product = new Products();
            product.setId(Long.parseLong(id));
            product.setName(optional.get().getProduct().getItem().getProductDescription().getTitle());
            if(productPrice != null) {
                productPrice.setId(null);
                product.setProductPrice(productPrice);
            }
            return product;
        }
        Map<String,String> errorParams = new HashMap<>();
        errorParams.put("Product ID", id);
        throw new MyRetailException(PRODUCT_NOT_FOUND, errorParams);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/products/{id}")
    public @ResponseBody Products updateProductPrice(@PathVariable String id, @RequestBody Products products) {
        Optional<ProductPrice> optional = Optional.ofNullable(productPriceRepository.findOne(id));
        if(optional.isPresent()) {
            ProductPrice latestProductPrice = products.getProductPrice();
            latestProductPrice.setId(id);
            ProductPrice updatedProductPrice = productPriceRepository.save(latestProductPrice);
            if(updatedProductPrice != null) {
                return getProductsById(id);
            }
            /*Map<String, String> map = new HashMap<String, String>();
            map.put("id", id);
            restTemplate.put("http://localhost:8090/productPrice/{id}", productPrice, map);*/
        }
        return null;
    }
}
