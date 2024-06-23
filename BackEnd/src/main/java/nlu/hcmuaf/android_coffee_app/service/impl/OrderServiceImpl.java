package nlu.hcmuaf.android_coffee_app.service.impl;

import nlu.hcmuaf.android_coffee_app.dto.request.order.CreateOrderRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.order.OrderResponseDTO;
import nlu.hcmuaf.android_coffee_app.entities.Orders;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.exceptions.UserInfoException;
import nlu.hcmuaf.android_coffee_app.mapper.response.order.OrderDTOMapper;
import nlu.hcmuaf.android_coffee_app.repositories.OrderRepository;
import nlu.hcmuaf.android_coffee_app.repositories.UserRepository;
import nlu.hcmuaf.android_coffee_app.service.templates.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDTOMapper orderDTOMapper;

    @Override
    public void createOrder(String username, CreateOrderRequestDTO dto) throws CustomException {
        var oUser = userRepository.findUsersByUsername(username);
        if (!oUser.isPresent())
            throw new UserInfoException(String.format("User with username: %s doesn't exist.", username));
        var user = oUser.get();
        Orders order = Orders.builder().user(user).build();
        // update this order
        orderDTOMapper.updateOrder(order, dto);
        orderRepository.save(order);
    }

    @Override
    public List<OrderResponseDTO> getOrders(String username) throws CustomException {
        var oUser = userRepository.findUsersByUsername(username);
        if (!oUser.isPresent())
            throw new UserInfoException(String.format("User with username: %s doesn't exist.", username));
        var user = oUser.get();
        var orderSet = user.getOrdersSet();
        return orderSet.stream().map(orderDTOMapper::mapToDTO).toList();
    }
}
