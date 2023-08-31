package test;
import adt.ConcurrentLinkedHashMap;


public class TestConcurrentLinkedHashMap {

    public static void main(String[] args) {
        ConcurrentLinkedHashMap<String, Integer> map = new ConcurrentLinkedHashMap<>(16);

//        System.out.println(map.put("one", 1));
//        map.put("two", 2);
//        map.put("three", 3);

//        System.out.println("Value for key 'one' before put: " + map.getValue("one"));
        System.out.println(map.put("one", 1));
        System.out.println("Value for key 'one' after put: " + map.getValue("one"));

//        System.out.println("Value for key 'two': " + map.getOrDefault("two", 0));
//
//        map.replace("three", 33);
//
//        System.out.println("Size of the map: " + map.size());
//        System.out.println("Map contains key 'one': " + map.containsKey("one"));
//        System.out.println("Map contains key 'four': " + map.containsKey("four"));
//
//        map.remove("two");
//
//        System.out.println("Is the map empty? " + map.isEmpty());
//
//        ConcurrentLinkedHashMap<String, Integer> copyMap = new ConcurrentLinkedHashMap<>(16);
//        map.copy(copyMap);
//        System.out.println("Size of copyMap: " + copyMap.size());
//
//        map.clear();
//        System.out.println("Is the map empty after clear? " + map.isEmpty());
    }

}
