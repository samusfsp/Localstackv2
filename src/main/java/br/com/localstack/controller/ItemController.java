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

    @PostMapping
    public void createItem(@RequestBody Item item) {
        itemService.createItem(item);
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable String id) {
        return itemService.getItemById(id);
    }

    @PutMapping("/{id}")
    public void updateItem(@PathVariable String id, @RequestBody Item item) {
        itemService.updateItem(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable String id) {
        itemService.deleteItemById(id);
    }
}
