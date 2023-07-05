/*
 * A player for testing purposes
 * Copyright 2017 Roger Jaffe
 * All rights reserved
 */

 

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Test player
 */
public class ScottBot extends Player {

    // static int dtest = 2;
    //final int count = 0;
    /**
     * Constructor
     * @param name Player's name
     * @param color Player color: one of Constants.BLACK or Constants.WHITE
     */
    public ScottBot(int color) {

        super(color);

    }


    // public static Board boardff() {
    // static Board b = this.boardf;
    // }
    class MINIMAX {
        Position move;
        Board evalBoard = new Board();
        private final Board boardf;
         int  color;
        MINIMAX(Board board, Position position, int color, int depth, boolean maximizingPlayer){
            this.boardf = board;
            this.color = color;
            
            
            
        }
        
        public Position getMove() {
            return this.move;
        }
        
        Square[][] getBoard(Board board){
            Square[][] squares = new Square[Constants.SIZE][Constants.SIZE];
            for(int i = 0; i < Constants.SIZE; i++) {
                for( int j = 0; j < Constants.SIZE; j++) {
                    squares[i][j] = board.getSquare(new Position(i, j));
                }
            }
            return squares;
        }

        void setBoard(Board copyBoard, Board board) {
            Square[][] original = getBoard(copyBoard);
            for(int i = 0; i < Constants.SIZE; i++) {
                for(int j = 0; j < Constants.SIZE; j++) {
                    board.setSquare(new Player(original[i][j].getStatus()), new Position(i, j));
                }
            }
        }

        public   int minimax( Board board, Position position, int color, int depth, boolean maximizingPlayer) {
            //this.color = color;
            // this.move = position;
            
            Player player = (maximizingPlayer)? new Player(color) : new Player(color*-1);
            ArrayList<Position> possibleMoves = getLegalMoves2(board, player);
            ArrayList<Board> allBoards = new ArrayList<>();
            int eval;
            Board boardb = board;  
            if (depth == 0 || board.noMovesAvailable(player)) {
                    // this.dtest =2;
                    return evaluateBoard(boardf);
                }
            if(maximizingPlayer) {
                int maxEval = Integer.MIN_VALUE;
                
                for(Position child : possibleMoves) {

                    Board childBoard = new Board();
                    setBoard(boardf, childBoard);
                    // childBoard = boardf;

                    childBoard.makeMove(player, child);
                    this.evalBoard = childBoard;
                    MINIMAX a = new MINIMAX(childBoard, child, getColor(), depth-1, false);
                    eval = a.minimax(childBoard, child, this.color, depth-1, false);
                   // eval =  minimax(childBoard, child, getColor(), depth-1, false);

                    maxEval = Math.max(maxEval, eval);
                    //movesAndEval.put(eval, child);
                    if(eval >= maxEval) {
                        this.move = child;

                    }
                }
                // this.move = movesAndEval.get(maxEval);
                //childBoard = board.makeMove(player, move);
                return maxEval;
            }
            else{
                int minEval = Integer.MAX_VALUE;

                for(Position child: possibleMoves) {
                    Board childBoard = new Board();
                    setBoard(boardf, childBoard);
                    //childBoard = boardf;

                    childBoard.makeMove(player, child);
                    this.evalBoard = childBoard;
                    // TestPlayer a = new TestPlayer(getColor());
                    MINIMAX a = new MINIMAX(childBoard, child, getColor(), depth -1, true);
                    
                    eval = a.minimax( childBoard, child, this.color, depth -1, true);

                    minEval = Math.min(minEval, eval);

                }
                return minEval;
            }

        }

        public int evaluateBoard(Board board) {
            int blackPieces = 0;
            int whitePiecess = 0;
            Player a = new Player(this.color);
            for (int row = 0; row < Constants.SIZE; row++) {
                for (int col = 0; col < Constants.SIZE; col++) {
                    if (board.getSquare(new Position( row, col)).getStatus() == Constants.BLACK){
                        blackPieces++;
                    }
                    else if(board.getSquare(new Position( row, col)).getStatus() == Constants.WHITE){
                        whitePiecess++;
                    }
                }

            }

            int cornerBonus = 10;
            if (board.getSquare(new Position( 0, 0)).getStatus() == Constants.BLACK) {
                blackPieces += cornerBonus;
            }
            if (board.getSquare(new Position( 0, 7)).getStatus() == Constants.BLACK) {
                blackPieces += cornerBonus;
            }
            if (board.getSquare(new Position( 7, 0)).getStatus() == Constants.BLACK){
                blackPieces += cornerBonus;
            }
            if (board.getSquare(new Position( 7, 7)).getStatus() == Constants.BLACK) {
                blackPieces += cornerBonus;
            }
            if (board.getSquare(new Position( 0, 0)).getStatus() == Constants.WHITE) {
                whitePiecess += cornerBonus;
            }
            if (board.getSquare(new Position( 0, 7)).getStatus() == Constants.WHITE) {
                whitePiecess += cornerBonus;
            }
            if (board.getSquare(new Position( 7, 0)).getStatus() == Constants.WHITE) {
                whitePiecess += cornerBonus;
            }
            if (board.getSquare(new Position( 7, 7)).getStatus() == Constants.WHITE) {
                whitePiecess += cornerBonus;
            }
            return getColor() == -1? (whitePiecess - blackPieces) : (blackPieces - whitePiecess);
        }
    }

