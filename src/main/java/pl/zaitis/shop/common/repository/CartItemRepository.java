package pl.zaitis.shop.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.zaitis.shop.common.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Long countByCartId(Long cartId);

    @Query("DELETE FROM CartItem ci where ci.cartId=cartId")
    @Modifying
    void deleteByCartId(Long id);
}
