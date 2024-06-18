package nlu.hcmuaf.android_coffee_app.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.transaction.Transactional;
import nlu.hcmuaf.android_coffee_app.dto.json.categories.CategoryJSON;
import nlu.hcmuaf.android_coffee_app.dto.json.stores.StoreJSON;
import nlu.hcmuaf.android_coffee_app.entities.Addresses;
import nlu.hcmuaf.android_coffee_app.entities.Categories;
import nlu.hcmuaf.android_coffee_app.entities.Stores;
import nlu.hcmuaf.android_coffee_app.repositories.AddressRepository;
import nlu.hcmuaf.android_coffee_app.repositories.CategoryRepository;
import nlu.hcmuaf.android_coffee_app.repositories.StoreRepository;
import nlu.hcmuaf.android_coffee_app.service.templates.ICategoryService;
import nlu.hcmuaf.android_coffee_app.service.templates.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.mail.Store;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl extends AService implements IStoreService {
    @Autowired
    private StoreRepository repository;
    @Autowired
    private AddressRepository addressRepository;
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void initData() {
        if (!repository.findAll().iterator().hasNext()) {
            try {
                ClassPathResource resource = new ClassPathResource("stores.json");
                List<StoreJSON> storeJSONS = objectMapper.readValue(resource.getInputStream(),
                        new TypeReference<List<StoreJSON>>() {});
                List<Stores> list = new ArrayList<>();
                for (StoreJSON json : storeJSONS) {
                    var store = new Stores();
                    store.setStoreName(json.getStoreName());
                    store.setActive(json.isActive());
                    store.setAvatar(json.getAvatar());
                    var address = modelMapper.map(json.getStoreAddress(), Addresses.class);
                    addressRepository.save(address);
                    store.setAddress(address);
                    list.add(store);
                }
                repository.saveAll(list);
            } catch (Exception e) {
                throw new RuntimeException("Error loading stores.json", e);
            }
        }
    }
}
