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

    private ArrayList<String> wordList = new ArrayList<>();
    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            String sorted = sortLetters(word);
            if (!lettersToWord.containsKey(sorted)) {
                lettersToWord.put(sorted, new ArrayList<String>());
                lettersToWord.get(sorted).add(word);
            } else {
                lettersToWord.get(sorted).add(word);
            }
            wordList.add(word);
        }
    }

    public boolean isGoodWord(String word, String base) {
        return true;
    }

    public List<String> getAnagrams(String targetWord) {
        String sortedWord = sortLetters(targetWord);
        return lettersToWord.containsKey(sortedWord) ?
                lettersToWord.get(sortedWord) :
                new ArrayList<String>();
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        return result;
    }

    public String pickGoodStarterWord() {
        Log.d(LOG_TAG, sortLetters("stop"));
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
