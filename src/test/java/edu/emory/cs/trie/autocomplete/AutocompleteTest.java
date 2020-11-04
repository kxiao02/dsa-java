
package edu.emory.cs.trie.autocomplete;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AutocompleteTest {
    static class Eval {
        int correct = 0;
        int total = 0;
    }

    @Test
    public void test() {
        String[] dictCase = new String[] {
                "src/main/resources/dict.txt",
                "src/main/resources/dictTest.txt",
                "src/main/resources/dictSmall.txt",
                "src/main/resources/dictTestOrdered.txt",
                "src/main/resources/dictSmallOrdered.txt",
        };
        final int caseNumber = 1;
        final String prefix = "su";
        final int max = 1;
        List<String> expected = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dictCase[caseNumber + 2])));
            String line;
            int count = 0;

            while ((line = reader.readLine()) != null && count < max) {
                expected.add(line);
                count++;
            }
        } catch (IOException ignored) {}

        Autocomplete<?> ac = new AutocompleteHW(dictCase[caseNumber], max);
        Autocomplete<?> acec = new AutocompleteHWExtra(dictCase[caseNumber], max);
        Eval eval = new Eval();
//        testAutocomplete(ac, eval, expected, prefix);
        testAutocompleteEC(acec, eval, expected, prefix);
    }

    private void testAutocomplete(Autocomplete<?> ac, Eval eval, List<String> expected, String prefix) {

        testGetCandidates(ac, eval, prefix, expected);//0

        testGetCandidates(ac, eval, prefix, expected);//1

        testGetCandidates(ac, eval, "superdecrease", expected);//2

        ac.pickCandidate(prefix, "summoner");
        testGetCandidates(ac, eval, prefix, expected);//3

        testGetCandidates(ac, eval, prefix, expected);//4

        ac.pickCandidate("supererogation", "LMAO");
        ac.pickCandidate("su", "XD");
        testGetCandidates(ac, eval, "supererogation", expected);//5

        ac.pickCandidate(prefix, "POG");//6
        testGetCandidates(ac, eval, prefix, expected);
        testGetCandidates(ac, eval, prefix, expected);//7

        testGetCandidates(ac, eval, prefix, expected);//8
        testGetCandidates(ac, eval, "su", expected);//9

        ac.pickCandidate(prefix, " ()");
        ac.pickCandidate(prefix, "  ");
        testGetCandidates(ac, eval, prefix, expected);//10
        testGetCandidates(ac, eval, "su", expected);//11

        System.out.printf("Score: %d/%d\n", eval.correct, eval.total);
    }

    private void testAutocompleteEC(Autocomplete<?> ac, Eval eval, List<String> expected, String prefix) {
        testGetCandidates(ac, eval, prefix, expected);//0

        testGetCandidates(ac, eval, prefix, expected);//1

        testGetCandidates(ac, eval, "superdecrease", expected);//2

        ac.pickCandidate(prefix, "summoner");
        ac.pickCandidate(prefix, "summoner");
        ac.pickCandidate(prefix, "sommelier");
        ac.pickCandidate(prefix, "sommelier");
        ac.pickCandidate(prefix, "sommelier");
        ac.pickCandidate(prefix, "sommelier");
        testGetCandidates(ac, eval, prefix, expected);//3
        testGetCandidates(ac, eval, prefix, expected);//4

        ac.pickCandidate("supererogation", "LMAO");
        ac.pickCandidate("supererogation", "LMAO");
        ac.pickCandidate("supererogation", "LMAO");
        ac.pickCandidate("supererogation", "LMFAO");
        ac.pickCandidate("supererogation", "LMFAO");
        ac.pickCandidate("supererogation", "LMFAO");
        ac.pickCandidate("su", "XD");
        ac.pickCandidate("su", "XD");
        ac.pickCandidate("su", "XD");
        ac.pickCandidate("su", "XD");
        ac.pickCandidate("su", "XD!!!!");
        ac.pickCandidate("su", "XD!!!!");
        testGetCandidates(ac, eval, "supererogation", expected);//5

        ac.pickCandidate(prefix, "POG");//6
        testGetCandidates(ac, eval, "su", expected);
        testGetCandidates(ac, eval, prefix, expected);//7

        testGetCandidates(ac, eval, prefix, expected);//8
        testGetCandidates(ac, eval, "su", expected);//9

        ac.pickCandidate("su", " []");
        ac.pickCandidate("su", " []");
        ac.pickCandidate("su", " []");
        ac.pickCandidate("su", " []");
        ac.pickCandidate("su", " ()");
        ac.pickCandidate("su", "  ");
        testGetCandidates(ac, eval, prefix, expected);//10
        testGetCandidates(ac, eval, "su", expected);//11

        System.out.printf("Score: %d/%d\n", eval.correct, eval.total);
    }

    private void testGetCandidates(Autocomplete<?> ac, Eval eval, String prefix, List<String> expected) {
        String log = String.format("%2d: ", eval.total);
        eval.total++;

        try {
            List<String> candidates = ac.getCandidates(prefix);

            if (expected.equals(candidates)) {
                eval.correct++;
                log += "PASS";
            }
            else
                log += String.format("FAIL -> expected = %s " +
                        "\n            returned = %s", expected, candidates);
        }
        catch (Exception e) {
            log += "ERROR";
        }

        System.out.println(log);
    }
}
