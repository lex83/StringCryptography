import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Cryptography {
    private final static ArrayList<Character> alphabet = Alphabet.getAlphabet();
    private static final String IN_DATA = "resources\\data.txt";
    private static final String OUT_DATA_ENCRYPTED = "resources\\encrypted.txt";
    private static final String OUT_DATA_DECRYPTED = "resources\\decrypted.txt";
    private static final String IN_DATA_DICTIONARY = "resources\\dictionary.txt";

    public void encrypt(int keyCrypt) {
        String inData = readFile(IN_DATA);
        String outData = getMovingString(keyCrypt, inData);
        fileWrite(outData, OUT_DATA_ENCRYPTED);
    }

    public void decryptToFile(int keyCrypt) {
        String encrypredData = readFile(OUT_DATA_ENCRYPTED);
        String decryptedData = getMovingString(keyCrypt*-1, encrypredData);
        fileWrite(decryptedData, OUT_DATA_DECRYPTED);
    }

    public int bruteForce() {
        String dictionary = readFile(IN_DATA_DICTIONARY).toLowerCase();
        ArrayList<String> wordsDictionary = new ArrayList<>(List.of(dictionary.toLowerCase().split("\r")));
        HashMap<String, Integer> wordsCounter = initializationDictionary(wordsDictionary);

        String encrypted = readFile(OUT_DATA_ENCRYPTED);
        HashMap<Integer, Integer> iterateKeysAndValues = iterateKeysAndCalculateOccurrences(encrypted, wordsCounter);

        return returnKeyMaximumValue(iterateKeysAndValues);
    }

    private String decrypttoString(int keyCrypt, String input) {
        return getMovingString(keyCrypt*-1, input);
    }

    private String getMovingString(int keyCrypt, String inData) {
        StringBuilder outString = new StringBuilder();
        for (char charData : inData.toCharArray()) {
            if (alphabet.contains(charData)) {
                int index = alphabet.indexOf(charData);
                outString.append(moveAlphabet(index, keyCrypt));
            } else {
                outString.append(charData);
            }
        }
        return outString.toString();
    }

    private int checkKeyCrypt(int key) {
        key=key%alphabet.size();
        return key == 0 ? key + 17 : key;
    }

    char moveAlphabet(int start, int key) {
        int indexalphabet = checkKeyCrypt(key);

        if(indexalphabet>=0){
            if(indexalphabet>=alphabet.size()-start){
                indexalphabet=indexalphabet-(alphabet.size()-1-start);
                return alphabet.get(indexalphabet);
            }else {
                return alphabet.get(start+indexalphabet);
            }
        }else {
            indexalphabet*=-1;
            if(indexalphabet>start){
                indexalphabet=alphabet.size()-1-(indexalphabet-start);
                return alphabet.get(indexalphabet);
            }else {
                return alphabet.get(start-indexalphabet);
            }
        }
    }

    private static HashMap<String, Integer> initializationDictionary(ArrayList<String> wordsDictionary) {
        HashMap<String, Integer> wordsDictionaryMap = new HashMap<String, Integer>();
        for (String word : wordsDictionary) {
            wordsDictionaryMap.put(word.toLowerCase(), 0);
        }
        return wordsDictionaryMap;
    }

    private HashMap<Integer, Integer> iterateKeysAndCalculateOccurrences(String encrypted, HashMap<String, Integer> wordsCounter) {
        String decrypted;
        HashMap<Integer, Integer> result = new HashMap<>();
        for (int key = 0; key < alphabet.size(); key++) {
            decrypted = decrypttoString(key, encrypted).substring(0, 1000);
            int valueWord = 0;
            for (String string : wordsCounter.keySet()) {
                valueWord += compareSimilarity(decrypted, string, 3);
            }
            result.put(key, valueWord);
        }
        return result;
    }

    private static void summationOccurrences(HashMap<String, Integer> wordMap, String decrypted) {
        for (String string : wordMap.keySet()) {
            int posicionOccurrence = 0;
            int counterOccurrence = 0;
            while ((posicionOccurrence = decrypted.toLowerCase().indexOf(string, posicionOccurrence)) != -1) {
                counterOccurrence += string.length();
                posicionOccurrence = posicionOccurrence + string.length();
            }

            if (counterOccurrence > 0) {
                wordMap.put(string, counterOccurrence);
            }
        }
    }

    private static int returnKeyMaximumValue(HashMap<Integer, Integer> iterateKeysAndValues) {
        int max = Integer.MIN_VALUE;
        int keyPosition = 0;
        for (Integer key : iterateKeysAndValues.keySet()) {
            if (iterateKeysAndValues.get(key) > max) {
                keyPosition = key;
                max = iterateKeysAndValues.get(key);
            }
        }
        return keyPosition;
    }

    private String readFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fileWrite(String data, String url) {
        try {
            Files.writeString(Path.of(url), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int compareSimilarity(String word1, String wordDictionary, int lenWords) {
        char[] charsWordDictionary = wordDictionary.toLowerCase().toCharArray();
        HashMap<String, Integer> s = new HashMap<>();
        for (int i = 0; i < charsWordDictionary.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(charsWordDictionary[i]);
            for (int j = i + 1; j < charsWordDictionary.length; j++) {
                if (stringBuilder.append(charsWordDictionary[j]).length() >= lenWords) {
                    s.put(stringBuilder.toString(), 0);
                }
            }
        }
        summationOccurrences(s, word1.toLowerCase());
        int result = 0;
        for (Integer value : s.values()) {
            result += value;
        }
        return result;
    }
}
