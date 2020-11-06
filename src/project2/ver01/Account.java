package project2.ver01;

import java.util.Scanner;


public class Account{
	Scanner sc = new Scanner(System.in);
	private String Anum ;
	private String name;
	private int balance;
	int cnt= 0;
	public Account(String anum, String name, int balance) {
		setAnum(anum);
		this.setName(name);
		this.setBalance(balance);
	}
	public Account[] AccountInfo;
	
	public Account(int n) {
		AccountInfo = new Account[n];
	}
	public void makeAccount() {
		sc.nextLine();
		System.out.println("***신규계좌개설***");
		System.out.print("계좌번호 :");
		setAnum(sc.nextLine());
		System.out.print("이름 :");
		setName(sc.nextLine());
		System.out.print("잔고 :");
		setBalance(sc.nextInt());
		AccountInfo[cnt++] = new Account(getAnum(),getName(),getBalance());
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
			if(An.equals(AccountInfo[i].getAnum())) {
				AccountInfo[i].setBalance(AccountInfo[i].getBalance() + add);
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
		if(An.equals(AccountInfo[i].getAnum()))
			AccountInfo[i].setBalance(AccountInfo[i].getBalance() - sub);}
		System.out.println("출금이 완료되었습니다.");
		
	} // 출    금
	public void showAccInfo() {
		for(int i=0; i<cnt; i++) {
		System.out.println("***계좌정보출력***");
		System.out.println("이름:"+ AccountInfo[i].getName());
		System.out.println("계좌번호:"+AccountInfo[i]. getAnum());
		System.out.println("잔액:"+ AccountInfo[i].getBalance());}
	}  // 전체계좌정보출력
	public String getAnum() {
		return Anum;
	}
	public void setAnum(String anum) {
		Anum = anum;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
