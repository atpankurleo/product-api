package redsky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import redsky.AppProperties;
import redsky.Resource.ProductsResource;
import redsky.ResourceAssembler.ProductsResourceAssembler;
import redsky.domain.ProductDetail;
import redsky.domain.ProductPrice;
import redsky.domain.Products;
import redsky.repository.ProductPriceRepository;

import java.net.URI;
import java.util.Optional;


@RestController
public class ProductsController {

    @Autowired
    ProductPriceRepository productPriceRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AppProperties appProperties;

    @RequestMapping(method = RequestMethod.GET, value = "/products/{id}")
    public ResponseEntity<ProductsResource> getProductsById(@PathVariable String id) {
        ProductPrice productPrice = productPriceRepository.findOne(id);
        Optional<ProductDetail> optional = Optional.ofNullable(restTemplate.getForObject(appProperties.getProductRedskyUrl(), ProductDetail.class));
        if(optional.isPresent() && optional.get().getProduct() != null) {
            Products product = new Products();
            product.setId(Long.parseLong(id));
            product.setName(optional.get().getProduct().getItem().getProductDescription().getTitle());
            if(productPrice != null) {
                product.setProductPrice(productPrice);
            }
            ProductsResource productsResource = new ProductsResourceAssembler().toResource(product);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(productsResource.getLink("self").getHref()));

            return new ResponseEntity<>(productsResource, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/products/{id}")
    public ResponseEntity<ProductsResource> updateProductPrice(@PathVariable String id, @RequestBody Products products) {
        Optional<ProductPrice> optional = Optional.ofNullable(productPriceRepository.findOne(id));
        if(optional.isPresent()) {
            ProductPrice latestProductPrice = products.getProductPrice();
            latestProductPrice.setId(id);
            ProductPrice updatedProductPrice = productPriceRepository.save(latestProductPrice);
            if(updatedProductPrice != null) {
                ResponseEntity<ProductsResource> productsById = getProductsById(id);
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(URI.create(productsById.getBody().getLink("self").getHref()));
                return new ResponseEntity<>(productsById.getBody(), headers, HttpStatus.OK);
            }
            /*Map<String, String> map = new HashMap<String, String>();
            map.put("id", id);
            restTemplate.put("http://localhost:8090/productPrice/{id}", productPrice, map);*/
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
