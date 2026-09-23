package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private final ChessPosition[][] boardPositions = new ChessPosition[8][8];

    public ChessBoard() {
        for(int i = 0; i < 64; i++)
            boardPositions[i/8][i%8] = new ChessPosition(i/8, i%8);
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        getPositionAt(position.getRow(), position.getColumn()).setPieceOccupied(piece);
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return getPositionAt(position.getRow(), position.getColumn()).getPieceOccupied();
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard()
    {
        for(int i = 0; i < 64; i++)
        {
            boardPositions[i/8][i%8].setPieceOccupied(null); // clear pieces
        }
        for(int i = 0; i < 2; i++)
        {
            ChessGame.TeamColor color = i == 0 ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;
            int rowToSet = i == 0 ? 0 : 7;

            ChessPiece rook = new ChessPiece(color, ChessPiece.PieceType.ROOK);
            ChessPiece knight = new ChessPiece(color, ChessPiece.PieceType.KNIGHT);
            ChessPiece bishop = new ChessPiece(color, ChessPiece.PieceType.BISHOP);
            ChessPiece pawn = new ChessPiece(color, ChessPiece.PieceType.PAWN);

            boardPositions[rowToSet][0].setPieceOccupied(rook);
            boardPositions[rowToSet][1].setPieceOccupied(knight);
            boardPositions[rowToSet][2].setPieceOccupied(bishop);
            boardPositions[rowToSet][3].setPieceOccupied(new ChessPiece(color, ChessPiece.PieceType.QUEEN));
            boardPositions[rowToSet][4].setPieceOccupied(new ChessPiece(color, ChessPiece.PieceType.KING));
            boardPositions[rowToSet][5].setPieceOccupied(bishop);
            boardPositions[rowToSet][6].setPieceOccupied(knight);
            boardPositions[rowToSet][7].setPieceOccupied(rook);

            rowToSet = rowToSet == 0 ? 1 : 6;
            for(int j = 0; j < 8; j++)
            {
                boardPositions[rowToSet][j].setPieceOccupied(pawn);
            }

        }
    }

    public ChessPosition getPositionAt(int row, int col)
    {
        return boardPositions[row - 1][col - 1];
    }

    public int getOccupationStatus(ChessPosition curPosition, ChessPosition newPosition)
    {
        if(newPosition.getPieceOccupied() == null)
            return 0; // no piece is at the new position
        if(newPosition.getPieceOccupied().getTeamColor() != boardPositions[curPosition.getRow() - 1][curPosition.getColumn() - 1].getPieceOccupied().getTeamColor())
            return 1; // opposite piece is there, so can move there but stop after wards
        return 2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(boardPositions, that.boardPositions);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(boardPositions);
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "boardPositions=" + Arrays.toString(boardPositions) +
                '}';
    }
}
