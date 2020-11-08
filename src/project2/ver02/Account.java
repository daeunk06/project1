package project2.ver02;

import java.util.Scanner;


public class Account{
	Scanner sc = new Scanner(System.in);
	String Anum ;
	String name;
	int balance;
	
	public Account(String anum, String name, int balance) {
		Anum = anum;
		this.name = name;
		this.balance = balance;
	}
	public int calinter(int add) {
		return 0;
	}
	
	public void makeAccount() {
	}    // 계좌개설을 위한 함수
	public void depositMoney(int cnt) {
	}    // 입    금
	public void withdrawMoney(int cnt) {
		
	} // 출    금
	public void showAccInfo() {
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
=======
package project2.ver02;

import java.util.Scanner;


public class Account{
	Scanner sc = new Scanner(System.in);
	String Anum ;
	String name;
	int balance;
	
	public Account(String anum, String name, int balance) {
		Anum = anum;
		this.name = name;
		this.balance = balance;
	}
	public int calinter(int add) {
		return 0;
	}
	
	public void makeAccount() {
	}    // 계좌개설을 위한 함수
	public void depositMoney(int cnt) {
	}    // 입    금
	public void withdrawMoney(int cnt) {
		
	} // 출    금
	public void showAccInfo() {
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
