/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Set;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */

public class GraphPoet {

    private final Graph<String> graph = Graph.empty();

    /**
     * Create a new poet with the graph from corpus (as described above).
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {      
        try {
            List<String> lines = Files.readAllLines(corpus.toPath());

            // Process lines to build the word affinity graph
            for (String line : lines) {
                String[] words = line.split("\\s+");
                for (int i = 0; i < words.length - 1; i++) {
                    String currentWord = words[i].toLowerCase();
                    String nextWord = words[i + 1].toLowerCase();
                    graph.add(currentWord);
                    graph.add(nextWord);
                    int weight = graph.set(currentWord, nextWord, graph.targets(currentWord).getOrDefault(nextWord, 0) + 1);
                    if (weight == 0) {
                        // Increment the weight of the existing edge
                        graph.set(currentWord, nextWord, weight + 1);
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException("Error reading the corpus file", e);
        }
    }
    

    /**
     * Generate a poem.
     *
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String[] inputWords = input.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < inputWords.length - 1; i++) {
            String currentWord = inputWords[i].toLowerCase();
            String nextWord = inputWords[i + 1].toLowerCase();
            result.append(currentWord).append(" ");
            String bridgeWord = findBridgeWord(currentWord, nextWord);
            result.append(bridgeWord).append(" ");
        }
        result.append(inputWords[inputWords.length - 1]);

        return result.toString();
    }

    private String findBridgeWord(String currentWord, String nextWord) {
        Map<String, Integer> targets = graph.targets(currentWord);
        Set<String> commonTargets = targets.keySet();
        commonTargets.retainAll(graph.sources(nextWord).keySet());

        if (!commonTargets.isEmpty()) {
            int maxWeight = 0;
            String bridgeWord = "";
            for (String commonTarget : commonTargets) {
                int weight = targets.get(commonTarget);
                if (weight > maxWeight) {
                    maxWeight = weight;
                    bridgeWord = commonTarget;
                }
            }
            return bridgeWord;
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        return graph.toString();
    }
}