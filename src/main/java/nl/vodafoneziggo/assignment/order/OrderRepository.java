package nl.vodafoneziggo.assignment.order;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepository extends JpaRepository<Order, Long> {

  Optional<Order> findByEmailAndProductId(String email, String productId);
}
