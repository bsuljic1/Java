package ba.unsa.etf.rpr;

public class Bishop extends ChessPiece {

    public Bishop(String position, Color color) {
        super(position, color);
    }

    @java.lang.Override
    public void move(String position) throws IllegalChessMoveException {
        //lovac se moze kretati samo dijagonalno
        position = position.toUpperCase();
        isLegal(position);
        int razmak1 = Math.abs(position.charAt(0) - this.position.charAt(0));
        int razmak2 = Math.abs(position.charAt(1) - this.position.charAt(1));
        if (razmak1 != razmak2)
            throw new IllegalChessMoveException("Nedozvoljen potez za lovca");
        this.position = position;
    }
}
