package guru.springframework.springmvccourse.domain;

import javax.persistence.*;

@Entity
public class CartDetail extends AbstractDomainObject {

    @Version
    private Integer version;

    @OneToOne
    private Product product;

    @ManyToOne
    private Cart cart;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
