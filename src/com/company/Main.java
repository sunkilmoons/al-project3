package com.company;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    static HashSet<String> dictionary = new HashSet<>();

    public static void main(String[] args) {
//        List<String> inputWords = // when not testing load input words...

        String inputWord = "aliceinwonderland";

        String dictFile = "./data/aliceInWonderlandDictionary.txt";
        loadDictionary(dictFile);

        // TODO: algorithm here...


        printResult(inputWord, Collections.emptyList());
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

    static void printResult(String inputWord, List<String> splitWords) {
        System.out.printf(
                "%s can be split into %d AiW words: %s",
                inputWord,
                splitWords.size(),
                String.join(", ", splitWords)
                );
    }
}
