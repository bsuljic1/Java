package ba.unsa.etf.rpr;

public abstract class ChessPiece {
    public enum Color {BLACK, WHITE}

    protected Color color;
    protected String position;

    public ChessPiece(String position, Color color) {
        isLegal(position);
        this.position = position;
        this.color = color;
    }

    public void setPosition(String position) {
        this.position = position.toUpperCase();
    }

    public String getPosition() {
        return position;
    }

    public abstract void move(String position) throws IllegalChessMoveException;

    public Color getColor() {
        return color;
    }

    public void isLegal(String position) {
        if (position.equals("")) throw new IllegalArgumentException("Nedozvoljena pozicija");
        if (!(position.charAt(0) >= 'A' && position.charAt(0) <= 'H') && !(position.charAt(0) >= 'a' && position.charAt(0) <= 'h'))
            throw new IllegalArgumentException("Nedozvoljena pozicija");
        if (!(position.charAt(1) >= '1' && position.charAt(1) <= '8'))
            throw new IllegalArgumentException("Nedozvoljena pozicija");
    }
}
