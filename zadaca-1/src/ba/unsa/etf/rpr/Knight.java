package ba.unsa.etf.rpr;

public class Knight extends ChessPiece {

    public Knight(String position, Color color) {
        super(position, color);
    }

    @java.lang.Override
    public void move(String position) throws IllegalChessMoveException {
        //konj se krece u obliku slova L
        position = position.toUpperCase();
        isLegal(position);
        int razmak1 = Math.abs(this.position.charAt(0) - position.charAt(0));
        int razmak2 = Math.abs(this.position.charAt(1) - position.charAt(1));
        if (razmak1 == 1 && razmak2 == 2 || razmak1 == 2 && razmak2 == 1)
            this.position = position;
        else
            throw new IllegalChessMoveException("Nedozvoljen potez za konja");
    }
}
