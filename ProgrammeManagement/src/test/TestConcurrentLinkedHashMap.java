package test;
import adt.ConcurrentLinkedHashMap;


public class TestConcurrentLinkedHashMap {

    public static void main(String[] args) {

        ConcurrentLinkedHashMap<String, Integer> map = new ConcurrentLinkedHashMap<>(16);

        // Test Add and Get methods
        System.out.println("\n Test Add");
        System.out.println("=================================");
        System.out.println("Value for key 'one' before put: " + map.getValue("one"));
        map.put("one", 1);
        System.out.println("Value for key 'one' after put: " + map.getValue("one"));

        // Test get or Default method
        System.out.println("\n Test Get or Default");
        System.out.println("=================================");
        map.put("two", 2);
        System.out.println("Value for key 'two': " + map.getOrDefault("two", 0));
        System.out.println("Value for key 'ten': " + map.getOrDefault("ten", 0));

        // Test Replace method
        System.out.println("\n Test Replace");
        System.out.println("=================================");
        map.put("three", 3);
        System.out.println("Value for key 'three' before replaced: " + map.getValue("three"));
        map.replace("three", 33);
        System.out.println("Value for key 'three' after replaced: " + map.getValue("three"));

        // Test Size and contains key methods
        System.out.println("\n Test Size and contains key");
        System.out.println("=================================");
        System.out.println("Size of the map: " + map.size());
        System.out.println("Map contains key 'one': " + map.containsKey("one"));
        System.out.println("Map contains key 'four': " + map.containsKey("four"));

        // Test Remove operation
        System.out.println("\n Test Remove");
        System.out.println("=================================");
        map.put("fifth", 5);
        System.out.println("Value for key 'two' after remove: " + map.getValue("fifth"));
        map.remove("fifth");
        System.out.println("Value for key 'two' after remove: " + map.getValue("fifth"));

        // Test Copy and Clear operations
        System.out.println("\n Test Copy and Clear");
        System.out.println("=================================");
        System.out.println("Is the map empty? " + map.isEmpty());
        System.out.println("Size of map: " + map.size());

        ConcurrentLinkedHashMap<String, Integer> copyMap = new ConcurrentLinkedHashMap<>(16);
        map.copy(copyMap);
        System.out.println("Size of copyMap: " + copyMap.size());

        map.clear();
        System.out.println("Is the map empty after clear? " + map.isEmpty());
    }

}
