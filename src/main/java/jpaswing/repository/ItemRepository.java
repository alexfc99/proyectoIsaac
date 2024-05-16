package jpaswing.repository;

import jpaswing.entity.Item;
import org.springframework.stereotype.Component;

@Component
public interface ItemRepository {
    Item findFirstByOrderIdAsc();
}
