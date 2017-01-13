package com;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by gaorongyu on 16/12/30.
 */
public class UtilTest {

    public static void main(String[] args) {
        testCollection();
    }

    public static void testCollection() {

        Long amount =1L;
        Long price =1L;
        Integer qty = 0;

        amount = price * qty;

    }

    public static void addAndPrint(Map map) {
        map.put("8", "eight");
        map.put("7", "seven");
        map.put("1", "one");
        map.put("2", "two");

        String mapType = map.getClass().getName();

        Iterator iter = map.keySet().iterator();
        while (iter.hasNext()) {
            System.out.println("mapType: " + mapType + " map.entry: " + iter.next());
        }
    }

    public static void testIntern() {
        String a = "abc";
        String b = new String("abc");
        String c = "a" + "bc";

        System.out.println(" a.intern: " + a.intern());
        System.out.println(" b.intern: " + b.intern());
        System.out.println(" c.intern: " + c.intern());
        System.out.println(" a.hashcode: " + a.hashCode());

        System.out.println(a == b.intern());
        System.out.println(a == c.intern());
        System.out.println(b.intern() == c.intern());
    }

}
