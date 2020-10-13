import parsing.Parser;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome!\n"
                + "Type in your logical expression and get info about it!\n"
                + "You can change options from what will be printed to how " +
                "operations looks like.\n"
                + "To get more information write !help\n");

        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();

        while (scanner.hasNextLine()) {
            parser.parseLine(scanner.nextLine());
        }
    }
}
