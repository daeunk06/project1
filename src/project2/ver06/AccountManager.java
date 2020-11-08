package project2.ver06;

import java.awt.dnd.Autoscroll;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;


public class AccountManager {
	HashSet<Account> hset;
	int cnt= 0;
	Scanner sc = new Scanner(System.in);

	public AccountManager() {
		hset= new HashSet<Account>();
		
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
		
		Account a = new NormalAccount(Anum,name,balance,interest);
		if(select == 1) {
			if(hset.add(a)==false) {
				System.out.println("중복계좌발견됨. 덮어쓸까요?(y or n)");
				sc.nextLine();
				String yorn = sc.nextLine();
				if(yorn.equalsIgnoreCase("y")) {
					hset.remove(a);
					System.out.println("덮어쓰기 완료");
				}
			}
			hset.add(a);
			
		}
		else if(select == 2) {
			sc.nextLine();
			System.out.print("신용등급(A,B,C등급):");
			String rank = sc.nextLine();
			Account b = new HighCreditAccount(Anum,name,balance,interest,rank);
			if(hset.add(b) ==false) {
				System.out.println("중복계좌발견됨. 덮어쓸까요?(y or n)");
				String yorn = sc.nextLine();
				if(yorn.equalsIgnoreCase("y")) {
					hset.remove((b));
					System.out.println("덮어쓰기 완료");
				}
			}
			hset.add(b);}
		System.out.println("계좌계설이 완료되었습니다.");
	
		
	}    // 계좌개설을 위한 함수
	public void depositMoney() {
		sc.nextLine();
		System.out.println("***입  금***");
		System.out.println("계좌번호와 입금할 금액을 입력하세요");
		System.out.print("계좌번호:");
		String An = sc.nextLine();
		System.out.print("입금액:");
		try {
			int add = sc.nextInt();
			if(add >= 0) {
				if(add % 500 == 0) {
					Iterator<Account> itr = hset.iterator();
					while(itr.hasNext()) {
						Account account = (Account) itr.next();
						if(An.equals(account.getAnum())) {
							account.setBalance(account.calinter(add));
						}
					}
					System.out.println("입금이 완료되었습니다.");
				}
				else System.out.println("500원 단위로만 입금가능");
			}
			else System.out.println("음수를 입금 할 수 없습니다.");
			
		} catch (InputMismatchException e) {
			System.out.println("입금은 숫자를 입력해주세요");
		}
	}    // 입    금
	public void withdrawMoney() {
		sc.nextLine();
		System.out.println("***출   금***" );
		System.out.println("계좌번호와 출금할 금액을 입력하세요");
		System.out.print("계좌번호:");
		String An = sc.nextLine();
		System.out.print("출금액:");
		int sub = sc.nextInt();
		if(sub >= 0) {
			Iterator<Account> itr = hset.iterator();
			while(itr.hasNext()) {
				Account account = (Account) itr.next();
				if(An.equals(account.getAnum())) {
					if(sub <= account.getBalance()) {
						if(sub % 1000==0) 
							account.setBalance(account.getBalance() - sub);
						else System.out.println("1000원 단위로만 출금가능");
					}
					else {
						sc.nextLine();
						System.out.println("잔고가 부족합니다. 금액전체를 출금할까요?\n" + 
								"YES : 금액전체 출금처리\n" + "NO : 출금요청취소\n" );
							String YesorNo = sc.nextLine();
							if(YesorNo.equalsIgnoreCase("yes"))
								account.setBalance(0);
							else System.out.println("출금취소 처리되었습니다.");
					}
				}
			}
			System.out.println("출금이 완료되었습니다.");
		}
		else System.out.println("음수를 출금 할 수 없습니다.");
		
	} // 출    금
	public void showAccInfo() {
		Iterator<Account> itr = hset.iterator();
		while(itr.hasNext()) {
			Account account = (Account) itr.next();
			System.out.println("***계좌정보출력***");
			System.out.println("이름:"+ account.getName());
			System.out.println("계좌번호:"+account. getAnum());
			System.out.println("잔액:"+ account.getBalance());
			account.showAccInfo();}
	}  // 전체계좌정보출력
	public void save() {
		try {
			//파일을 만들기 위한 스트림 생성
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream("src/project2/AccountInfo.obj"));
			
			Iterator<Account> itr = hset.iterator();
			while(itr.hasNext()) {
				Account account = (Account) itr.next();
				out.writeObject(account);
			}out.close();
				
		}
		catch (Exception e) {
			System.out.println("예외 발생");
			e.printStackTrace();
		}
		
	}///e
		
	public void restore() {
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(
					new FileInputStream("src/project2/AccountInfo.obj"));
			while(true) {
				
				Account account = (Account)in .readObject();
				hset.add(account);
				if(account==null) break;
			}in.close();
		} catch (FileNotFoundException e) {
		}
		catch (IOException e) {
		}
		catch (Exception e) {
		}
	}
	public static  int  dn =0;
	
	public void Dthread(AutoSaverT saver) {
		sc.nextLine();
		System.out.println("1.자동저장on 2.자동저장off");
		int onoff = sc.nextInt();
		if(onoff == 1) {
			if(saver.isAlive()) {
				System.out.println("이미 자동저장 실행중입니다.");
			}
			else {
				saver.setDaemon(true);
				saver.start();
			}
			dn = 1;
		}
		else if(onoff == 2) {
			saver.interrupt();
			dn = 0;
		}
	}
	public void autosave() {
		try {
			//파일을 만들기 위한 스트림 생성
			PrintWriter out = new PrintWriter(new
					FileWriter("src/project2/AutoSaveAccount.txt"));
			
			Iterator<Account> itr = hset.iterator();
			while(itr.hasNext()) {
				Account account = (Account) itr.next();
				
				out.printf("계좌번호 : %s %n이름: %s %n잔고 :%d%n "
				,account.getAnum(),account.getName(),account.getBalance());
			}out.close();
				
		}
		catch (Exception e) {
			System.out.println("예외 발생");
			e.printStackTrace();
		}
	}
	
}
	
		


