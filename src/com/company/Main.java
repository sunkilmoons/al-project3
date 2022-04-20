package com.company;

import java.io.File;
import java.util.*;

public class Main {

    static HashSet<String> dictionary = new HashSet<>();

    public static void main(String[] args) {
//        List<String> inputWords = // when not testing load input words and repeat for each input word.

//        String inputWord = "aliceinwonderland";
        String inputWord = "williamwillwritewonderfulwalrus";

        String dictFile = "./data/aliceInWonderlandDictionary.txt";
        loadDictionary(dictFile);

        List<String> splitWords = splitIntoWords(inputWord);
        List<String> correctAnswer = assertAnswerIsCorrectForInput(inputWord, splitWords);

        if (correctAnswer != null) {
            System.out.printf(
                    "Your answer is not correct. Yours: %s\nCorrect answer: %s\n",
                    String.join(", ", splitWords),
                    String.join(", ", correctAnswer)
            );
        } else printResult(inputWord, splitWords);

    }

    static List<String> assertAnswerIsCorrectForInput(String inputWord, List<String> splitWords) {
        List<String> correctAnswer;
        switch (inputWord) {
            case "aliceinwonderland":
                correctAnswer = Arrays.asList("alice", "in", "wonderland");
                if (!splitWords.equals(correctAnswer)) return correctAnswer;
                break;
            case "williamwillwritewonderfulwalrus":
                correctAnswer = Arrays.asList("william will write wonderful walrus");
                if (!splitWords.equals(correctAnswer)) return correctAnswer;
                break;
            default:
                return null;
        }
        return correctAnswer;
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
     * <p>
     * This is a bottom up approach
     *
     * @param inputWord the word to split from the dictionary
     * @return input words split into the minimum number of words from the dictionary
     */
    static List<String> splitIntoWords(String inputWord) {
        final int n = inputWord.length();
        int[][] cuts = new int[n][n];

        ArrayList<String> words = new ArrayList<>();

        System.out.printf("\nn = %d\n", n);
        for (int i = 0; i < n; i++) {
            for (int j = n - 1; j >= i; j--) {

                System.out.printf("i = %d, j = %d, count = %d, ", i, j, cuts[i][j]);
                String substring = inputWord.substring(i, j + 1);
                System.out.printf("substring = %s\n", substring);

                if (dictionary.contains(substring) && !words.isEmpty()) {
                    String lastWord = words.get(words.size() - 1);
                    if (substring.contains(lastWord)) {
                        words.remove(lastWord);
                        words.add(substring);
                    } else if (!lastWord.contains(substring)) {
                        words.add(substring);
                    }
                } else if (dictionary.contains(substring)) {
                    words.add(substring);
                    break;
                }
            }
        }

        return words;
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
