package projet_test_mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TicTacToeToSaveTest {

	  private GererDB mockDbManager;
	    private TicTacToeSave saveManager;

	    @BeforeEach
	    void setUp() {
	        mockDbManager = mock(GererDB.class); // Create a mock instance of GererDB
	        saveManager = new TicTacToeSave(mockDbManager); // Inject the mock into TicTacToeSave
	    }
	    
	    @Test
	    void testDatabaseInitializationWithExistingDatabase() throws Exception {
	        doNothing().when(mockDbManager).verify();
	        doNothing().when(mockDbManager).connect();

	        
	        saveManager.initializeDatabase("tic-tac-toe");

	        
	        verify(mockDbManager, times(1)).verify();
	        verify(mockDbManager, times(1)).connect();
	        verify(mockDbManager, never()).create();
	    }
	    
	    
	    
	    @Test
	    void testDatabaseInitializationWithNonExistentDatabase() throws Exception {
	    	  
	    	doThrow(new Exception("Database not found")).when(mockDbManager).verify();
	        doNothing().when(mockDbManager).create();
	        doNothing().when(mockDbManager).connect();

	        
	        saveManager.initializeDatabase("tic-tac-toe");

	        
	        verify(mockDbManager, times(1)).verify();
	        verify(mockDbManager, times(1)).create();
	        verify(mockDbManager, times(1)).connect();
	    }
	    
	    
	    @Test
	    void testSaveMoveToDatabase() throws Exception {
	        doNothing().when(mockDbManager).save(any(Data.class));
	         
	        Data move = new Data(1, 2, 3, 'X');
	        
	        saveManager.saveMove(move);
	        
	        verify(mockDbManager, times(1)).save(move);
	        
	    }

	    @Test
	    void testSaveMoveReturnsTrue() throws Exception {
	        
	        doNothing().when(mockDbManager).save(any(Data.class));
	        
	        Data move = new Data(1, 2, 3, 'X'); // Turn: 1, X: 2, Y: 3, Player: X
	  
	        boolean result = saveManager.saveMove(move);
	       
	        assertTrue(result);

	        verify(mockDbManager, times(1)).save(move);
	    }
	    
	    
	    
	    @Test
	    void testSveMoveReturnsFalse() throws Exception{
	   doThrow(new Exception ("Connexion error c")).when(mockDbManager).save(any(Data.class));
	   Data move = new Data(1, 2, 3, 'X'); // Turn: 1, X: 2, Y: 3, Player: X
		  
       boolean result = saveManager.saveMove(move);
       
       assertFalse(result);
       verify(mockDbManager, times(1)).save(move);
	    }
	    
	    
	    @Test
	    void testClearDatabase() throws Exception {
	    	doNothing().when(mockDbManager).drop();
	    	 saveManager.clearDatabase();
	    	  verify(mockDbManager, times(1)).drop();
	    }
	    
	    @Test
	    void testClearDatabasereturnTrue() throws Exception {
	    	doNothing().when(mockDbManager).drop();
	    	 boolean result = saveManager.clearDatabase();
	    	 assertTrue(result);
	    	  verify(mockDbManager, times(1)).drop();
	    }
        
	    void testClearDatabasereturnFalse() throws Exception {
	    	doNothing().when(mockDbManager).drop();
	    	 boolean result = saveManager.clearDatabase();
	    	 assertFalse(result);
	    	  verify(mockDbManager, times(1)).drop();
	    }
	    
	    
	     


}
