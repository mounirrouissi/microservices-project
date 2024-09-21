package com.mr.microservices.inventory.service;

import com.mr.microservices.inventory.model.Inventory;
import com.mr.microservices.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode, Integer quantity) {
        log.info(" Start -- Received request to check stock for skuCode {}, with quantity {}", skuCode, quantity);
        inventoryRepository.existsBySkuCode(skuCode);
        boolean isInStock = inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
        log.info(" End -- Product with skuCode {}, and quantity {}, is in stock - {}", skuCode, quantity, isInStock);
        return isInStock;
    }

    public List<Inventory> getAllInventory() {
        log.info(" Start -- Received request to get all inventory");
        List<Inventory> allInventory = inventoryRepository.findAll();
        log.info(" End -- All inventory - {}", allInventory);
        return allInventory;
    }
}
