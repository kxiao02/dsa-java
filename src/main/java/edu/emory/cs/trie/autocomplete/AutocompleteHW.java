
package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.Trie;
import edu.emory.cs.trie.TrieNode;

import java.util.*;

public class AutocompleteHW extends Autocomplete<String> {
    private final Trie<String> pickTrie = new Trie<>();
    private final int max = getMax();
    private String pickedString = null;

    public AutocompleteHW(String dict_file, int max) {
        super(dict_file, max);
    }

    @Override
    public List<String> getCandidates(String prefix) {
        int count = 0;
        List<String> candidates = new ArrayList<>();
        prefix = prefix.trim();
        TrieNode<String> node = prefixEnd(prefix);
        if (node == null) return candidates;
        TrieNode<String> pickedNode = pickTrie.find(prefix);
        if (pickedNode != null) {
            pickedString = pickedNode.getValue();
            if (pickedString != null) {
                find(pickedString).setEndState(false);
                count++;
            }
        }
        // Handle case where prefix is a valid candidate
        if (count < max && node.isEndState()) {
            candidates.add(prefix);
            count++;
        }
        node.setValue(prefix);
        BFS(count, candidates, node);
        candidates = toOrderedList(candidates);
        return candidates;
    }

    private void BFS(int count, List<String> candidates, TrieNode<String> node) {
        String parentVal = node.getValue();
        String childVal;
        int currLayer = parentVal.length();
        int prevLayer = currLayer;
        StringBuilder candidateTmp = new StringBuilder(parentVal);
        Queue<TrieNode<String>> queue = new ArrayDeque<>();
        queue.offer(node);
        // The major loop remove parent node from queue head
        while (!queue.isEmpty()) {
            TrieNode<String> parentNode = queue.poll();
            parentVal = parentNode.getValue();
            currLayer = parentVal.length();
            // Make sure the last layer is completely traversed
            if (currLayer > prevLayer)
                if (count >= max) return;
            // Initialize SB
            candidateTmp.setLength(0);
            candidateTmp.append(parentVal);
            Map<Character, TrieNode<String>> childrenMap = parentNode.getChildrenMap();
            if (childrenMap != null && !childrenMap.isEmpty()) {
                // Minor loop appends the children to queue tail
                Collection<TrieNode<String>> children = childrenMap.values();
                for (TrieNode<String> child : children) {
                    childVal = candidateTmp.append(child.getKey()).toString();
                    child.setValue(childVal);
                    queue.offer(child);
                    if (child.isEndState()) {
                        candidates.add(childVal);
                        count++;
                    }
                    // initialize SB
                    candidateTmp.setLength(0);
                    candidateTmp.append(parentVal);
                }
            }
            prevLayer = currLayer;
        }
    }

    private List<String> toOrderedList(List<String> candidates) {
        int size = candidates.size();
        // Deals with extremely small max or size
        if (size < 2) {
            if (pickedString != null) {
                candidates.add(0, pickedString);
                find(pickedString).setEndState(true);
            }
            return candidates;
        }
        size = Math.max(size, max);
        String[] sortingArray = new String[size];
        sortingArray = candidates.toArray(sortingArray);
        candidates = new ArrayList<>();
        int startIdx = 0;
        int endIdx = 1;
        while (endIdx < size - 1) {
            if (sortingArray[endIdx].length() < sortingArray[++endIdx].length()) {
                Arrays.sort(sortingArray, startIdx, endIdx);
                startIdx = endIdx;
            }
        }
        if (startIdx < endIdx) Arrays.sort(sortingArray, startIdx, endIdx + 1);
        for (int i = 0; i < size; i++) {
            if (sortingArray[i] == null) break;
            candidates.add(sortingArray[i]);
        }
        if (pickedString != null) {
            candidates.add(0, pickedString);
            find(pickedString).setEndState(true);
        }
        return candidates.subList(0, size);
    }

    // Return the node of the last character of the prefix as the root for bfs
    private TrieNode<String> prefixEnd(String prefix) {
        if (prefix.equals("")) return null;
        TrieNode<String> node = getRoot();
        for (char c : prefix.toCharArray()) {
            node = node.getChild(c);
            if (node == null) return null;
        }
        return node;
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {
        prefix = prefix.trim();
        candidate = candidate.trim();
        // Empty string is illegal
        if (prefix.equals("") || candidate.equals("")) return;
        TrieNode<String> prefixNode = pickTrie.find(prefix);
        TrieNode<String> candidateNode = find(candidate);
        if (candidateNode == null) put(candidate, null);
        if (prefixNode != null) prefixNode.setValue(candidate);
        else pickTrie.put(prefix, candidate);
    }
}





