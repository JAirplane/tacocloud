package com.tacocloud.data;

import com.tacocloud.domain.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

//    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable page);
}
