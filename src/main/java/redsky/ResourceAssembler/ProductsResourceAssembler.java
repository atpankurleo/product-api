package redsky.ResourceAssembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import redsky.Resource.ProductsResource;
import redsky.controller.ProductsController;
import redsky.domain.ProductPrice;
import redsky.domain.Products;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ProductsResourceAssembler extends ResourceAssemblerSupport<Products, ProductsResource> {
    public ProductsResourceAssembler() {
        super(ProductsController.class, ProductsResource.class);
    }

    @Override
    public ProductsResource toResource(Products products) {
        ProductsResource res = new ProductsResource();
        res.setPid(products.getId());
        res.setName(products.getName());
        if(products.getProductPrice() != null) {
            ProductPrice productPrice = new ProductPrice();
            productPrice.setValue(products.getProductPrice().getValue());
            productPrice.setCurrencyCode(products.getProductPrice().getCurrencyCode());
            res.setProductPrice(productPrice);
        }

        res.add(linkTo(methodOn(ProductsController.class).getProductsById(String.valueOf(products.getId()))).withSelfRel());
        return res;
    }
}
