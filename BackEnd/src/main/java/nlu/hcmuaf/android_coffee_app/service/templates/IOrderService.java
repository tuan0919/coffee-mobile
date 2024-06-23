package nlu.hcmuaf.android_coffee_app.service.templates;

import nlu.hcmuaf.android_coffee_app.dto.request.order.CreateOrderRequestDTO;
import nlu.hcmuaf.android_coffee_app.dto.response.order.OrderResponseDTO;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IOrderService {
    void createOrder(String username, CreateOrderRequestDTO dto) throws CustomException;
    List<OrderResponseDTO> getOrders(String username) throws CustomException;
}
