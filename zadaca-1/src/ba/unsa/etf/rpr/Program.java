package ba.unsa.etf.rpr;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static ba.unsa.etf.rpr.ChessPiece.Color.BLACK;
import static ba.unsa.etf.rpr.ChessPiece.Color.WHITE;

public class Program {

    public static void main(String[] args) {
        ChessPiece.Color color = WHITE;
        Board b = new Board();

        Scanner in = new Scanner(System.in);
        String input = "e3\n" +
                "c5\n" +
                "f4\n" +
                "d6\n" +
                "Na3\n" +
                "Nf6\n" +
                "Bc4\n" +
                "Qa5\n" +
                "c3\n" +
                "Qc3\n" +
                "c3\n" +
                "Bg4\n" +
                "Qg4\n" +
                "d5\n" +
                "Qc8\n" +
                "X\n";


        System.setIn(new ByteArrayInputStream(input.getBytes()));
        while (true) {

            if (color == WHITE)
                System.out.println("White move: ");
            else
                System.out.println("Black move: ");

            String move = in.nextLine();
            try {
                if (move.length() == 2)
                    b.move(Pawn.class, color, move);
                else if (move.charAt(0) == 'K' || move.charAt(0) == 'k')
                    b.move(King.class, color, move.substring(1));
                else if (move.charAt(0) == 'Q' || move.charAt(0) == 'q')
                    b.move(Queen.class, color, move.substring(1));
                else if (move.charAt(0) == 'R' || move.charAt(0) == 'r')
                    b.move(Rook.class, color, move.substring(1));
                else if (move.charAt(0) == 'B' || move.charAt(0) == 'b')
                    b.move(Bishop.class, color, move.substring(1));
                else if (move.charAt(0) == 'N' || move.charAt(0) == 'n')
                    b.move(Knight.class, color, move.substring(1));
                else if (move.equals("X") || move.equals("x")) {
                    if (color == WHITE)
                        System.out.println("White surrendered");
                    else
                        System.out.println("Black surrendered");
                    break;
                }

                if (color == WHITE)
                    color = BLACK;
                else
                    color = WHITE;

                if ((b.isCheck(WHITE) || b.isCheck(BLACK)))
                    System.out.println("CHECK!!!");
            } catch (IllegalChessMoveException | IllegalArgumentException e) {
                System.out.println("Nedozvoljen potez");
            }
        }
    }
}
