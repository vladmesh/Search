package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private Scanner scanner;

    private Main() {
        scanner = new Scanner(System.in);
    }

    private String[] enterData() throws FileNotFoundException {
        try (Scanner fileScanner = new Scanner(new File("names.txt"))) {
            List<String> books = new LinkedList<>();
            while (fileScanner.hasNextLine()) {
                books.add(fileScanner.nextLine());
            }

            return books.toArray(new String[0]);
        }
    }

    private Map<String, List<Integer>> makeInverseMap(String[] books) {
        Map<String, List<Integer>> words = new HashMap<>();
        String[] tmpArr;
        for (int i = 0; i < books.length; i++) {
            tmpArr = books[i].split(" ");
            for (String word : tmpArr) {
                word = word.toLowerCase();
                if (words.containsKey(word)) {
                    words.get(word).add(i);
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(i);
                    words.put(word, list);
                }
            }
        }
        return words;
    }

    private void findBook(String[] books, Map<String, List<Integer>> words) {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String strategy = scanner.next();
        Finder finder;

        switch (strategy) {
            case "ALL":
                finder = new AllFinder();
                break;
            case "ANY":
                finder = new AnyFinder();
                break;
            case "NONE":
                finder = new NoneFinder();
                break;
            default:
                System.out.println("Incorrect command");
                return;
        }

        System.out.println("\nEnter data to search books:");
        scanner.nextLine();//flush
        String query = scanner.nextLine().toLowerCase().trim();
        String[] result = finder.find(query, words, books);
        System.out.printf("%d books found\n", result.length);
        for (String s : result) {
            System.out.println(s);
        }
    }

    private void printBooks(String[] books) {
        System.out.println("=== List of books ===");
        for (String book : books) {
            System.out.println(book);
        }
    }

    private void startEngine() throws FileNotFoundException {
        String[] books = enterData();
        Map<String, List<Integer>> words = makeInverseMap(books);
        String command;
        do {
            System.out.println("=== Menu ===\n" +
                    "1. Find a book\n" +
                    "2. Print all books\n" +
                    "0. Exit");
            command = scanner.next();
            switch (command) {
                case "1":
                    findBook(books, words);
                    break;
                case "2":
                    printBooks(books);
                    break;
                case "0":
                    System.out.println("Bye");
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");
            }
        } while (!command.equals("0"));

    }

    public static void main(String[] args) throws FileNotFoundException {
        Main engine = new Main();
        engine.startEngine();
    }
}
