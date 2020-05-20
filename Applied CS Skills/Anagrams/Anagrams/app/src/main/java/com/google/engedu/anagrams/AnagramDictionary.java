/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private static final String LOG_TAG = "Logging";
    private Random random = new Random();

    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    private HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();

    private int wordLength = DEFAULT_WORD_LENGTH;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            String letters = sortLetters(word);
            int wordLength = word.length();

            if (!lettersToWord.containsKey(letters)) {
                lettersToWord.put(letters, new ArrayList<String>());
            }
            if (!sizeToWords.containsKey(wordLength)) {
                sizeToWords.put(wordLength, new ArrayList<String>());
            }

            lettersToWord.get(letters).add(word);
            sizeToWords.get(wordLength).add(word);

            wordSet.add(word);
        }
    }


    public boolean isGoodWord(String word, String base) {
        boolean containsSubstring = false;
        char[] wordLetters = word.toCharArray(), baseLetters = base.toCharArray();
        int baseLength = baseLetters.length, wordLength = wordLetters.length;
        if (baseLength <= wordLength) {
            char startingBaseLetter = baseLetters[0];
            for (int i = 0; i < wordLength && !containsSubstring; i++) {
                if (wordLetters[i] == startingBaseLetter && wordLength - (i + 1) >= baseLength) {
                    containsSubstring = true;
                    int j, nextStart = i;
                    for (j = 1; j < baseLength && j + i < wordLength; j++) {
                        if (wordLetters[i + j] == startingBaseLetter) {
                            nextStart = i + j;
                        }
                        if (!(wordLetters[i + j] == baseLetters[j])) {
                            containsSubstring = false;
                            break;
                        }
                    }
                    if (nextStart == i) {
                        nextStart = i + j;
                    }
                    i = nextStart;
                }
            }
        }
        return wordSet.contains(word) && !containsSubstring;
    }

    public List<String> getAnagrams(String targetWord) {
        String sortedWord = sortLetters(targetWord);
        return lettersToWord.containsKey(sortedWord) ?
                lettersToWord.get(sortedWord) :
                new ArrayList<String>();
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        String letters = sortLetters(word);
        String newWord, newWordLetters;
        for (char c = 'a'; c <= 'z'; c++) {
            newWord = letters + c;
            newWordLetters = sortLetters(newWord);
            if (lettersToWord.containsKey(newWordLetters)) {
                for (String anagram : lettersToWord.get(newWordLetters)) {
                    if (isGoodWord(anagram, word) && !result.contains(anagram)) {
                        result.add(anagram);
                    }
                }
            }
        }
        return result;
    }

    public List<String> getAnagramsWithTwoMoreLetters(String word) {
        ArrayList<String> result = new ArrayList<String>();
        String letters = sortLetters(word);
        String newWord, newWordLetters;
        for (char c = 'a'; c <= 'z'; c++) {
            for (char d = 'a' ; d <= 'z'; d++) {
                newWord = letters + c;
                newWordLetters = sortLetters(newWord);
                if (lettersToWord.containsKey(newWordLetters)) {
                    for (String anagram : lettersToWord.get(newWordLetters)) {
                        if (isGoodWord(anagram, word) && !result.contains(anagram)) {
                            result.add(anagram);
                        }
                    }
                }
            }
        }
        return result;
    }

    public String pickGoodStarterWord(boolean isOneLetterMode) {
        ArrayList<String> words = sizeToWords.get(wordLength);
        int totalWords = words.size();
        String goodWord;
        for (int i = random.nextInt(totalWords), j = 0; j < totalWords; i = ++i % totalWords, j++) {
            goodWord = words.get(i);
            String letters = sortLetters(goodWord);
            int anagramCount;
            if (isOneLetterMode) {
                anagramCount = getAnagramsWithOneMoreLetter(goodWord).size();
            } else {
                anagramCount = getAnagramsWithTwoMoreLetters(goodWord).size();
            }
            if (lettersToWord.containsKey(letters)) {
                if (anagramCount >= MIN_NUM_ANAGRAMS) {
                    return goodWord;
                } else {
                    sizeToWords.get(wordLength).remove(goodWord);
                    lettersToWord.get(letters).remove(goodWord);
                }
            }
        }
        wordLength = ++wordLength % MAX_WORD_LENGTH;
        return "stop";
    }

    private String sortLetters(String word) {
        char[] letters = word.toLowerCase().toCharArray();
        return new String(quicksort(letters, 0, letters.length - 1));
    }

    private char[] quicksort(char[] array, int low, int high) {
        if (low < high) {
            int partition = partition(array, low, high);
            quicksort(array, low, partition - 1);
            quicksort(array, partition + 1, high);
        }
        return array;
    }

    private int partition(char[] array, int low, int high) {
        int pivot = array[high];
        char temp;
        int j = low, i;
        for (i = low; i <= high; i++) {
            if (array[i] < pivot) {
                temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                j++;
            }
        }
        temp = array[--i];
        array[i] = array[j];
        array[j] = temp;
        return j;
    }
}
