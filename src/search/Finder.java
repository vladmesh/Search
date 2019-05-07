package search;

import java.util.*;

public interface Finder {
    String[] find(String query, Map<String, List<Integer>> words, String[] books);
}
