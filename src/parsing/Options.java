package parsing;

public enum Options {
    PARSED(true),
    TAUTOLOGY(true),
    CONTRADICTION(true),
    ALL_VALUES(false),
    HARD_FOLDING(false);

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

    public static void printAllOptions() {
        for (Options option : values())
            System.out.println(option + " is set to " + option.isEnabled());

        System.out.println("To change any option just write: " +
                "!options option_name new_value\n");
    }
}
