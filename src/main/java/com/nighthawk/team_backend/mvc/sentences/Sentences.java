package com.nighthawk.team_backend.mvc.sentences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sentences {
    // Key instance variables
    private final String expression;
    private ArrayList<String> tokens;
    private Long characters = 0l;
    private Integer len;
    private Long characterSpace;
    private Long words = 0l;

    // Helper definition for supported operators
    private final Map<String, Integer> SEPARATORS = new HashMap<>();
    {
        // Map<"separator", not_used>
        SEPARATORS.put(" ", 0);
    }

    // Test if token is an separator
    private boolean isSeparator(String token) {
        // find the token in the hash map
        return SEPARATORS.containsKey(token);
    }

    // Create a 1 argument constructor expecting a mathematical expression
    public Sentences(String expression) {
        // original input
        this.expression = expression;

        len = this.expression.length();
        characterSpace = Long.valueOf(len);

        // parse expression into terms
        this.termTokenizer();

        // calculate words
        this.tokensToResult();
    }

    private void termTokenizer(){
        // contains final list of tokens
        this.tokens = new ArrayList<>();

        int start = 0;  // term split starting index
        StringBuilder multiCharTerm = new StringBuilder();    // term holder
        for (int i = 0; i < this.expression.length(); i++) {
            Character c = this.expression.charAt(i);
            if (isSeparator(c.toString())) {
                // 1st check for working term and add if it exists
                if (multiCharTerm.length() > 0) {
                    tokens.add(this.expression.substring(start, i));
                }
                // Add operator or parenthesis term to list
                if (c != ' ') {
                    tokens.add(c.toString());
                }
                // Get ready for next term
                start = i + 1;
                multiCharTerm = new StringBuilder();
            } else {
                // multi character terms: numbers, functions, perhaps non-supported elements
                // Add next character to working term
                multiCharTerm.append(c);
            }
        }
        // Add last term
        if (multiCharTerm.length() > 0) {
            tokens.add(this.expression.substring(start));
        }
    }

    private void tokensToResult(){
        for(String token : this.tokens){
            for(int i = 0; i < token.length(); i++){
                characters++;
            }
            words++;
        }
    }

    // Print the expression, terms, and result
    public String toString() {
        return ("Original expression: " + this.expression + "\n" +
                "Tokenized expression: " + this.tokens.toString() + "\n" +
                "Final result: " + "\n" +
                "Words: " + this.words + "\n" + 
                "Characters: " + this.characters + "\n" + 
                "Characters including spaces: " + this.characterSpace + "\n");
    }

    public String apiToString(){
        return String.format("{\"Original\": %s, \"Words\": %s, \"Characters\": %s, \"Spaces\": %s}", this.expression, this.words, this.characters, this.characterSpace);
    }

    public static void main(String[] args) {
        Sentences sen = new Sentences("Hi! My name is Bob.");
        System.out.println(sen.toString());

        // 69 words, 445 characters including spaces, 377 exclugin
        Sentences sen2 = new Sentences("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        System.out.println(sen2.toString());
    }
    
}
