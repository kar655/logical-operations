package parsing;

public class ParserError extends Error {

    public ParserError(String line) {
        super(line);
    }
}
