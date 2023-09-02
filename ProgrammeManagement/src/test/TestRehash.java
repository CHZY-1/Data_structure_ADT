package test;
import adt.ConcurrentHashMapWithLinkedLists;

import java.util.Iterator;


public class TestRehash {

    public static void main(String[] args) {

        rehash();

    }

    public static void rehash(){
        // Create a ConcurrentHashMapWithLinkedLists with a capacity and load factor
        ConcurrentHashMapWithLinkedLists<String, Integer> map = new ConcurrentHashMapWithLinkedLists<>(5, 0.75f);

        map.put("Alice", 100);
        map.put("Alex", 200);
        map.put("Bob", 300);
//        map.put("Charlie", 400);
        Iterator<String> beforeReHashIterator = map.iteratorWithKeys(); // Iterator return key value only
        System.out.println("\n Entries before rehash:");
        while (beforeReHashIterator.hasNext()) {
            String key = beforeReHashIterator.next();
            Integer value = map.getValue(key);
            System.out.println(key + " = " + value);
        }

        System.out.println("Capacity before rehash: " + map.getTotalCapacity());

        // trigger rehash
        map.put("ExtraKey", 1000);

        Iterator<String> afterReHashIterator = map.iteratorWithKeys(); // Iterator return key value only

        // Print the entries after rehash
        System.out.println("\n Entries after rehash:");
        while (afterReHashIterator.hasNext()) {
            String key = afterReHashIterator.next();
            Integer value = map.getValue(key);
            System.out.println(key + " = " + value);
        }

        System.out.println("Capacity after rehash: " + map.getTotalCapacity());
    }
}
