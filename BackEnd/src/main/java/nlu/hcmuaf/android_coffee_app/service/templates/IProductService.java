package nlu.hcmuaf.android_coffee_app.service.templates;

import nlu.hcmuaf.android_coffee_app.dto.response.product.ProductResponseDTO;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;

import java.util.List;

public interface IProductService extends IInitializerData {
    List<ProductResponseDTO> findProducts(String typePathName, String categoryPathName, String name, Long id) throws CustomException;
}
