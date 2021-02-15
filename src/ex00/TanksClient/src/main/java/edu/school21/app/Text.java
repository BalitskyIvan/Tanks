package edu.school21.app;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;

public class Text extends TextInputControl {
    private String hits;
    private String shots;
    private String misses;

    public Text(Content content, String hits, String shots, String misses) {
        super(content);
        this.hits = hits;
        this.shots = shots;
        this.misses = misses;
    }


}
