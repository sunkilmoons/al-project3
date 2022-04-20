package com.company;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {

    static HashSet<String> dictionary = new HashSet<>();

    public static void main(String[] args) {
//        List<String> inputWords = // when not testing load input words and repeat for each input word.

        String inputWord = "aliceinwonderland";

        String dictFile = "./data/aliceInWonderlandDictionary.txt";
        loadDictionary(dictFile);

        List<String> splitWords = splitIntoWords(inputWord);

        printResult(inputWord, splitWords);
    }

    /**
     * Let n be the length of the input string.
     * c[i][j] is an nxn, two-dimensional array. Then c[i][j] stores the minimum number of cuts
     * needed to cut the substring starting on the ith character and ending on the jth character into words.
     * If it is not possible to cut, then assign it a value that is infeasible, (e.g., -1). Any time you see that
     * value in the array, that would mean that you cannot split that substring into words.
     * So in the first example above, c[5][12] would correspond to the substring “inwonder”. Then you want to set
     * c[5][12] to be 2 because this can be split into “in wonder”.
     * In the first example, c[6][10] would correspond to the substring “nwond”. This cannot be split into words in
     * the word list, and therefore could set c[6][10] to be -1.
     * The base cases could be c[i][j] = 1 if the substring corresponding to i and j is in the word list, and
     * c[i][i] = 0 if the ith character is not a word in the word list (i.e., the subproblem has only one character
     * in it, but that character is not a word in the word list).
     * Then the trick is to figure out how to define c[i][j] as a function of its subproblems if the substring has
     * more than 1 character in it and that substring is not in the word list itself.
     *
     * @param inputWord the word to split from the dictionary
     * @return input words split into the minimum number of words from the dictionary
     */
    static List<String> splitIntoWords(String inputWord) {
        int[][] cuts = new int[inputWord.length()][inputWord.length()];



        return Collections.emptyList();
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
