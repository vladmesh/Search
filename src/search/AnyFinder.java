package search;

import java.util.*;

public class AnyFinder implements Finder {

    @Override
    public String[] find(String query, Map<String, List<Integer>> words, String[] books) {
        String[] queryWords = query.split(" ");
        if (queryWords.length == 0 || query.length() == 0){
            return new String[0];
        }
        List<String> res = new ArrayList<>();
        Set<Integer> resSet = new HashSet<>();
        for (int i = 0; i < queryWords.length; i++) {
            if(words.containsKey(queryWords[i])) {
                resSet.addAll(words.get(queryWords[i]));
            }
        }
        for (Integer number : resSet) {
            res.add(books[number]);
        }
        return res.toArray(new String[0]);
    }
}
