package projet_test_mockito;

public interface GererDB {
    // Génère une exception si la connexion à la BD échoue
    void connect()throws Exception;

    // Génère une exception si la BD n’existe pas
    void verify()throws Exception ;

    // Crée la BD
    void create()throws Exception;

    // Supprime les données de la BD
    void drop()throws Exception;

    // Sauvegarde dans la BD les données de d
    void save(Data d) throws Exception ;
}

