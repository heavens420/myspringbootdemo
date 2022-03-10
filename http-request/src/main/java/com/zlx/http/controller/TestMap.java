package com.zlx.http.controller;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;

public class TestMap {
    public static void main(String[] args) {
        MultiValueMap<String, Integer> map = new LinkedMultiValueMap(8);
        map.put("1", new ArrayList<Integer>(Arrays.asList(4,5,6)));
        map.add("1",9);
        map.add("2",92);
        map.add("2",93);
        map.add("3",96);

        map.forEach((key,value)-> System.out.println("key = " + key+"\nvalue = "+ value));
    }
}
