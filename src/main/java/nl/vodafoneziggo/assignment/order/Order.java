package nl.vodafoneziggo.assignment.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "orders") // h2 is not happy with table named order
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "orderID", nullable = false)
  @NotBlank(message = "Order id is required")
  private String orderId;
  @Column(name = "productID", nullable = false)
  @NotBlank(message = "Product id is required")
  private String productId;
  @Column(nullable = false)
  @Email(message = "Invalid e-mail format")
  @NotBlank(message = "Email is required")
  private String email;
  @Column(name = "first_name", nullable = false)
  @NotBlank(message = "First name is required")
  private String firstName;
  @Column(name = "last_name", nullable = false)
  @NotBlank(message = "Last name is required")
  private String lastName;
}
