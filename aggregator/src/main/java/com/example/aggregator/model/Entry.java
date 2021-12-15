package com.example.aggregator.model;

public class Entry {

    private String word;
    private String definition;

    public Entry() {
    }

    public Entry(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Entry)
            return this.word.equals(((Entry) obj).getWord());
        else
            return false;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Entry{");
        sb.append("word='")
          .append(word)
          .append('\'');
        sb.append(", definition='")
          .append(definition)
          .append('\'');
        sb.append('}');
        return sb.toString();
    }
}
