package nlu.hcmuaf.android_coffee_app.repositories;

import nlu.hcmuaf.android_coffee_app.entities.WishList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends CrudRepository<WishList, Long> {
    List<WishList> findAllByUserUserId(long userId);
    int deleteByProductProductIdAndAndUserUserId(long productId, long userId);
}
