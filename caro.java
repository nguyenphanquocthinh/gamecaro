/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Thinh
 */
public class caro {
    //Trong truong hop AI di truoc no se cho random 1 point tren ban co de danh
    public static final Random RANDOM = new Random();
    
    public static void main(String[] args) {
        BanCo b = new BanCo();
        Scanner scanner = new Scanner(System.in);
        b.displayBoard();
        System.out.println("Chon nguoi di truoc:\n1. Computer (x)/2.User(o): ");
        
        int choice = scanner.nextInt();
        
        // neu chon 1 thi user la "x" va nguoc lai
        if(choice == BanCo.player_x){
            Point p = new Point(RANDOM.nextInt(3),RANDOM.nextInt(3));
            b.placeMove(p, BanCo.player_x);
            b.displayBoard();
        }
        
        while(!b.GameOver()){
            boolean move0k = true;
            
            do{
                if(!move0k){
                    System.out.println("O da duoc choose");
            }
                
                System.out.println("Nuoc di cua ban: ");
                Point userMove = new Point(scanner.nextInt(), scanner.nextInt());
                //dat diem
                move0k = b.placeMove(userMove, BanCo.player_0);
            } while (!move0k);
            b.displayBoard();
            
            if(b.GameOver())
                break;
            b.min_max(0, BanCo.player_x);
            System.out.println("May chon diem: " + b.computerMove);
            
            b.placeMove(b.computerMove, BanCo.player_x);
            b.displayBoard();
        }
        if(b.hasPlayerWin(BanCo.player_x))
            System.out.println("You lost!");
        else if(b.hasPlayerWin(BanCo.player_0)){
            System.out.println("You win!");
        }
        else
            System.out.println("Draw");
            
    }
}
