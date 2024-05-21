package jpaswing.controller;

import jpaswing.entity.Item;
import jpaswing.repository.ItemRepository;
import jpaswing.repository.ItemRepositoryPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ItemController {
    private final ItemRepository itemRepository;
    private final ItemRepositoryPagination itemRepositoryPagination;
    private int currentPage = 0;
    private int count;
    private Optional<Item> currentItem;
    @Autowired
    public ItemController(ItemRepository itemRepository, ItemRepositoryPagination itemRepositoryPagination){
        this.itemRepository = itemRepository;
        this.itemRepositoryPagination = itemRepositoryPagination;
        this.count = itemRepositoryPagination.countAllRecords();
    }
    public Optional<Item> getItem(){
        PageRequest pr = PageRequest.of(currentPage, 1);
        currentItem = Optional.of(itemRepositoryPagination.findAll(pr).getContent().get(0));
        return currentItem;
    }
    public Optional<Item> next(){
        this.count = itemRepositoryPagination.countAllRecords();
        if (currentPage == this.count -1 ) return currentItem;
        currentPage++;
        return getItem();
    }
    public Optional<Item> previous(){
        if (currentPage == 0) return currentItem;

        currentPage--;
        return getItem();
    }
    public Optional<Item> first(){
        currentPage = 0;
        return getItem();
    }
    public Optional<Item> last(){
        this.count = itemRepositoryPagination.countAllRecords();
        currentPage = count - 1;
        return getItem();
    }

}

