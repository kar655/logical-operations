import parsing.Parser;

import java.util.Scanner;

/**
 * dwa rodzaje rzeczy
 * wyrazenia czyli zmienne lub zmienne polaczone operacjami
 * i ustawienie (takie srodowisko / wartosciowanie niektorych zmiennych)
 *
 *
 * -----------------------------------------------------------
 * moze w expression jakis iterator po stanach
 * -----------------------------------------------------------
 * moze jakies when false i return HashMapz danym wartosciowaniem
 */


/**
 * moze jakas mozliwosc wlaczenia mocnego sprawdzania ze
 * po dodawaniu czego kolwiek jest sprawdzane
 * czy to jest tautologia / contradiction i zastepowanie T / F
 */

/**
 *
 */

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();

        while (scanner.hasNextLine()) {
            parser.parseLine(scanner.nextLine());
        }
    }
}
