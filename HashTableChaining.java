public class HashTableChaining {
    private Node[] table;
    private static final int M_CHAINING = 1000;

    public HashTableChaining() {
        table = new Node[M_CHAINING];
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
        return Math.abs(hash % M_CHAINING);
    }

    public void insert(String key, int value, boolean useNewHash) {
        int index = customHash(key, useNewHash);
        Node newNode = new Node(key, value);
        newNode.next = table[index];
        table[index] = newNode;
    }

    public boolean search(String key, boolean useNewHash, int[] cost) {
        int index = customHash(key, useNewHash);
        Node current = table[index];
        while (current != null) {
            cost[0]++;
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}