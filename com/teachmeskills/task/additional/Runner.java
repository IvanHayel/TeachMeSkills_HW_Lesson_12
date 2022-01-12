package com.teachmeskills.task.additional;

import com.teachmeskills.task.additional.model.DynamicArray;

/**
 * Imagine that Java does not have a collection of type ArrayList.
 * Create your own class that simulates the work of a dynamic collection class -
 * i.e. create your own custom collection.
 * The collection will be based on an array.
 * A custom collection should store elements of different classes (i.e. it is generic).
 * Provide operations for adding an element, deleting an element, getting an element by index,
 * checking if there is an element in a collection, a method for clearing all collections.
 * Provide constructor with no parameters - creates an array of default size.
 * Provide a constructor with a configurable size of the internal array.
 * Provide the possibility of automatic expansion of the collection when adding an element in
 * the case when the collection is already full.
 */

public class Runner {
    public static void main(String[] args) {
        DynamicArray<String> stringArray = new DynamicArray<>(3);
        System.out.println(stringArray);
        stringArray.add("Afghanistan");
        stringArray.add("Cameroon");
        stringArray.add("Denmark");
        System.out.println(stringArray);
        stringArray.add("Gabon");
        stringArray.add("Sudan");
        stringArray.add("Madagascar");
        System.out.println(stringArray);
        stringArray.remove(stringArray.size() - 1);
        stringArray.remove(0);
        stringArray.drop("Gabon");
        System.out.println(stringArray);
        if (stringArray.contains("Denmark")) {
            System.out.println("Of course contains 'Denmark'");
        }
        String country = stringArray.get(0);
        System.out.println("Country = '" + country + '\'');
        stringArray.clear();
        System.out.println(stringArray);
        System.out.println();

        DynamicArray<Integer> integerArray = new DynamicArray<>();
        for (int counter = 0; counter < 30; counter += 2) {
            integerArray.add(counter);
        }
        System.out.println(integerArray);
        if (integerArray.contains(14)) {
            System.out.println("Of course contains 14.");
        }
        integerArray.drop(28);
        integerArray.drop(0);
        System.out.println(integerArray.drop(239));
        System.out.println(integerArray);
    }
}