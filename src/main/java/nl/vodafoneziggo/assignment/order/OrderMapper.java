package nl.vodafoneziggo.assignment.order;

import java.util.List;
import nl.vodafoneziggo.assignment.model.OrderGetResponseBody;
import nl.vodafoneziggo.assignment.model.OrderPostRequestBody;
import nl.vodafoneziggo.assignment.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface OrderMapper {

  OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

  List<OrderGetResponseBody> toOrderGetResponseBody(List<Order> orders);

  @Mapping(target = "orderId", expression = "java(java.util.UUID.randomUUID().toString())")
  @Mapping(source = "userDto.email", target = "email")
  Order toOrder(UserDto userDto, OrderPostRequestBody postRequestBody);

}
