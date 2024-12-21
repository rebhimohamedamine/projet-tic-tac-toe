package projet_test_mockito;

public class Data {
     private int turn;
         public int getTurn() {
return turn;
}
private int x;
public int getX() {
return x;
}
private int y;
public int getY() {
return y;
}
private char player; 
public char getPlayer() {
return player;
}
public Data() { }
public Data (int turn, int x, int y, char player) {
this.turn = turn;
this.x = x;
this.y = y;
this.player = player;
}
public String toString() {
return String.format("Turn: %d; X: %d; Y: %d; Player: %s", turn,
x, y, player);
}
}
