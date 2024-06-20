package nlu.hcmuaf.android_coffee_app.service.templates;

import nlu.hcmuaf.android_coffee_app.dto.response.product_controller.ProductResponseDTO;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;

public interface IProductService extends IInitializerData {
    ProductResponseDTO findProductById(Long id) throws CustomException;
}
