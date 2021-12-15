package com.example.aggregator.service;

import com.example.aggregator.client.AggregatorRestClient;
import com.example.aggregator.model.Entry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AggregatorService {

    private AggregatorRestClient restClient;

    public AggregatorService(AggregatorRestClient restClient) {
        this.restClient = restClient;
    }

    public Entry getDefinitionFor(String word) {

        return restClient.getDefinitionFor(word);
    }

    public List<Entry> getWordsThatContainSuccessiveLettersAndStartsWith(String chars) {

        List<Entry> wordsThatStartWith = restClient.getWordsStartingWith(chars);
        List<Entry> wordsThatContainSuccessiveLetters = restClient.getWordsThatContainConsecutiveLetters();

        // combine lists with common words
        List<Entry> common = new ArrayList<>(wordsThatStartWith);
        common.retainAll(wordsThatContainSuccessiveLetters);

        return common;
    }

    public List<Entry> getWordsStartingWith(String chars) {

        return restClient.getWordsStartingWith(chars);
    }

    public List<Entry> getWordsEndingWith(String chars) {

        return restClient.getWordsEndingWith(chars);
    }

    public List<Entry> getAllPalindromes() {

        List<Entry> finalCandidates = new ArrayList<>();

        //Instead, we just gather all the words and determine which are the same reversed. Those other functions have a lot going on in behind the scenes anyway.
        for(char abc = 'a'; abc <= 'z'; abc++)
        {
            List<Entry> candidates = restClient.getWordsStartingWith(String.valueOf(abc));

            //Run a loop on each Entry inside candidates. If the reversed word is the same as the item's word, add to a new list.
            for (Entry item : candidates) {
                String reverse = new StringBuilder(item.getWord()).reverse().toString();
                if (item.getWord().equals(reverse))
                    finalCandidates.add(item);
            }
        }

        return finalCandidates;

        /*final List<Entry> candidates = new ArrayList<>();

        // Iterate from a to z
        IntStream.range('a', '{')
                .mapToObj(i -> Character.toString(i))
                .forEach(c -> {

                    // get words starting and ending with character
                    List<Entry> startsWith = restClient.getWordsStartingWith(c);
                    List<Entry> endsWith = restClient.getWordsEndingWith(c);

                    // keep entries that exist in both lists
                    List<Entry> startsAndEndsWith = new ArrayList<>(startsWith);
                    startsAndEndsWith.retainAll(endsWith);

                    // store list with existing entries
                    candidates.addAll(startsAndEndsWith);

                });

        // test each entry for palindrome, sort and return
        return candidates.stream()
                .filter(entry -> {
                    String word = entry.getWord();
                    String reverse = new StringBuilder(word).reverse()
                            .toString();
                    return word.equals(reverse);
                })
                //.sorted() this is not needed because candidates already has a sorted listed. It also throws an exception as it doesn't know how to sort the Entries without defining a comparator
                .collect(Collectors.toList());*/
    }
}
