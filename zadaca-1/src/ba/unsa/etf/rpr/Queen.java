package ba.unsa.etf.rpr;

public class Queen extends ChessPiece {

    public Queen(String position, Color color) {
        super(position, color);
    }

    @java.lang.Override
    public void move(String position) throws IllegalChessMoveException {
        //kraljica se moze kretati u pravo i dijagonalno
        position = position.toUpperCase();
        isLegal(position);
        int razmak1 = Math.abs(this.position.charAt(0) - position.charAt(0));
        int razmak2 = Math.abs(this.position.charAt(1) - position.charAt(1));
        if (razmak1 != razmak2 && (razmak1 != 0 && razmak2 != 0))
            throw new IllegalChessMoveException("Nedozvoljen potez za kraljicu");
        this.position = position;
    }
}
