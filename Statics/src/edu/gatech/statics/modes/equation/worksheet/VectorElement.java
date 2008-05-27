package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.math.Vector;

class VectorElement extends TermElement {

    VectorElement(Vector source) {
        super();
        this.source = source;
    }
    final Vector source;

    String getText() {
        return source.toString();
    }

    boolean isKnown() {
        return source.isKnown();
    }
}
