package ba.unsa.etf.rpr;

public class Rook extends ChessPiece {

    public Rook(String position, Color color) {
        super(position, color);
    }

    @java.lang.Override
    public void move(String position) throws IllegalChessMoveException {
        //top se moze kretati samo vodoravno i uspravno
        position = position.toUpperCase();
        isLegal(position);
        if (position.charAt(0) != this.position.charAt(0) && position.charAt(1) != this.position.charAt(1))
            throw new IllegalChessMoveException("Nedozvoljen potez za topa");
        this.position = position;
    }
}
