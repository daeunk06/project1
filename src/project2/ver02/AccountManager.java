package project2.ver02;

import java.util.Scanner;

public class AccountManager {
	Account[] acn;
	int cnt= 0;
	Scanner sc = new Scanner(System.in);
	public AccountManager(int n) {
		acn = new Account[n];
	}
	public void makeAccount() {
		sc.nextLine();
		System.out.println("***신규계좌개설***");
		System.out.println("-----계좌선택------\n1.보통계좌 \n2.신용신뢰계좌");
		int select = sc.nextInt();
		sc.nextLine();
		System.out.print("계좌번호 :");
		String Anum = sc.nextLine();
		System.out.print("이름 :");
		String name = sc.nextLine();
		System.out.print("잔고 :");
		int balance = sc.nextInt();
		System.out.print("기본이자%(정수형태로입력):");
		int interest = sc.nextInt();
		if(select == 1) {
			acn[cnt++] = new NormalAccount(Anum,name,balance,interest);}
		else if(select == 2) {
			sc.nextLine();
			System.out.print("신용등급(A,B,C등급):");
			String rank = sc.nextLine();
			acn[cnt++] = new HighCreditAccount(Anum,name,balance,interest,rank);}
		System.out.println("계좌계설이 완료되었습니다.");
	}    // 계좌개설을 위한 함수
	public void depositMoney() {
		sc.nextLine();
		System.out.println("***입  금***");
		System.out.println("계좌번호와 입금할 금액을 입력하세요");
		System.out.print("계좌번호:");
		String An = sc.nextLine();
		System.out.print("입금액:");
		int add = sc.nextInt();
		for(int i=0; i<cnt; i++) {
			if(An.equals(acn[i].getAnum())) {
				acn[i].setBalance(acn[i].calinter(add));
			}
		}
		System.out.println("입금이 완료되었습니다.");
	}    // 입    금
	public void withdrawMoney() {
		sc.nextLine();
		System.out.println("***출   금***" );
		System.out.println("계좌번호와 출금할 금액을 입력하세요");
		System.out.print("계좌번호:");
		String An = sc.nextLine();
		System.out.print("출금액:");
		int sub = sc.nextInt();
		for(int i=0; i<cnt; i++) {
		if(An.equals(acn[i].getAnum()))
			acn[i].setBalance(acn[i].getBalance() - sub);}
		System.out.println("출금이 완료되었습니다.");
		
	} // 출    금
	public void showAccInfo() {
		for(int i=0; i<cnt; i++) {
		System.out.println("***계좌정보출력***");
		System.out.println("이름:"+ acn[i].getName());
		System.out.println("계좌번호:"+acn[i]. getAnum());
		System.out.println("잔액:"+ acn[i].getBalance());
		acn[i].showAccInfo();}
	}  // 전체계좌정보출력

}
=======
package project2.ver02;

import java.util.Scanner;

public class AccountManager {
	Account[] acn;
	int cnt= 0;
	Scanner sc = new Scanner(System.in);
	public AccountManager(int n) {
		acn = new Account[n];
	}
	public void makeAccount() {
		sc.nextLine();
		System.out.println("***신규계좌개설***");
		System.out.println("-----계좌선택------\n1.보통계좌 \n2.신용신뢰계좌");
		int select = sc.nextInt();
		sc.nextLine();
		System.out.print("계좌번호 :");
		String Anum = sc.nextLine();
		System.out.print("이름 :");
		String name = sc.nextLine();
		System.out.print("잔고 :");
		int balance = sc.nextInt();
		System.out.print("기본이자%(정수형태로입력):");
		int interest = sc.nextInt();
		if(select == 1) {
			acn[cnt++] = new NormalAccount(Anum,name,balance,interest);}
		else if(select == 2) {
			sc.nextLine();
			System.out.print("신용등급(A,B,C등급):");
			String rank = sc.nextLine();
			acn[cnt++] = new HighCreditAccount(Anum,name,balance,interest,rank);}
		System.out.println("계좌계설이 완료되었습니다.");
	}    // 계좌개설을 위한 함수
	public void depositMoney() {
		sc.nextLine();
		System.out.println("***입  금***");
		System.out.println("계좌번호와 입금할 금액을 입력하세요");
		System.out.print("계좌번호:");
		String An = sc.nextLine();
		System.out.print("입금액:");
		int add = sc.nextInt();
		for(int i=0; i<cnt; i++) {
			if(An.equals(acn[i].getAnum())) {
				acn[i].setBalance(acn[i].calinter(add));
			}
		}
		System.out.println("입금이 완료되었습니다.");
	}    // 입    금
	public void withdrawMoney() {
		sc.nextLine();
		System.out.println("***출   금***" );
		System.out.println("계좌번호와 출금할 금액을 입력하세요");
		System.out.print("계좌번호:");
		String An = sc.nextLine();
		System.out.print("출금액:");
		int sub = sc.nextInt();
		for(int i=0; i<cnt; i++) {
		if(An.equals(acn[i].getAnum()))
			acn[i].setBalance(acn[i].getBalance() - sub);}
		System.out.println("출금이 완료되었습니다.");
		
	} // 출    금
	public void showAccInfo() {
		for(int i=0; i<cnt; i++) {
		System.out.println("***계좌정보출력***");
		System.out.println("이름:"+ acn[i].getName());
		System.out.println("계좌번호:"+acn[i]. getAnum());
		System.out.println("잔액:"+ acn[i].getBalance());
		acn[i].showAccInfo();}
	}  // 전체계좌정보출력

}
