import java.io.*;
import java.util.Scanner;

public class TextAnalyzer2 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String para = ""; // Paragraph text to work with

        // Prompt user to choose between typing or loading from a file
        System.out.println("Welcome to Text Analyzer Tool");

        System.out.println("Enter the paragraph in English:");
        para = input.nextLine();

        // Main menu
        while (true) {
            System.out.println("\nPlease select an option:");
            System.out.println("1 - Vowel and Consonant Count");
            System.out.println("2 - Keyword Finder");
            System.out.println("3 - Letter Counter");
            System.out.println("4 - Estimate the Reading Time");
            System.out.println("5 - Word Frequency Analysis");
            System.out.println("6 - Palindrome Finder");
            System.out.println("7 - Save Results to File");
            System.out.println("8 - User Authentication System");
            System.out.println("9 - Exit");

            int choice = input.nextInt();
            input.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    VowelConsonantCount(para);
                    break;
                case 2:
                    KeyWordFinder(para, input);
                    break;
                case 3:
                    letterCount(para);
                    break;
                case 4:
                    calculateReadingTime(para);
                    break;
                case 5:
                    wordFrequency(para);
                    break;
                case 6:
                    findPalindromes(para);
                    break;
                case 7:
                    saveResultsToFile(para, input); // Updated method to save to user-defined file
                    break;
                case 8:
                    userAuthentication();
                    break;
                case 9:
                    System.out.println("Exiting the program. Goodbye!");
                    input.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        
    }

    public static void VowelConsonantCount(String para) {
        String noSpace = para.replaceAll("\\s", "");
        int vowelCount = 0;
        int consonantCount = 0;

        for (int i = 0; i < noSpace.length(); i++) {
            char currentChar = Character.toLowerCase(noSpace.charAt(i));

            if ("aeiou".indexOf(currentChar) != -1) {
                vowelCount++;
            } else if (currentChar >= 'a' && currentChar <= 'z') {
                consonantCount++;
            }
        }

        System.out.println("The number of vowels in the paragraph: " + vowelCount);
        System.out.println("The number of consonants in the paragraph: " + consonantCount);
    }

    public static void KeyWordFinder(String para, Scanner input) {
        System.out.println("Enter the word you want to check and type (exit) to end:");

        while (true) {
            String word = input.nextLine();
            if (word.equalsIgnoreCase("exit")) {
                break;
            }

            int countOfWord = 0;
            String[] words = para.split("\\s+");

            for (String w : words) {
                if (w.equalsIgnoreCase(word)) {
                    countOfWord++;
                }
            }

            if (countOfWord > 0) {
                System.out.println("The word \"" + word + "\" occurs " + countOfWord + " time(s).");
            } else {
                System.out.println("The word \"" + word + "\" does not occur in the paragraph.");
            }
        }
    }

    public static void letterCount(String para) {
        String noSpace = para.replaceAll("\\s", "").toLowerCase();
        int[] letterCounts = new int[26];

        for (int i = 0; i < noSpace.length(); i++) {
            char currentChar = noSpace.charAt(i);
            if (currentChar >= 'a' && currentChar <= 'z') {
                letterCounts[currentChar - 'a']++;
            }
        }

        for (int i = 0; i < letterCounts.length; i++) {
            if (letterCounts[i] > 0) {
                System.out.println((char) (i + 'a') + ": " + letterCounts[i]);
            }
        }
    }

    public static void calculateReadingTime(String para) {
        int wordCount = para.split("\\s+").length;
        int readingTimeInMinutes = (int) Math.ceil(wordCount / 250.0);

        System.out.println("Estimated reading time: " + readingTimeInMinutes + " minute(s)");
    }

    public static void wordFrequency(String para) {
        String[] words = para.split("\\s+");
        String[][] wordFrequency = new String[words.length][2];

        try {
            for (int i = 0; i < words.length; i++) {
                String word = words[i].toLowerCase().replaceAll("[^a-z]", "");
                int frequency = 1;

                for (int j = 0; j < i; j++) {
                    if (wordFrequency[j][0] != null && wordFrequency[j][0].equals(word)) {
                        frequency = Integer.parseInt(wordFrequency[j][1]) + 1;
                        wordFrequency[j][1] = Integer.toString(frequency);
                        word = null;
                        break;
                    }
                }

                if (word != null) {
                    wordFrequency[i][0] = word;
                    wordFrequency[i][1] = "1";
                }
            }

            System.out.println("Word Frequency:");
            for (String[] wf : wordFrequency) {
                if (wf[0] != null) {
                    System.out.println(wf[0] + ": " + wf[1]);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while analyzing word frequency: " + e.getMessage());
        }
    }

    public static boolean isPalindrome(String word, int start, int end) {
        if (start >= end) return true;
        if (word.charAt(start) != word.charAt(end)) return false;
        return isPalindrome(word, start + 1, end - 1);
    }

    public static void findPalindromes(String para) {
        String[] words = para.split("\\s+");
        System.out.println("Palindromes in the paragraph:");

        for (String word : words) {
            String cleanWord = word.toLowerCase().replaceAll("[^a-z]", "");
            if (cleanWord.length() > 1 && isPalindrome(cleanWord, 0, cleanWord.length() - 1)) {
                System.out.println(word);
            }
        }
    }

    // Updated method to save results to a user-defined file
    public static void saveResultsToFile(String para, Scanner input) {
        String result = "";

        // Collect results from cases 1, 3, 4, 5, and 6
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(output);
        
        // Redirect output to the print stream
        System.setOut(printStream);
        
        VowelConsonantCount(para);
        result += output.toString();
        output.reset();

        letterCount(para);
        result += output.toString();
        output.reset();

        calculateReadingTime(para);
        result += output.toString();
        output.reset();

        wordFrequency(para);
        result += output.toString();
        output.reset();

        findPalindromes(para);
        result += output.toString();

        // Ask the user for a file name
        System.out.println("Enter the name of the file to save the results (without extension):");
        String fileName = input.nextLine().trim();

        // Construct the file path on the user's desktop
        String filePath = System.getProperty("user.home") + "/Desktop/" + fileName + ".txt"; // Path to Desktop
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(result);
            System.out.println("Results saved to: " + filePath);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void userAuthentication() {
        String[][] users = {
            {"admin", "password123"},
            {"user1", "mypassword"},
            {"user2", "123456"}
        };

        Scanner input = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = input.nextLine();
        System.out.println("Enter password:");
        String password = input.nextLine();

        boolean isAuthenticated = false;
        for (String[] user : users) {
            if (user[0].equals(username) && user[1].equals(password)) {
                isAuthenticated = true;
                break;
            }
        }

        if (isAuthenticated) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
        input.close();
    }
}
