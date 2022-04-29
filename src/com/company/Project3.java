package com.company;

import java.io.File;
import java.util.*;

public class Main {

    static HashSet<String> dictionary = new HashSet<>();
    static final boolean log = false;

    public static void main(String[] args) {

        ArrayList<String> inputWords = new ArrayList<>();
        loadWordsToCollection("./input.txt", inputWords);

        String dictFile = "./aliceInWonderlandDictionary.txt";
        loadWordsToCollection(dictFile, dictionary);

        for (String inputWord : inputWords)
            printResult(inputWord, splitIntoWords(inputWord));
    }

    static void checkCorrectAnswer(String inputWord) {
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
                break;
            case "williamwillwritewonderfulwalrus":
                correctAnswer = Arrays.asList("william", "will", "write", "wonderful", "walrus");
                break;
            case "tonguetoes":
                correctAnswer = Arrays.asList("tongue", "toes");
                break;
            case "suddenly":
                correctAnswer = Arrays.asList("suddenly");
                break;
            case "alicee":
            case "tacosaregood":
                correctAnswer = Collections.emptyList();
                break;
            case "tiredtinytoes":
                correctAnswer = Arrays.asList("tired", "tiny", "toes");
                break;
            default:
                return null;
        }
        if (!splitWords.equals(correctAnswer)) return correctAnswer;
        return null;
    }

    /**
     * Let n be the length of the input string.
     * c[i][j] is an nxn, two-dimensional array. Then c[i][j] stores the minimum number of cuts
     * needed to cut the substring starting on the ith character and ending on the jth character into words.
     * If it is not possible to cut, then assign it a value that is infeasible, (e.g., -1). Any time you see that
     * value in the array, that would mean that you cannot split that substring into words.
     * So in the first example above, c[5][12] would correspond to the substring â€œinwonderâ€�. Then you want to set
     * c[5][12] to be 2 because this can be split into â€œin wonderâ€�.
     * In the first example, c[6][10] would correspond to the substring â€œnwondâ€�. This cannot be split into words in
     * the word list, and therefore could set c[6][10] to be -1.
     * The base cases could be c[i][j] = 1 if the substring corresponding to i and j is in the word list, and
     * c[i][i] = 0 if the ith character is not a word in the word list (i.e., the subproblem has only one character
     * in it, but that character is not a word in the word list).
     * Then the trick is to figure out how to define c[i][j] as a function of its subproblems if the substring has
     * more than 1 character in it and that substring is not in the word list itself.
     * <p>
     * This is a bottom up approach
     *
     * @param iw the word to split from the dictionary
     * @return input words split into the minimum number of words from the dictionary
     */
    static List<String> splitIntoWords(String iw) {

        char[] ca = iw.toCharArray();
        String inputWord = String.copyValueOf(ca);
        StringBuilder sb = new StringBuilder();

        final int n = inputWord.length();
        int[][] cuts = new int[n+1][n+1];
        String[][] frag = new String[n+1][n+1];
        int i,j;

        ArrayList<String> words = new ArrayList<>();

        if (log) System.out.printf("\nn = %d\n", n);

        for(i = 1; i <= n; i++) {
        	for(j = i; j <= n; j++) {
                if (log) System.out.printf("i = %d, j = %d, ", i, j);
                String substring = inputWord.substring(i-1, j);
                if(dictionary.contains(substring)) {
                	cuts[i][j] = cuts[i-1][j-1] + substring.length();
                	frag[i][j] = substring;
                }
                else {
                	cuts[i][j] = Math.max(cuts[i-1][j], cuts[i][j-1]);
                }
                if (log) System.out.printf("count = %d, substring = %s\n", cuts[i][j], substring);
            }
        }
        j=n;
        for(i=n; i>0;) {
            if(dictionary.contains(frag[i][j]) && cuts[i][j] > cuts[i-1][j] && cuts[i][j] > cuts[i][j-1]) {
                if (log) System.out.println(frag[i][j]);
            	words.add(frag[i][j]);

                // remove words from input word starting at the last occurrence
                // this is to assure that at the end, all words were found and split from the dictionary
                int end = inputWord.lastIndexOf(frag[i][j]);
                if (end == 0) sb.append(inputWord, frag[i][j].length(), inputWord.length());
                else sb.append(inputWord, 0, end);
                inputWord = sb.toString();
                sb.setLength(0);

                if (log) System.out.printf("Input word is now %s\n", inputWord);
            	i--;
            	j--;
            }
            else {
            	if(cuts[i-1][j]>= cuts[i][j-1]) {
            		i--;
            	}
            	else{
            		j--;
            	}
            }
        }
        Collections.reverse(words);
        return inputWord.isEmpty() ? words : Collections.emptyList();
    }

    static void loadWordsToCollection(String fileName, Collection<String> c) {
        try {
            File file = new File(fileName);

            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                c.add(sc.nextLine().trim());
            }
        } catch (Exception e) {
            System.out.printf("ERROR: Failed to find file: %s.\n", fileName);
            e.printStackTrace();
        }
    }

    static void printResult(String inputWord, List<String> splitWords) {
        if (splitWords.isEmpty()) System.out.printf("%s cannot be split into AiW words.\n", inputWord);
        else System.out.printf(
                    "%s can be split into %d AiW words: %s\n",
                    inputWord,
                    splitWords.size(),
                    String.join(", ", splitWords)
            );
    }
}
