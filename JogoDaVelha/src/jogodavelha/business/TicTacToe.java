/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jogodavelha.business;

import java.util.logging.Level;
import java.util.logging.Logger;
import jogodavelha.exceptions.InvalidPositionException;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class TicTacToe {
    
    public static String PLAYER_1 = "X";
    public static String PLAYER_2 = "O";
    public static String EMPTY = "";
    
    public static String PLAYER_1_WIN_OPTION = "XXX";
    public static String PLAYER_2_WIN_OPTION = "OOO";
    
    private static int MTX_SIZE = 3;
    
    private String[][] board = new String[][]{
        {"","",""},
        {"","",""},
        {"","",""}
    };
    
    private String turn = PLAYER_1;
    
    private String winner;
    
    public TicTacToe() {
        
    }
    
    public String[][] getBoard() {
        return this.board;
    }
    
    public String getWinner() {
        return this.winner;
    }
    
    private boolean checkRow() {
        
        for(String[] row : this.board) {
            
            boolean isFull = false;
            
            if( !row[0].equalsIgnoreCase(EMPTY) || !row[1].equalsIgnoreCase(EMPTY) || !row[2].equalsIgnoreCase(EMPTY) ) {
                if( (row[0].equalsIgnoreCase(row[1]) && row[1].equalsIgnoreCase(row[2])) ) {
                    return true;
                }
            }
            
        }
        
        return false;
    }
    
    private boolean checkColum() {
        
        for( int i=0; i < MTX_SIZE; i++ ) {
            
            String columCount = "";
            for( int j=0; j < MTX_SIZE; j++ ) {
                columCount += board[j][i];
            }
            if( columCount.equalsIgnoreCase(PLAYER_1_WIN_OPTION) || columCount.equals(PLAYER_2_WIN_OPTION) ) 
                return true;
        }
        
        return false;
    }
    
    private boolean checkDiagonals() {
        
        if( (board[0][0] + board[1][1] + board[2][2]).equalsIgnoreCase(PLAYER_1_WIN_OPTION) || (board[0][0] + board[1][1] + board[2][2]).equalsIgnoreCase(PLAYER_2_WIN_OPTION) ) {
            return true;
        }
        else if( (board[0][2] + board[1][1] + board[2][0]).equalsIgnoreCase(PLAYER_1_WIN_OPTION) || (board[0][2] + board[1][1] + board[2][0]).equalsIgnoreCase(PLAYER_2_WIN_OPTION) ) {
            return true;
        }
        
        return false;
    }
    
    public boolean isGameOver() {
        
        for(String[] row : board)
            for(String col : row)
                if(col.equalsIgnoreCase(EMPTY))
                    return false;
        
        return true;
    }
    
    public boolean play(int row, int col) throws InvalidPositionException {
        
        if ( !this.board[row][col].equalsIgnoreCase(EMPTY) ) {
            throw new InvalidPositionException();
        }
        
        this.board[row][col] = this.turn;
        
            boolean hasWinner = checkRow() || checkColum() || checkDiagonals();
        
        if( hasWinner ) {
            this.winner = turn;
        }
        
        if (turn.equalsIgnoreCase(PLAYER_1))
            turn = PLAYER_2;
        else
            turn = PLAYER_1;
        
        return hasWinner;
    }

}
