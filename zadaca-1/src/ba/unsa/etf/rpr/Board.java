package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ba.unsa.etf.rpr.ChessPiece.Color.BLACK;
import static ba.unsa.etf.rpr.ChessPiece.Color.WHITE;

public class Board {
    private List<ChessPiece> figure = new ArrayList<>(Arrays.asList(new Rook("A1", WHITE), new Knight("B1", WHITE), new Bishop("C1", WHITE),
            new Queen("D1", WHITE), new King("E1", WHITE), new Bishop("F1", WHITE),
            new Knight("G1", WHITE), new Rook("H1", WHITE), new Pawn("A2", WHITE), new Pawn("B2", WHITE),
            new Pawn("C2", WHITE), new Pawn("D2", WHITE), new Pawn("E2", WHITE), new Pawn("F2", WHITE),
            new Pawn("G2", WHITE), new Pawn("H2", WHITE),
            new Rook("A8", BLACK), new Knight("B8", BLACK), new Bishop("C8", BLACK),
            new Queen("D8", BLACK), new King("E8", BLACK), new Bishop("F8", BLACK),
            new Knight("G8", BLACK), new Rook("H8", BLACK), new Pawn("A7", BLACK), new Pawn("B7", BLACK),
            new Pawn("C7", BLACK), new Pawn("D7", BLACK), new Pawn("E7", BLACK), new Pawn("F7", BLACK),
            new Pawn("G7", BLACK), new Pawn("H7", BLACK)));


    public Board() {
    }

    //metoda koja provjerava da li se na datoj poziciji nalazi figura
    boolean IsThereAFigure(String position) {
        for (ChessPiece piece : figure)
            if (piece.getPosition().equals(position))
                return true;
        return false;
    }

