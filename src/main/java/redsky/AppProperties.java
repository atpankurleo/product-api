package redsky;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource(value = "classpath:/default.properties")
@ConfigurationProperties
@Component
public class AppProperties {
    private String productRedskyUrl;

    public String getProductRedskyUrl() {
        return productRedskyUrl;
    }

    public void setProductRedskyUrl(String productRedskyUrl) {
        this.productRedskyUrl = productRedskyUrl;
    }
}
