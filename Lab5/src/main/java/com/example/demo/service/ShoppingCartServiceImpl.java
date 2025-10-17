package com.example.demo.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import com.example.demo.model.DB;
import com.example.demo.model.Item;

@Service
@SessionScope
public class ShoppingCartServiceImpl implements ShoppingCartService {
    Map<Integer, Item> map = new HashMap<>();

    @Override
    public Item add(Integer id) {
        Item item = DB.items.get(id);
        if (item != null) {
            Item existingItem = map.get(id);
            if (existingItem != null) {
                existingItem.setQty(existingItem.getQty() + 1);
            } else {
                Item newItem = new Item(item.getId(), item.getName(), item.getPrice(), 1);
                map.put(id, newItem);
            }
        }
        return map.get(id);
    }

    @Override
    public void remove(Integer id) {
        map.remove(id);
    }

    @Override
    public Item update(Integer id, int qty) {
        Item item = map.get(id);
        if (item != null) {
            item.setQty(qty);
        }
        return item;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Collection<Item> getItems() {
        return map.values();
    }

    @Override
    public int getCount() {
        return map.values().stream().mapToInt(Item::getQty).sum();
    }

    @Override
    public double getAmount() {
        return map.values().stream().mapToDouble(item -> item.getPrice() * item.getQty()).sum();
    }
}