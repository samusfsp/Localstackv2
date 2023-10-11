package br.com.localstack.controller;

import br.com.localstack.model.Item;
import br.com.localstack.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/{tableName}")
    public void createItem(@PathVariable String tableName, @RequestBody Item item) {
        itemService.createItem(tableName, item);
    }

    @GetMapping("/{tableName}/{id}")
    public Item getItemById(@PathVariable String tableName, @PathVariable String id) {
        return itemService.getItemById(tableName, id);
    }

    @PutMapping("/{tableName}")
    public void updateItem(@PathVariable String tableName, @RequestBody Item item) {
        itemService.updateItem(tableName, item);
    }

    @DeleteMapping("/{tableName}/{id}")
    public void deleteItemById(@PathVariable String tableName, @PathVariable String id) {
        itemService.deleteItemById(tableName, id);
    }
}
