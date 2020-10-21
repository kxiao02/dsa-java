
package edu.emory.cs.trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TrieQuiz extends Trie<Integer> {
    /**
     * PRE: this trie contains all country names as keys and their unique IDs as values
     * (e.g., this.get("United States") -> 0, this.get("South Korea") -> 1).
     * @param input the input string in plain text
     *              (e.g., "I was born in South Korea and raised in the United States").
     * @return the list of entities (e.g., [Entity(14, 25, 1), Entity(44, 57, 0)]).
     */
    List<Entity> getEntities(String input) {
        TrieNode<Integer> node;
        List<Entity> output = new ArrayList<>();
        char[] keyArr = input.toCharArray();
        int size = keyArr.length;
        int currIndex;
        int endIndexHold;

        for (int i = 0; i < size; i++) {
            if (Character.isUpperCase(keyArr[i])) {
                node = getRoot();
                currIndex = i;
                endIndexHold = i + 1;
                while (currIndex < size) {
                    node = node.getChild(keyArr[currIndex++]);
                    if (node == null) {
                        i = endIndexHold - 1;
                        break;
                    }
                    if (node.isEndState()) {
                        output.add(new Entity(i, currIndex, node.getValue()));
                        endIndexHold = currIndex;
                        System.out.println(output);
                    }
                }
            }
        }

        return output;
    }
//
//    public static void testGetEntities() {
//        final List<String> L = List.of("Costa rica", "Japan", "Dominica", "Chile", "China", "United States", "South Korea", "Dominican Republic");
//        TrieQuiz trie = new TrieQuiz();
//        for (int i = 0; i < L.size(); i++)
//            trie.put(L.get(i), i);
//
//        String input = "Costa ricaJJapan   DDFFFF123Dominican RepublicCChileCChinaUnited South Korea";
//        // [(0, 10, 0), (11, 16, 1), (22, 30, 2), (22, 40, 7), (41, 46, 3), (47, 52, 4), (59, 70, 6)]
//        // [(11, 16, 1), (0, 10, 0), (28, 46, 7), (28, 36, 2), (47, 52, 3), (53, 58, 4)]
//        // [(11, 16, 1), (65, 76, 6), (0, 10, 0), (28, 46, 7), (28, 36, 2), (47, 52, 3), (53, 58, 4)]
//        Set<String> actual = trie.getEntities(input).stream().map(Entity::toString).collect(Collectors.toSet());
//        System.out.println(actual);
//    }
//
//    public static void main(String[] args) {
//        testGetEntities();
//    }
}
