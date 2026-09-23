package chess;

import java.util.HashSet;

public class PieceMoves
{
    public static HashSet<ChessMove> pawnMoves(ChessBoard board, ChessPosition startPosition)
    {
        HashSet<ChessMove> possibleMoves = new HashSet<>();
        ChessGame.TeamColor color = board.getPositionAt(startPosition.getRow(), startPosition.getColumn()).getPieceOccupied().getTeamColor();
        int direction = color == ChessGame.TeamColor.WHITE ? 1 : -1;
        ChessPosition nextPosition = board.getPositionAt(startPosition.getRow() + direction, startPosition.getColumn());
        if(board.getOccupationStatus(startPosition, nextPosition) == 0)
            possibleMoves.add(new ChessMove(startPosition, new ChessPosition(startPosition.getRow() + direction, startPosition.getColumn()), null));
        if((color == ChessGame.TeamColor.WHITE && startPosition.getRow() == 2) || (color == ChessGame.TeamColor.BLACK && startPosition.getRow() == 7))
        {
            nextPosition = board.getPositionAt(startPosition.getRow() + (direction * 2), startPosition.getColumn());
            if(board.getOccupationStatus(startPosition, nextPosition) == 0 && possibleMoves.size() == 1) // previous move was possible
                possibleMoves.add(new ChessMove(startPosition, new ChessPosition(startPosition.getRow() + (direction * 2), startPosition.getColumn()), null));
        }

        for(int i = -1; i < 2; i += 2)
        {
            int nextCol = startPosition.getColumn() + i;
            if(nextCol > 0 && nextCol < 9)
            {
                nextPosition = board.getPositionAt(startPosition.getRow() + direction, nextCol);
                if(board.getOccupationStatus(startPosition, nextPosition) == 1)
                    possibleMoves.add(new ChessMove(startPosition, new ChessPosition(nextPosition.getRow() + 1, nextPosition.getColumn() + 1), null));
            }
        }

        for(ChessMove testMove : possibleMoves)
        {
            if(testMove.getEndPosition().getRow() == 8 || testMove.getEndPosition().getRow() == 1)
            {
                ChessPiece.PieceType[] promotionTypes = new ChessPiece.PieceType[4];
                promotionTypes[0] = ChessPiece.PieceType.ROOK;
                promotionTypes[1] = ChessPiece.PieceType.BISHOP;
                promotionTypes[2] = ChessPiece.PieceType.QUEEN;
                promotionTypes[3] = ChessPiece.PieceType.KNIGHT;
                HashSet<ChessMove> promotionMoves = new HashSet<>();
                for(ChessMove move : possibleMoves)
                {
                    for(int i = 0; i < 4; i++)
                    {
                        promotionMoves.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), promotionTypes[i]));
                    }
                }
                return promotionMoves;
            }
        }

        return possibleMoves;
    }

    public static HashSet<ChessMove> kingMoves(ChessBoard board, ChessPosition startPosition)
    {
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        int startRow = startPosition.getRow();
        int startCol = startPosition.getColumn();
        for(int i = -1; i < 2; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                if(i != 0 || j != 0)
                {
                    if(startRow + j < 8 && startRow + j > 0 && startCol + i < 8 && startCol + i > 0)
                    {
                        if (board.getOccupationStatus(startPosition, board.getPositionAt(startRow + j, startCol + i)) < 2)
                        {
                            possibleMoves.add(new ChessMove(startPosition, new ChessPosition(startRow + j, startCol + i), null));
                        }
                    }
                }
            }
        }

        return possibleMoves;
    }

    public static HashSet<ChessMove> rookMoves(ChessBoard board, ChessPosition startPosition)
    {
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        for(int i = -1; i < 2; i += 2)
        {
            int newCol = startPosition.getColumn() + i;
            if(newCol > 0 && newCol < 9) {
                ChessPosition nextPosition = board.getPositionAt(startPosition.getRow(), newCol);
                int status = board.getOccupationStatus(startPosition, nextPosition);
                while (status < 2) {
                    possibleMoves.add(new ChessMove(startPosition, new ChessPosition(nextPosition.getRow() + 1, nextPosition.getColumn() + 1), null));
                    newCol += i;
                    if (status == 1 || (newCol < 1 || newCol > 8))
                        break;
                    nextPosition = board.getPositionAt(startPosition.getRow(), newCol);
                    status = board.getOccupationStatus(startPosition, nextPosition);
                }
            }
        }

        for(int i = -1; i < 2; i += 2)
        {
            int newRow = startPosition.getRow() + i;
            if(newRow > 0 && newRow < 9) {
                ChessPosition nextPosition = board.getPositionAt(newRow, startPosition.getColumn());
                int status = board.getOccupationStatus(startPosition, nextPosition);
                while (status < 2) {
                    possibleMoves.add(new ChessMove(startPosition, new ChessPosition(nextPosition.getRow() + 1, nextPosition.getColumn() + 1), null));
                    newRow += i;
                    if (status == 1 || (newRow < 1 || newRow > 8))
                        break;
                    nextPosition = board.getPositionAt(newRow, startPosition.getColumn());
                    status = board.getOccupationStatus(startPosition, nextPosition);
                }
            }
        }

        return possibleMoves;
    }

    public static HashSet<ChessMove> bishopMoves(ChessBoard board, ChessPosition startPosition)
    {
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        for(int i = 0; i < 4; i++)
        {
            int directionX = i < 2 ? 1 : -1;
            int directionY = i % 2 == 0 ? 1 : -1;
            int newRow = startPosition.getRow() + directionX;
            int newCol = startPosition.getColumn() + directionY;
            while(newRow > 0 && newRow < 9 && newCol > 0 && newCol < 9)
            {
                ChessPosition nextPosition = board.getPositionAt(newRow, newCol);
                int status = board.getOccupationStatus(startPosition, nextPosition);
                if(status < 2)
                {
                    possibleMoves.add(new ChessMove(startPosition, new ChessPosition(newRow, newCol), null));
                    if(status == 1)
                        break;
                }
                else
                    break;
                newRow += directionX;
                newCol += directionY;
            }
        }

        return possibleMoves;
    }

    public static HashSet<ChessMove> knightMoves(ChessBoard board, ChessPosition startPosition)
    {
        HashSet<ChessMove> possibleMoves = new HashSet<>();
        for(int i = -1; i < 2; i+= 2) // vertical
        {
            int newRow = startPosition.getRow() + (i * 2);
            if(newRow > 0 && newRow < 9)
            {
                for (int j = -1; j < 2; j += 2)
                {
                    int newCol = startPosition.getColumn() + j;
                    if (newCol > 0 && newCol < 9)
                    {
                        ChessPosition nextPosition = board.getPositionAt(newRow, newCol);
                        if(board.getOccupationStatus(startPosition, nextPosition) < 2)
                            possibleMoves.add(new ChessMove(startPosition, new ChessPosition(newRow, newCol), null));
                    }
                }
            }
        }

        for(int i = -1; i < 2; i+= 2) // horizontal
        {
            int newCol = startPosition.getColumn() + (i * 2);
            if(newCol > 0 && newCol < 9)
            {
                for (int j = -1; j < 2; j += 2)
                {
                    int newRow = startPosition.getRow() + j;
                    if (newRow > 0 && newRow < 9)
                    {
                        ChessPosition nextPosition = board.getPositionAt(newRow, newCol);
                        if(board.getOccupationStatus(startPosition, nextPosition) < 2)
                            possibleMoves.add(new ChessMove(startPosition, new ChessPosition(newRow, newCol), null));
                    }
                }
            }
        }

        return possibleMoves;
    }
}
