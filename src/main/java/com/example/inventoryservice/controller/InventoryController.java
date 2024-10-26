package com.example.inventoryservice.controller;


import com.example.inventoryservice.dto.InventoryCheckRequest;
import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.entity.InventoryItem;
import com.example.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory-manager/")
public class InventoryController {
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Endpoint to check the stock of a specific item by ID
    @GetMapping("/{itemId}")
    public ResponseEntity<InventoryItem> getStock(@PathVariable("itemId") Long itemId) {
        InventoryItem item = inventoryService.getStock(itemId);
        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to list all inventory items
    @GetMapping("/items")
    public ResponseEntity<List<InventoryItem>> getAllInventoryItems() {
        List<InventoryItem> items = inventoryService.getAllInventoryItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    // Endpoint to add stock for a specific item
    @PostMapping
    public ResponseEntity<InventoryItem> addStock(@RequestBody InventoryItem item) {
        InventoryItem addedItem = inventoryService.addStock(item);
        return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    @PutMapping("/check")
    public ResponseEntity<InventoryResponse> checkStock(@RequestBody InventoryCheckRequest item) {
        InventoryResponse availableItem = inventoryService.check(item);
        return new ResponseEntity<>(availableItem, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<InventoryItem> updateStock(
            @RequestBody InventoryCheckRequest item) {
        InventoryItem updatedItem = inventoryService.updateByName(item);
        if (updatedItem != null) {
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to update stock quantity for a specific item
    @PutMapping("/{itemId}")
    public ResponseEntity<InventoryItem> updateStock(
            @PathVariable("itemId") Long itemId,
            @RequestParam("quantity") int quantity) {
        InventoryItem updatedItem = inventoryService.updateStock(itemId, quantity);
        if (updatedItem != null) {
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to delete an item from inventory
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable("itemId") Long itemId) {
        boolean deleted = inventoryService.deleteItem(itemId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
