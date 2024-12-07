import java.io.*;
public class PasswordChecker {
    public static boolean isStrongPassword(String password, HashTableChaining chaining, HashTableProbing probing, boolean useNewHash, int[] costChaining, int[] costProbing) {
        costChaining[0] = 0;
        costProbing[0] = 0;

        if (password.length() < 8) {
            return false;
        }
        if (chaining.search(password, useNewHash, costChaining) || probing.search(password, useNewHash, costProbing)) {
            return false;
        }
        for (int i = 0; i <= 9; i++) {
            String testPassword = password + i;
            if (chaining.search(testPassword, useNewHash, costChaining) || probing.search(testPassword, useNewHash, costProbing)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("wordlist.txt"));
        HashTableChaining chaining = new HashTableChaining();
        HashTableProbing probing = new HashTableProbing();
        String line;
        int lineNumber = 1;

        while ((line = reader.readLine()) != null) {
            chaining.insert(line.trim(), lineNumber, false);
            probing.insert(line.trim(), false);
            lineNumber++;
        }
        reader.close();

        String[] passwords = {"account8", "accountability", "9a$D#qW7!uX&Lv3zT", "B@k45*W!c$Y7#zR9P", "X$8vQ!mW#3Dz&Yr4K5"};
        int[] costChaining = new int[1];
        int[] costProbing = new int[1];

        for (String password : passwords) {
            boolean strongOld = isStrongPassword(password, chaining, probing, false, costChaining, costProbing);
            System.out.println("Password: " + password);
            System.out.println("Strong (old hash): " + strongOld);
            System.out.println("Search cost (chaining old hash): " + costChaining[0]);
            System.out.println("Search cost (probing, old hash): " + costProbing[0]);

            boolean strongNew = isStrongPassword(password, chaining, probing, true, costChaining, costProbing);
            System.out.println("Strong (new hash): " + strongNew);
            System.out.println("Search cost (chaining, new hash): " + costChaining[0]);
            System.out.println("Search cost (probing, new hash): " + costProbing[0]);
            System.out.println();
        }
    }
}