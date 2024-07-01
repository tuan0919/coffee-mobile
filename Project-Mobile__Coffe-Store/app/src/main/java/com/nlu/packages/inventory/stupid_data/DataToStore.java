package com.nlu.packages.inventory.stupid_data;
import com.nlu.packages.response_dto.order.OrderResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class DataToStore {
    List<OrderResponseDTO.StoreDTO> list;

    public DataToStore() {
        list = new ArrayList<>();
        OrderResponseDTO.StoreDTO sd1 = new OrderResponseDTO.StoreDTO((long)1,"HCM Hoàng Việt","TP. Hồ Chí Minh,Tân Bình,Phường 4,17 Út tịch","https://file.hstatic.net/1000075078/file/12232963_1646846405589379_7723364037985650962_o_9f5674e7c32a443094da29aed456c80f.jpeg");
        OrderResponseDTO.StoreDTO sd2 = new OrderResponseDTO.StoreDTO((long)2,"The Coffee House 64A Lữ Gia","TP. Hồ Chí Minh,Quận 11,Phường 15,64A Đ.Lữ Gia","https://lh5.googleusercontent.com/p/AF1QipNAC8L3aeIV4zLMNnYs7cSlaYf1Bn7W4WczpJ40=w408-h272-k-no");
        list.add(sd1);
        list.add(sd2);
    }

    public List<OrderResponseDTO.StoreDTO> getList() {
        return list;
    }
}