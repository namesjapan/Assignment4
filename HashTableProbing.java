public class HashTableProbing {
    private String[] table;
    private static final int M_PROBING = 20000;

    public HashTableProbing() {
        table = new String[M_PROBING];
    }

    private int customHash(String s, boolean useNewHash) {
        int hash = 0;
        if (useNewHash) {
            for (int i = 0; i < s.length(); i++) {
                hash = (hash * 31) + s.charAt(i);
            }
        } else {
            int skip = Math.max(1, s.length() / 8);
            for (int i = 0; i < s.length(); i += skip) {
                hash = (hash * 37) + s.charAt(i);
            }
        }
        return Math.abs(hash % M_PROBING);
    }

    public void insert(String key, boolean useNewHash) {
        int index = customHash(key, useNewHash);
        while (table[index] != null) {
            index = (index + 1) % M_PROBING;
        }
        table[index] = key;
    }

    public boolean search(String key, boolean useNewHash, int[] cost) {
        int index = customHash(key, useNewHash);
        while (table[index] != null) {
            cost[0]++;
            if (table[index].equals(key)) {
                return true;
            }
            index = (index + 1) % M_PROBING;
        }
        return false;
    }
}