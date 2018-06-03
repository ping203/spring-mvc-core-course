package guru.springframework.springmvccourse.domain;

import javax.persistence.*;

@Entity
public class CartDetail extends AbstractDomainObject {

    @OneToOne
    private Product product;

    @ManyToOne
    private Cart cart;

    private Integer quantity;

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
