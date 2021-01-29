package ba.unsa.etf.rpr;

import static ba.unsa.etf.rpr.ChessPiece.Color.BLACK;
import static ba.unsa.etf.rpr.ChessPiece.Color.WHITE;

public class Pawn extends ChessPiece {

    public Pawn(String position, Color color) {
        super(position, color);
    }

    @java.lang.Override
    public void move(String position) throws IllegalChessMoveException {
        position = position.toUpperCase();
        isLegal(position);
        int razmak1 = Math.abs(this.position.charAt(0) - position.charAt(0));
        int razmak2 = Math.abs(this.position.charAt(1) - position.charAt(1));
        if (razmak1 > 1) throw new IllegalChessMoveException("Nedozvoljen potez za pijuna");
        if (razmak2 > 2)
            throw new IllegalChessMoveException("Nedozvoljen potez za pijuna");
        if (razmak2 == 2) {
            if (razmak1 != 0) throw new IllegalChessMoveException("Nedozvoljen potez za pijuna");
            else {
                if (color == WHITE) {
                    if (this.position.charAt(1) != '2')
                        throw new IllegalChessMoveException("Nedozvoljen potez za pijuna");
                } else if (color == BLACK) {
                    if (this.position.charAt(1) != '7')
                        throw new IllegalChessMoveException("Nedozvoljen potez za pijuna");
                }
            }
        }
        if (position.charAt(1) < this.position.charAt(1) && color == WHITE)
            throw new IllegalChessMoveException("Nedozvoljen potez za pijuna");
        if (position.charAt(1) > this.position.charAt(1) && color == BLACK)
            throw new IllegalChessMoveException("Nedozvoljen potez za pijuna");
        this.position = position;
    }
}
