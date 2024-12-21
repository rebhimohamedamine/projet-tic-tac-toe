package projet_test_mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class tictactoe_test {

    private tictactoe ticTacToe;
    private GererDB mockDbManager;
    private TicTacToeSave saveManager;

    @BeforeEach
    public void before() throws Exception {
         mockDbManager = mock(GererDB.class); // Mock the database
         saveManager = new TicTacToeSave(mockDbManager); // Pass it to TicTacToeSave
        ticTacToe = new tictactoe(saveManager); // Inject the saveManager into tictactoe
    }

    @Test
    public void whenXOutsideBoardThenRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ticTacToe.play(5, 2);
        });
        assertEquals("X is outside board", exception.getMessage());
    }
    
    @Test
    public void whenYOutsideBoardThenRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ticTacToe.play(2, 5);
        });
        assertEquals("Y is outside board", exception.getMessage());
    }
    
    @Test
    public void whenOccupiedThenRuntimeException() {
        ticTacToe.play(2, 1);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ticTacToe.play(2, 1);
        });
        assertEquals("Occupied Case", exception.getMessage());
    }

    @Test
    public void whenGameStartsNextPlayerIsX() {
        assertEquals('X', ticTacToe.nextPlayer());
    }
    
    @Test
    public void whenLastTurnPlayedByXnextTurnPlayedbyO() {
    	ticTacToe.play(2,1);
    	assertEquals('O',ticTacToe.nextPlayer());
   
    }
    
    @Test
    public void whenLastTurnPlayedByOnextTurnPlayedbyX() {
    	ticTacToe.play(2,1);
    	ticTacToe.play(1, 1);  	   
    	assertEquals('X',ticTacToe.nextPlayer()); 
    }
    
    @Test
    public void whenPlayThenGameStillOn() {
        String gameStatus = ticTacToe.play(1, 1);
        assertEquals("Game still on ", gameStatus);
    }
    
    @Test
    public void whenAllHorizontalPawnsThenWinner()
    {    String gameStatus = ticTacToe.play(1, 1);
                 gameStatus = ticTacToe.play(2, 1);
                 gameStatus = ticTacToe.play(1, 2);
                 gameStatus = ticTacToe.play(2, 2);
                 gameStatus = ticTacToe.play(1, 3);
         
                 assertEquals("X is the Winner", gameStatus);        
    	
    }
    
    @Test
    public void whenAllDiagonalPawnsThenWinner() {
        String gameStatus = ticTacToe.play(1, 1); // X plays
        gameStatus = ticTacToe.play(1, 2);       // O
        gameStatus = ticTacToe.play(2, 2);       
        gameStatus = ticTacToe.play(2, 1);       
        gameStatus = ticTacToe.play(3, 3);       

        assertEquals("X is the Winner", gameStatus);
    }

    
    @Test
    public void whenFirstDiagonalPawnsThenWinner()
    {String gameStatus = ticTacToe.play(1, 1);
            gameStatus = ticTacToe.play(1, 3);
            gameStatus = ticTacToe.play(2, 2);
            gameStatus = ticTacToe.play(2, 3);
            gameStatus = ticTacToe.play(3,3);
     assertEquals("X is the Winner", gameStatus); 
            
    	
    }
     
    @Test
    public void whenSecondDiagonalPawnsThenWinner() {
        String gameStatus = ticTacToe.play(1, 3); // X plays
        gameStatus = ticTacToe.play(1, 1);       // O plays
        gameStatus = ticTacToe.play(2, 2);       // X plays
        gameStatus = ticTacToe.play(2, 1);       // O plays
        gameStatus = ticTacToe.play(3, 1);       // X plays

        assertEquals("X is the Winner", gameStatus);
    }
    
    @Test
    public void whenAllCellsAreFullThenItsTie() {
  
    	 ticTacToe.play(1, 1);
    	 ticTacToe.play(1, 2);
    	 ticTacToe.play(1, 3);
    	 ticTacToe.play(2, 1);
    	 ticTacToe.play(2, 3);
    	 ticTacToe.play(2, 2);
    	 ticTacToe.play(3, 1);
    	 ticTacToe.play(3, 3);
    	 String gameStatus = ticTacToe.play(3, 2);
    	 
    	 assertEquals("There is a Tie ", gameStatus);
    }
    
    
    @Test
    void testDatabaseInitializationOnNewGame() throws Exception {
        // Stub void methods to prevent exceptions
        doNothing().when(mockDbManager).drop();
        doNothing().when(mockDbManager).verify();
        doNothing().when(mockDbManager).connect();

        // Verify the interactions with the mock
        verify(mockDbManager, times(1)).drop();
        verify(mockDbManager, times(1)).verify();
        verify(mockDbManager, times(1)).connect();
    }
    
    
    @Test
    void testDataSavedOnEachMove() throws Exception {
        doNothing().when(mockDbManager).save(any(Data.class));  
        ticTacToe.play(1, 1);  // Move 1 by 'X'
        ticTacToe.play(1, 2);  // Move 2 by 'O'
        ticTacToe.play(2, 1);  // Move 3 by 'X
        verify(mockDbManager, times(3)).save(any(Data.class));  
        Data expectedData1 = new Data(1, 1, 1, 'X');
        verify(mockDbManager).save(argThat(data -> data.getTurn() == expectedData1.getTurn() &&
                                                    data.getX() == expectedData1.getX() &&
                                                    data.getY() == expectedData1.getY() &&
                                                    data.getPlayer() == expectedData1.getPlayer()));
        // Second move: 'O' on (1, 2)
        Data expectedData2 = new Data(2, 1, 2, 'O');
        verify(mockDbManager).save(argThat(data -> data.getTurn() == expectedData2.getTurn() &&
                                                    data.getX() == expectedData2.getX() &&
                                                    data.getY() == expectedData2.getY() &&
                                                    data.getPlayer() == expectedData2.getPlayer()));
        // Third move: 'X' on (2, 1)
        Data expectedData3 = new Data(3, 2, 1, 'X');
        verify(mockDbManager).save(argThat(data -> data.getTurn() == expectedData3.getTurn() &&
                                                    data.getX() == expectedData3.getX() &&
                                                    data.getY() == expectedData3.getY() &&
                                                    data.getPlayer() == expectedData3.getPlayer()));
    }
    @Test
    public void whenPlayAndSaveReturnsFalseThenThrowException() throws Exception {
        // Stub the saveMove method to throw a RuntimeException when called
        doThrow(new RuntimeException("Data saving failed")).when(mockDbManager).save(any(Data.class)); 

        // Create a new Data object representing the move
        Data move = new Data(1, 1, 3, 'X');  

        // Expect a RuntimeException to be thrown when play() is called
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ticTacToe.play(move.getX(), move.getY());  
        });

        // Verify the exception message (optional)
        assertEquals("Data saving failed", exception.getMessage());
    }


} 

