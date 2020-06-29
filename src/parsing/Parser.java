package parsing;

public class Parser {

    private int line = 0;

    private void errorMessage() {
        System.out.println("Error in line " + line);
    }

    public void parseLine(String string) {
        line++;
    }
}