    /**
     *
     * @param board
     * @return The player's next move
     */
    @Override
    public Position getNextMove(Board board) {

        ArrayList<Position> list = this.getLegalMoves(board);

        Position ul = new Position(0,0);
        Position ur = new Position(0,7);
        Position bl = new Position(7,0);
        Position br = new Position(7,7);
        
        /*
        if(isLegalMove(board, new Position(0,0)) && (board.getSquare(this, 0, 0).getStatus() == Constants.EMPTY)){
        return ul;
        }
        else if(isLegalMove(board, new Position(0,7))&& (board.getSquare(this, 0, 7).getStatus() == Constants.EMPTY)){
        return ur;
        }
        else if(isLegalMove(board, new Position(7,0))&& (board.getSquare(this, 7, 0).getStatus() == Constants.EMPTY)){
        return bl;
        }
        else if(isLegalMove(board, new Position(7,7))&& (board.getSquare(this, 7, 7).getStatus() == Constants.EMPTY)){
        return br;
        }
        else   { 
        MINIMAX m = new MINIMAX(  board,null, getColor(), 5, true);
      m.minimax(  board,null, getColor(), 5, true);
        return m.getMove();
        }
         */
        MINIMAX m = new MINIMAX(  board,null, getColor(), 5, true);
      m.minimax(  board,null, getColor(), 5, true);
        return m.getMove();
    }


    /**
     * Is this a legal move?
     * @param player Player asking
     * @param positionToCheck Position of the move being checked
     * @return True if this space is a legal move
     */
    private boolean isLegalMove(Board board, Position positionToCheck) {
        for (String direction : Directions.getDirections()) {
            Position directionVector = Directions.getVector(direction);
            if (step(board, positionToCheck, directionVector, 0)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Traverses the board in the provided direction. Checks the status of
     * each space: 
     * a. If it's the opposing player then we'll move to the next
     *    space to see if there's a blank space
     * b. If it's the same player then this direction doesn't represent
     *    a legal move
     * c. If it's a blank AND if it's not the adjacent square then this
     *    direction is a legal move. Otherwise, it's not.
     * 
     * @param player  Player making the request
     * @param position Position being checked
     * @param direction Direction to move
     * @param count Number of steps we've made so far
     * @return True if we find a legal move
     */
    private boolean step(Board board, Position position, Position direction, int count) {
        Position newPosition = position.translate(direction);
        int color = this.getColor();
        if (newPosition.isOffBoard()) {
            return false;
        } else if (board.getSquare(newPosition).getStatus() == -color) {
            return this.step(board, newPosition, direction, count+1);
        } else if (board.getSquare(newPosition).getStatus() == color) {
            return count > 0;
        } else {
            return false;
        }
    }

    /**
     * Get the legal moves for this player on the board
     * @param board
     * @return True if this is a legal move for the player
     */
    public ArrayList<Position> getLegalMoves(Board board) {
        int color = this.getColor();
        ArrayList list = new ArrayList<>();
        for (int row = 0; row < Constants.SIZE; row++) {
            for (int col = 0; col < Constants.SIZE; col++) {
                if (board.getSquare(this, row, col).getStatus() == Constants.EMPTY) {
                    Position testPosition = new Position(row, col);
                    if (this.isLegalMove(board, testPosition)) {
                        list.add(testPosition);
                    }
                }        
            }
        }
        return list;
    }

    public ArrayList<Position> getLegalMoves2(Board board, Player player) {

        ArrayList list = new ArrayList<>();
        for (int row = 0; row < Constants.SIZE; row++) {
            for (int col = 0; col < Constants.SIZE; col++) {
                if (board.getSquare(player, row, col).getStatus() == Constants.EMPTY) {
                    Position testPosition = new Position(row, col);
                    if (board.isLegalMove(player, testPosition)) {
                        list.add(testPosition);
                    }
                }        
            }
        }
        return list;
    }

}

