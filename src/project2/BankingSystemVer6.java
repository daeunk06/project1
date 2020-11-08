package project2;

import java.util.Scanner;

import game.Puzzle_Game;
import project2.ver06.AccountManager;
import project2.ver06.AutoSaverT;
import project2.ver06.MenuChoice;
import project2.ver06.MenuSelectException;



public class BankingSystemVer6 implements  MenuChoice{

	public static void showMenu() {
		System.out.println("----Menu----");
		System.out.println("1.계좌계설\n" + "2.입금\n" + "3.출금\n"
				+ "4.전체계좌정보출력\n" + "5.저장옵션\n"+ "6.프로그램종료\n" +"7.퍼즐게임");
	}       // 메뉴출력
	public static void main(String[] args) {
		
		AutoSaverT saver = null;
		AccountManager ac = new AccountManager();
		Puzzle_Game pg = new Puzzle_Game();
		Scanner sc = new Scanner(System.in);
		
		ac.restore();
		while(true) {
			showMenu();
			try {
				System.out.print("선택:");
				int choice = sc.nextInt();
				if(choice >=1 && choice <= 7) {
					switch (choice) {
					case MAKE:
					{ ac.makeAccount(); break;}
					case DEPOSIT:
					{	ac.depositMoney(); break;}
					case WITHDRAW:
					{ac.withdrawMoney(); break;}
					case INQUIRE:
					{ac.showAccInfo();
					break;}
					case SAVE:	
					{  	
						if(ac.dn == 0) {
							saver = new AutoSaverT(ac);
						}
						ac.Dthread(saver);
						break;}
					case GAME:
					{
						pg.main(args); break;
					}
					case EXIT:
					{	ac.save();
						System.out.println("저장후 종료");
						return;}
					}
				}
				else {
					MenuSelectException an = new MenuSelectException();
					throw an;
				}
			} catch (MenuSelectException e) {
				System.out.println(e.getMessage());
			}
			
		}
	}
}
