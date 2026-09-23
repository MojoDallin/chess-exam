package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition)
    {
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        switch(type)
        {
            case PAWN -> possibleMoves.addAll(PieceMoves.pawnMoves(board, myPosition));
            case ROOK -> possibleMoves.addAll(PieceMoves.rookMoves(board, myPosition));
            case BISHOP -> possibleMoves.addAll(PieceMoves.bishopMoves(board, myPosition));
            case KING -> possibleMoves.addAll(PieceMoves.kingMoves(board, myPosition));
            case QUEEN ->
            {
                possibleMoves.addAll(PieceMoves.rookMoves(board, myPosition));
                possibleMoves.addAll(PieceMoves.bishopMoves(board, myPosition));
                break;
            }
            case KNIGHT -> possibleMoves.addAll(PieceMoves.knightMoves(board, myPosition));
        }

        return possibleMoves;
    }

    @Override
    public String toString() {
        char returnChar = ' ';
        switch(type)
        {
            case ROOK -> returnChar = 'r';
            case KNIGHT -> returnChar = 'n';
            case BISHOP -> returnChar = 'b';
            case KING -> returnChar = 'k';
            case QUEEN -> returnChar = 'q';
            case PAWN -> returnChar = 'p';
        }
        return pieceColor == ChessGame.TeamColor.WHITE ? String.valueOf(returnChar).toUpperCase() : String.valueOf(returnChar);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}
