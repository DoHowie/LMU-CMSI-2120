import java.util.ArrayList;
import java.util.List;

public class WordFinder {
    private static class Node {
        char character;
        boolean last;
        Node left;
        Node right;
        Node middle;

        public Node(char character) {
            this.character = character;
            this.last = false;
            this.left = null;
            this.middle = null;
            this.right = null;
        }

        void add(String word) {
            var first = word.charAt(0);
            var rest = word.substring(1);

            if (first < character) {
                if (left == null) {
                    left = new Node(first);
                }
                left.add(word);
            }

            else if (first > character) {
                if (right == null) {
                    right = new Node(first);
                }
                right.add(word);
            }

            else if (rest.isEmpty()) {
                last = true;
            }

            else {
                if (middle == null) {
                    middle = new Node(rest.charAt(0));
                }
                middle.add(rest);
            }
        }

        void accumulateWords(String prefix, List<String> words) {
            if (left != null) {
                left.accumulateWords(prefix, words);
            }

            if (last) {
                words.add(prefix + character);
            }

            if (middle != null) {
                middle.accumulateWords(prefix + character, words);
            }

            if (right != null) {
                right.accumulateWords(prefix, words);
            }
        }

        Node nodeFor(String word) {
            var first = word.charAt(0);
            var rest = word.substring(1);

            if (word.isEmpty()) {
                return this;
            }

            if (first < character) {
                if (left == null) {
                    return null;
                }
                return left.nodeFor(word);
            }

            if (first > character) {
                if (right == null) {
                    return null;
                }
                return right.nodeFor(word);
            }

            if (rest.isEmpty()) {
                return this;
            }

            if (middle == null) {
                return null;
            }

            return middle.nodeFor(rest);
        }
    }

    Node root = new Node('j');

    public void add(String word) {
        word = word.trim();
        if (word.isEmpty()) {
            throw new IllegalArgumentException("Empty word is not allowed");
        }
        root.add(word);
    }

    List<String> allWords() {
        var words = new ArrayList<String>();
        root.accumulateWords("", words);

        return words;
    }

    public boolean contains(String word) {
        word = word.trim();
        var node = root.nodeFor(word);

        return node != null && node.last;
    }

    public List<String> suggestions(String prefix) {
        prefix = prefix.trim();

        if (prefix.isEmpty()) {
            throw new IllegalArgumentException("Need at least one character for suggestions");
        }

        var words = new ArrayList<String>();
        var node = root.nodeFor(prefix);

        if (node != null) {
            if (node.last) {
                words.add(prefix);
            }

            if (node.middle != null) {
                node.middle.accumulateWords(prefix, words);
            }
        }

        return words;
    }
}