    //metoda koja provjerava da li figura preskace neku drugu na putu
    private void tryJump(ChessPiece chessPiece, String figure_position, String position, ChessPiece.Color color) throws IllegalChessMoveException {
        int razmak1 = Math.abs(figure_position.charAt(0) - position.charAt(0));
        int razmak2 = Math.abs(figure_position.charAt(1) - position.charAt(1));

        if (chessPiece instanceof Queen) {
            if (figure_position.charAt(1) == position.charAt(1)) { // ide horizontalno
                if (figure_position.charAt(0) < position.charAt(0))
                    for (int i = 1; i < razmak1; i++) { // ide horizontalno desno
                        String pom2 = figure_position.replace(figure_position.charAt(0), (char) (figure_position.charAt(0) + i));
                        boolean is = IsThereAFigure(pom2);
                        if (is) throw new IllegalChessMoveException("Ne moze preskakati drugu figuru");
                    }
                else
                    for (int i = 1; i < razmak1; i++) { // ide horizontalno lijevo
                        String pom2 = figure_position.replace(figure_position.charAt(0), (char) (figure_position.charAt(0) - i));
                        if (IsThereAFigure(pom2))
                            throw new IllegalChessMoveException("Ne moze preskakati drugu figuru");
                    }
            }

            if (figure_position.charAt(0) == position.charAt(0)) { // ide vertikalno
                if (figure_position.charAt(1) < position.charAt(1))
                    for (int i = 1; i < razmak2; i++) { // ide vertikalno gore
                        String pom2 = figure_position.replace(figure_position.charAt(1), (char) (figure_position.charAt(1) + i));
                        if (IsThereAFigure(pom2))
                            throw new IllegalChessMoveException("Ne moze preskakati drugu figuru");
                    }
                else
                    for (int i = 1; i < razmak2; i++) { // ide vertikalno dolje
                        String pom2 = figure_position.replace(figure_position.charAt(1), (char) (figure_position.charAt(1) - i));
                        if (IsThereAFigure(pom2))
                            throw new IllegalChessMoveException("Ne moze preskakati drugu figuru");
                    }
            }

            if (razmak1 == razmak2) { // ide dijagonalno
                if (figure_position.charAt(0) > position.charAt(0)) { // ide dijagonalno lijevo
                    if (figure_position.charAt(1) > position.charAt(1)) { // ide dijagonalno lijevo dolje
                        for (int i = 1; i < razmak1; i++) {
                            String pom2 = figure_position.replace(figure_position.charAt(0), (char) (figure_position.charAt(0) - i));
                            String pom3 = pom2.replace(pom2.charAt(1), (char) (pom2.charAt(1) - i));
                            if (IsThereAFigure(pom3))
                                throw new IllegalChessMoveException("Ne moze preskakati drugu figuru");
                        }
                    } else { // ide dijagonalno lijevo gore
                        for (int i = 1; i < razmak1; i++) {
                            String pom2 = figure_position.replace(figure_position.charAt(0), (char) (figure_position.charAt(0) - i));
                            String pom3 = pom2.replace(pom2.charAt(1), (char) (pom2.charAt(1) + i));
                            if (IsThereAFigure(pom3))
                                throw new IllegalChessMoveException("Ne moze preskakati drugu figuru");
                        }
                    }
                } else { //ide dijagonalno desno
                    if (figure_position.charAt(1) > position.charAt(1)) { // ide dijagonalno desno dolje
                        for (int i = 1; i < razmak1; i++) {
                            String pom2 = figure_position.replace(figure_position.charAt(0), (char) (figure_position.charAt(0) + i));
                            String pom3 = pom2.replace(pom2.charAt(1), (char) (pom2.charAt(1) - i));
                            if (IsThereAFigure(pom3))
                                throw new IllegalChessMoveException("Ne moze preskakati drugu figuru");
                        }
                    } else { // ide dijagonalno desno gore
                        for (int i = 1; i < razmak1; i++) {
                            String pom2 = figure_position.replace(figure_position.charAt(0), (char) (figure_position.charAt(0) + i));
                            String pom3 = pom2.replace(pom2.charAt(1), (char) (pom2.charAt(1) + i));
                            if (IsThereAFigure(pom3))
                                throw new IllegalChessMoveException("Ne moze preskakati drugu figuru");
                        }
                    }
                }
            }

        } else if (chessPiece instanceof Pawn) {
            //Ovdje je to trivijalno, samo jedna pozicija za provjeriti, i to samo ako ide 2 mjesta unaprijed(razmak2 == 2)
            if (razmak2 == 2 && razmak1 == 0) {
                String pom2;
                if (color == WHITE)
                    pom2 = figure_position.replace(figure_position.charAt(1), (char) (figure_position.charAt(1) + 1));
                else
                    pom2 = figure_position.replace(figure_position.charAt(1), (char) (figure_position.charAt(1) - 1));
                if (IsThereAFigure(pom2)) throw new IllegalChessMoveException("Ne moze preskakati drugu figuru");
            }
        } else if (chessPiece instanceof Bishop) {
            //Ovdje je od onih slucajeva sa kraljivom samo dijagonalni
            if (razmak1 == razmak2) { // ide dijagonalno
                if (figure_position.charAt(0) > position.charAt(0)) { // ide dijagonalno lijevo
                    if (figure_position.charAt(1) > position.charAt(1)) { // ide dijagonalno lijevo dolje
                        for (int i = 1; i < razmak1; i++) {
                            String pom2 = figure_position.replace(figure_position.charAt(0), (char) (figure_position.charAt(0) - i));
                            String pom3 = pom2.replace(pom2.charAt(1), (char) (pom2.charAt(1) - i));
                            if (IsThereAFigure(pom3))
                                throw new IllegalChessMoveException("Ne moze preskakati drugu figuru");
                        }
                    } else { // ide dijagonalno lijevo gore
                        for (int i = 1; i < razmak1; i++) {
                            String pom2 = figure_position.replace(figure_position.charAt(0), (char) (figure_position.charAt(0) - i));
                            String pom3 = pom2.replace(pom2.charAt(1), (char) (pom2.charAt(1) + i));
                            if (IsThereAFigure(pom3))
                                throw new IllegalChessMoveException("Ne moze preskakati drugu figuru");
                        }
                    }
                } else { //ide dijagonalno desno
                    if (figure_position.charAt(1) > position.charAt(1)) { // ide dijagonalno desno dolje
                        for (int i = 1; i < razmak1; i++) {
                            String pom2 = figure_position.replace(figure_position.charAt(0), (char) (figure_position.charAt(0) + i));
                            String pom3 = pom2.replace(pom2.charAt(1), (char) (pom2.charAt(1) - i));
                            if (IsThereAFigure(pom3))
                                throw new IllegalChessMoveException("Ne moze preskakati drugu figuru");
                        }
                    } else { // ide dijagonalno desno gore
                        for (int i = 1; i < razmak1; i++) {
                            String pom2 = figure_position.replace(figure_position.charAt(0), (char) (figure_position.charAt(0) + i));
                            String pom3 = pom2.replace(pom2.charAt(1), (char) (pom2.charAt(1) + i));
                            if (IsThereAFigure(pom3))
                                throw new IllegalChessMoveException("Ne moze preskakati drugu figuru");
                        }
                    }
                }
            }

        } else if (chessPiece instanceof Rook) { // ovdje je horizontalni i verikalni slucaj od kraljice
            boolean can = false;
            if (figure_position.charAt(1) == position.charAt(1)) { // ide horizontalno
                if (figure_position.charAt(0) < position.charAt(0))
                    for (int i = 1; i < razmak1; i++) { // ide horizontalno desno
                        String pom2 = figure_position.replace(figure_position.charAt(0), (char) (figure_position.charAt(0) + i));
                        if (IsThereAFigure(pom2))
                            throw new IllegalChessMoveException("Ne moze preskakati drugu figuru");
                    }
                else
                    for (int i = 1; i < razmak1; i++) { // ide horizontalno lijevo
                        String pom2 = figure_position.replace(figure_position.charAt(0), (char) (figure_position.charAt(0) - i));
                        if (IsThereAFigure(pom2))
                            throw new IllegalChessMoveException("Ne moze preskakati drugu figuru");
                    }
            }

            if (figure_position.charAt(0) == position.charAt(0)) { // ide vertikalno
                if (figure_position.charAt(1) < position.charAt(1))
                    for (int i = 1; i < razmak2; i++) { // ide vertikalno gore
                        String pom2 = figure_position.replace(figure_position.charAt(1), (char) (figure_position.charAt(1) + i));
                        if (IsThereAFigure(pom2))
                            throw new IllegalChessMoveException("Ne moze preskakati drugu figuru");
                    }
                else
                    for (int i = 1; i < razmak2; i++) { // ide vertikalno dolje
                        String pom2 = figure_position.replace(figure_position.charAt(1), (char) (figure_position.charAt(1) - i));
                        if (IsThereAFigure(pom2))
                            throw new IllegalChessMoveException("Ne moze preskakati drugu figuru");
                    }
            }
        }
    }

