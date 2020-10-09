package parsing;

public enum Options {
    PARSED(true),
    TAUTOLOGY(true),
    CONTRADICTION(true),
    ALL_VALUES(false);

    private boolean enabled;

    Options(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
