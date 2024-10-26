package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.InventoryCheckRequest;
import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.entity.InventoryItem;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    // Temporary in-memory storage for inventory items
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public InventoryItem getStock(Long itemId) {
        return inventoryRepository.findById(itemId).orElse(null);
    }

    public List<InventoryItem> getAllInventoryItems() {
        return inventoryRepository.findAll();
    }


    public InventoryItem addStock(InventoryItem item) {
        return inventoryRepository.save(item);

    }

    public InventoryItem updateStock(Long itemId, int quantity) {
        Optional<InventoryItem> existingItem = inventoryRepository.findById(itemId);
        if (existingItem.isPresent()) {
            InventoryItem item = existingItem.get();
            item.setQuantity(quantity);
            inventoryRepository.save(item);
            return item;
        }
        return null;
    }

    public boolean deleteItem(Long itemId) {
         if(inventoryRepository.findById(itemId).isPresent()) {
             inventoryRepository.deleteById(itemId);
             return true;
         }
         return false;
    }

    @PostConstruct
    public void seedData() {
        if (inventoryRepository.count() == 0) {
            inventoryRepository.save(new InventoryItem("Laptop", 50));
            inventoryRepository.save(new InventoryItem("Smartphone", 100));
        }
    }

    public InventoryResponse check(InventoryCheckRequest item) {
        Optional<InventoryItem> existingItem = inventoryRepository.findByName(item.name());
        System.out.println(existingItem);
        if(existingItem.isPresent() && existingItem.get().getQuantity()>= item.quantity()) {
            return new InventoryResponse(true);
        }
        return new InventoryResponse(false);
    }

    public InventoryItem updateByName(InventoryCheckRequest item) {
        Optional<InventoryItem> existingItem = inventoryRepository.findByName(item.name());
        if (existingItem.isPresent()) {
            InventoryItem itemToSave = existingItem.get();
            itemToSave.setQuantity(itemToSave.getQuantity()-item.quantity());
            inventoryRepository.save(itemToSave);
            return itemToSave;
        }
        return null;
    }
}

