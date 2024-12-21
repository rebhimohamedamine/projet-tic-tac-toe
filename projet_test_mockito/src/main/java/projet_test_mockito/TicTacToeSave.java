package projet_test_mockito;

public class TicTacToeSave {

    private GererDB dbManager;

    public TicTacToeSave(GererDB dbManager) {
        this.dbManager = dbManager;
    }
    
    

    public void initializeDatabase(String dbName) throws Exception {
        try {
        	dbManager.verify();
        	dbManager.connect();
        } catch (Exception e) {
        	dbManager.create();
        	dbManager.connect();
        }
    }
    
    
    
    public boolean saveMove(Data move) {
        try {
        	dbManager.save(move); 
            return true;        
        } catch (Exception e) {
        	
            return false;       
        }
    }

	public boolean clearDatabase() throws Exception {
		// TODO Auto-generated method stub
        try {
        	dbManager.drop(); 
            return true;        
        } catch (Exception e) {
            return false;       
        
	}
      
	}
    
}
