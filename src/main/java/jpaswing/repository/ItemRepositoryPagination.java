package jpaswing.repository;

import jpaswing.entity.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface ItemRepositoryPagination extends PagingAndSortingRepository<Item,Long> {
    @Query("SELECT COUNT(*) FROM Item ")
    public int countAllRecords();
}
