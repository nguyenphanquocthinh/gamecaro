/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thinh
 */
public class BanCo {
    public static final int no_player = 0; // no player
    public static final int player_x = 1; // chon danh x
    public static final int player_0 = 2; // chon danh O
    private int[][] board = new int[3][3]; // so o tren ban co 3x3
    public Point computerMove; 
    
    public boolean GameOver(){
        return hasPlayerWin(player_x) || hasPlayerWin(player_0) || getAvailableCells().isEmpty();
        // Truong hop end game khi nguoi choi danh x or danh o thang or khi khong con o tren ban co
    }
    
    public boolean hasPlayerWin(int player) {
        // xet nguoi choi thang khi co 3 diem thang hang
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == player)
                || 
                (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == player)
                ){
        return true;
        }
        
        //check row va columm
        for(int i=0;i<3;i++){
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == player)
                    || 
                    (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == player)
                    )
                return true;
        }
        return false;
    }
    
    public List<Point> getAvailableCells(){
        //so o trong khong ton tai
        
        List<Point> availableCells = new ArrayList<>();
        
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if (board[i][j] == no_player)
                    availableCells.add(new Point(i,j));
            }
        }
        return availableCells;
    }
    
    public boolean placeMove(Point point,int player){
        //place diem neu diem do chua duoc danh
        if(board[point.x][point.y] != no_player)
            return false; 
        board[point.x][point.y] = player;
        return true;
    }
    
    public void displayBoard(){
        System.out.println();
        for(int i=0;i<3;i++){ // thay doi value o day de tang o trong ban co
            for(int j=0;j<3;j++){
                // o trong chua duoc danh dau la " ? "
                String value = "?";
                if (board[i][j] == player_x)
                    // hien chu x khi danh len man hinh
                    value = "x";
                else if (board[i][j] == player_0)
                    // hien chu o khi danh len man hinh
                    value = "o";
                System.out.print(value + " ");
            }
            System.out.println();
        }  
        System.out.println();
    }
    
    // thuat toan min max
    public int min_max(int depth,int turn){
        //2 tham so, neu may tinh o trang thai x thi player se di nuoc max va nguoc lai
        if(hasPlayerWin(player_x))  return 1;
        if(hasPlayerWin(player_0)) return -1;
        
        List<Point> availableCells = getAvailableCells();
        
        if(availableCells.isEmpty())
            return 0;
        
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        for(int i=0;i<availableCells.size();i++){
            Point point = availableCells.get(i);
            
            
            //dieu kien trong turn, nguoi nao chon nuoc max thi nguoi kia di min, va nguoc lai
            if(turn == player_x){
                placeMove(point, player_x);
                int currentScore = min_max(depth + 1,player_0);
                max = Math.max(currentScore,max);
                
                if(depth == 0) // in ra cac gia tri cua diem
                    System.out.println("Gia tri cua diem la "+point+"="+currentScore);
                if(currentScore >= 0) // in ra nhung diem va gia tri AI da danh
                    if(depth == 0)
                        computerMove = point;
                if(currentScore == 1){
                    board[point.x][point.y] = no_player;
                    break;
                }
                //Neu da di het tat ca cac nuoc MAX thi khuay vi tri va chon nuoc di con lai
                if(i == availableCells.size() - 1 && max<0)
                    if(depth == 0)
                        computerMove = point;
            } else if(turn == player_0){ //
                placeMove(point,player_0);
                int currentScore = min_max(depth + 1,player_x);
                min = Math.min(currentScore, min);
                
                if(min == -1){
                    board[point.x][point.y] = no_player;
                    break;
                }
            }
            board[point.x][point.y] = no_player; // save point
        }
        // neu trong turn da danh point thi tra ve Max or Min
        return turn == player_x ? max:min;
    }
}