    public void move(Class<? extends ChessPiece> type, ChessPiece.Color color, String position) throws
            IllegalChessMoveException {
        ChessPiece figuraZaPojesti = null;
        for (ChessPiece piece : figure) { // trazimo da li se na position vec nalazi neka figura
            if (piece.getPosition().equals(position))
                if (piece.getColor() == color) throw new IllegalChessMoveException("Tu se nalazi figura iste boje");
                else {
                    figuraZaPojesti = piece;
                    break;
                }
        }

        for (ChessPiece piece : figure) {
            if (type.isAssignableFrom(piece.getClass()) && piece.getColor() == color) {
                tryJump(piece, piece.getPosition(), position, piece.getColor());

                try {
                    //Ako nema sta pojesti, pijun moze praviti problem
                    if (type == Pawn.class && figuraZaPojesti == null) {
                        //Ako ide dijagonalno, baci izuzeta
                        int r1 = Math.abs(piece.getPosition().charAt(0) - position.charAt(0));
                        int r2 = Math.abs(piece.getPosition().charAt(1) - position.charAt(1));
                        if (r1 == r2) {
                            throw new IllegalChessMoveException("Pijun ne moze ici dijagonalno, osim ako jede");
                        }
                    }
                    piece.move(position);
                    if (figuraZaPojesti != null)
                        figure.remove(figuraZaPojesti);
                    return;
                } catch (Exception e) {
                }

            }
        }

        throw new IllegalChessMoveException("Ni za jednu figuru potez nije legalan");
    }

    void move(String oldPosition, String newPosition) throws IllegalChessMoveException {
        ChessPiece figuraZaPojesti = null;
        ChessPiece figuraZaPremjestiti = null;
        for (ChessPiece piece : figure)
            if (piece.getPosition().equals(oldPosition))
                figuraZaPremjestiti = piece;

        for (ChessPiece piece : figure) { // trazimo da li se na position vec nalazi neka figura
            if (piece.getPosition().equals(newPosition))
                if (figuraZaPremjestiti != null && piece.getColor() == figuraZaPremjestiti.getColor())
                    throw new IllegalChessMoveException("Tu se nalazi figura iste boje");
                else {
                    figuraZaPojesti = piece;
                    break;
                }
        }


        for (ChessPiece piece : figure)
            if (piece.getPosition().equals(oldPosition)) {
                tryJump(piece, piece.getPosition(), newPosition, piece.getColor());
                //Ako nema sta pojesti, pijun moze praviti problem
                try {
                    if (piece instanceof Pawn && figuraZaPojesti == null) {
                        //Ako ide dijagonalno, baci izuzeta
                        int r1 = Math.abs(piece.getPosition().charAt(0) - newPosition.charAt(0));
                        int r2 = Math.abs(piece.getPosition().charAt(1) - newPosition.charAt(1));
                        if (r1 == r2) {
                            throw new IllegalChessMoveException("Pijun ne moze ici dijagonalno, osim ako jede");
                        }
                    }
                    piece.move(newPosition);
                    if (figuraZaPojesti != null)
                        figure.remove(figuraZaPojesti);
                    return;
                } catch (Exception e) {
                }

            }
        throw new IllegalArgumentException("Nijedna figura se ne nalazi na oldPosition");
    }

    public boolean isCheck(ChessPiece.Color color) {
        ChessPiece king = null;
        for (ChessPiece piece : figure) {
            if (piece instanceof King && piece.getColor() == color)
                king = piece;
        }
        if (king != null) {
            for (ChessPiece piece : figure) {
                if (piece.getColor() != color) {
                    try {
                        String position = piece.getPosition();
                        move(position, king.getPosition());
                        piece.setPosition(position);
                        return true;
                    } catch (Exception e) {
                    }
                }
            }
        }
        return false;
    }
}
