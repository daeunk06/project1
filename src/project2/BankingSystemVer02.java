package project2;

import java.awt.peer.ChoicePeer;
import java.io.StreamCorruptedException;
import java.util.Scanner;

import project2.ver01.Account;
import project2.ver02.AccountManager;
import project2.ver02.MenuChoice;

public class BankingSystemVer02 implements  MenuChoice{

	public static void showMenu() {
		System.out.println("----Menu----");
		System.out.println("1.계좌계설\n" + "2.입금\n" + "3.출금\n"
				+ "4.전체계좌정보출력\n" + "5.프로그램종료\n");
	}       // 메뉴출력
	public static void main(String[] args) {
		
		AccountManager ac = new AccountManager(50);
		Scanner sc = new Scanner(System.in);
				while(true) {
			showMenu();
			System.out.print("선택:");
			int choice = sc.nextInt();
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
			case EXIT:
			{return;}
			}
			
		}
	}
}
