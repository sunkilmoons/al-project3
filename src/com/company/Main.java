package com.company;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    static HashSet<String> dictionary = new HashSet<>();

    public static void main(String[] args) {
        String dictFile = "./data/aliceInWonderlandDictionary.txt";
        loadDictionary(dictFile);
    }

    static void loadDictionary(String fileName) {
        try {
            File file = new File(fileName);

            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String word = sc.nextLine();
                dictionary.add(word);
            }

            System.out.printf("Successfully loaded dictionary. Words are: %s", dictionary.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
