
package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.Trie;
import edu.emory.cs.trie.TrieNode;

import java.util.*;
import java.util.stream.Collectors;

public class AutocompleteHW_v2 extends Autocomplete<String> {
    private final Trie<List<Deque<String>>> pickDequeTrie = new Trie<>();
    private final int max = getMax();

    public AutocompleteHW_v2(String dict_file, int max) {
        super(dict_file, max);
    }

    @Override
    public List<String> getCandidates(String prefix) {
        int count = 0;
        List<String> candidates = new ArrayList<>();
        prefix = prefix.trim();
        TrieNode<String> node = prefixEnd(prefix);
        if (node == null) return candidates;
        TrieNode<List<Deque<String>>> pickedNode = pickDequeTrie.find(prefix);

        // Handle case where prefix is a valid candidate
        if (count < max && node.isEndState()) {
            candidates.add(prefix);
            count++;
        }

        node.setValue(prefix);
        BFS(count, candidates, node);
        candidates = toOrderedList(pickedNode, candidates);
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

    private List<String> toOrderedList(TrieNode<List<Deque<String>>> pickedNode, List<String> candidates) {
        List<Deque<String>> frequencyList = null;
        List<String> candidatesHold = null;
        int size = candidates.size();
        String[] sortingArray = new String[size];
        if (pickedNode != null) {
            frequencyList = pickedNode.getValue();
            if (frequencyList != null) {
                frequencyList = pickedNode.getValue();
                candidatesHold = addToOutput(frequencyList);
            }
        }

        if (size < 2) {
            if (frequencyList != null) {
                size += candidatesHold.size();
                candidatesHold.addAll(candidates);
                return candidatesHold.subList(0, Math.min(size, max));
            }
            return candidates;
        }

//        size = Math.min(size, max);
//        TODO ⬆️This line of code is being moved to L122
        sortingArray = candidates.toArray(sortingArray);
        candidates = new ArrayList<>();
        int startIdx = 0;
        int endIdx = 1;

        while (endIdx < size - 1) {
            if (sortingArray[endIdx].length() < sortingArray[endIdx + 1].length()) {
                Arrays.sort(sortingArray, startIdx, endIdx);
                startIdx = endIdx;
            }
            endIdx++;
        }

        if (startIdx < endIdx) Arrays.sort(sortingArray, startIdx, endIdx + 1);
        for (int i = 0; i < size; i++) {
            if (sortingArray[i] == null) break;
            candidates.add(sortingArray[i]);
        }

        size = Math.min(size, max);
        //        TODO ⬆️This line of code is from L103
        if (frequencyList != null) {
            candidatesHold.addAll(candidates);
            removeDuplicate(candidatesHold);
            return candidatesHold.subList(0, size);
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
        Deque<String> candidateDeque = new ArrayDeque<>();
        List<Deque<String>> frequencyList = new ArrayList<>();
        TrieNode<List<Deque<String>>> prefixNode = pickDequeTrie.find(prefix);
        TrieNode<String> candidateNode = find(candidate);

        // If candidate does not exist in the vocab trie
        if (candidateNode == null) put(candidate, null);

        // If the prefix is being selected before
        if (prefixNode != null) {
            frequencyList = prefixNode.getValue();
            // If the frequency group list has elements in it
            if (frequencyList != null) {
                int size = frequencyList.size();
                int idx = searchFrequencyGroup(frequencyList, candidate);
                // If this is not a new frequency, push to the corresponding group
                if (idx < size) frequencyList.get(idx).push(candidate);
                else {
                    candidateDeque.push(candidate);
                    frequencyList.add(candidateDeque);
                }
                // This is the first time being assigned a candidate
            } else {
                candidateDeque.push(candidate);
                frequencyList = new ArrayList<>();
                frequencyList.add(candidateDeque);
                pickDequeTrie.put(prefix, frequencyList);
            }
        }
        // If the prefix is being selected for the first time
        else {
            candidateDeque.push(candidate);
            frequencyList.add(candidateDeque);
            pickDequeTrie.put(prefix, frequencyList);
        }
    }

    private static void removeDuplicate(List<String> candidates) {
        LinkedHashSet<String> set = new LinkedHashSet<>(candidates.size());
        set.addAll(candidates);
        candidates.clear();
        candidates.addAll(set);
    }

    private List<String> addToOutput (List<Deque<String>> frequencyList) {
        String candidate;
        Deque<String> fGroup;
        List<String> output = new ArrayList<>();
        // make a deep copy of the frequency group list
        List<Deque<String>> deepCopy = frequencyList.stream().map(ArrayDeque::new).collect(Collectors.toList());
        ListIterator<Deque<String>> fGroupIterator = deepCopy.listIterator(deepCopy.size());
        while (fGroupIterator.hasPrevious()) {
            fGroup = fGroupIterator.previous();

            while (!fGroup.isEmpty()) {
                candidate = fGroup.pop();
                output.add(candidate);
            }
        }
        return output;
    }

    // Find the group the candidate is currently in
    private int searchFrequencyGroup(List<Deque<String>> frequencyList, String candidate) {
        int frequencyIdx = 0;
        for (Deque<String> deque: frequencyList) {
            // If found in any deque, remove the string and push it into the next group
            // ONLY ONE of each candidate can exist in the entire list
            if (deque.contains(candidate)) {
//                frequencyIdx = frequencyList.indexOf(deque) + 1;
                // ToDo ⬆️Only this line of code is removed
                deque.remove(candidate);
                return frequencyIdx;
            }
        }
        return frequencyIdx;
    }
}