import parsing.Parser;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();

        while (scanner.hasNextLine()) {
            parser.parseLine(scanner.nextLine());
        }
    }
}
