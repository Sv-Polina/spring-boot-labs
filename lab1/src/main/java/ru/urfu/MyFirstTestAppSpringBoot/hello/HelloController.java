package ru.urfu.MyFirstTestAppSpringBoot.hello;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private ArrayList<String> arrayList;
    private HashMap<Integer, String> hashMap;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name",
            defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/update-array")
    public String updateArrayList(@RequestParam(value = "s") String s) {
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            return "Первый вызов: создан пустой ArrayList";
        }
        arrayList.add(s);
        return String.format("В ArrayList записано значение '%s', элементов: %d", s, arrayList.size());
    }

    @GetMapping("/show-array")
    public ArrayList<String> showArrayList() {
        return arrayList == null ? new ArrayList<>() : arrayList;
    }

    @GetMapping("/update-map")
    public String updateHashMap(@RequestParam(value = "s") String s) {
        if (hashMap == null) {
            hashMap = new HashMap<>();
            return "Первый вызов: создан пустой HashMap";
        }
        int key = hashMap.size() + 1;
        hashMap.put(key, s);
        return String.format("В HashMap записано значение '%s' с ключом %d, элементов: %d", s, key, hashMap.size());
    }

    @GetMapping("/show-map")
    public HashMap<Integer, String> showHashMap() {
        return hashMap == null ? new HashMap<>() : hashMap;
    }

    @GetMapping("/show-all-lenght")
    public String showAllLenght() {
        int arraySize = arrayList == null ? 0 : arrayList.size();
        int mapSize = hashMap == null ? 0 : hashMap.size();
        return String.format("Количество элементов в ArrayList: %d, количество элементов в HashMap: %d",
                arraySize, mapSize);
    }
}
