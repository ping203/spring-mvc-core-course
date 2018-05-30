package guru.springframework.springmvccourse.domain;

import java.math.BigDecimal;

/**
 * Created by yriyMitsiuk on 30.05.2018.
 */
public class Product extends AbstractDomainObject {
    private String description;
    private BigDecimal price;
    private String imageUrl;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
