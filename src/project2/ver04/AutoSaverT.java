package project2.ver04;

import java.io.Serializable;

public class AutoSaverT extends Thread implements Serializable{
	AccountManager acount ;
	public AutoSaverT(AccountManager ac) {
		acount = ac;
	}
	@Override
	public void run() {
		while(true) {
			try {
				acount.autosave();
				Thread.sleep(5000);
				System.out.println("5초마다 자동저장.");
			} catch (InterruptedException e) {
				System.out.println("데몬쓰레드 종료");
				return;
			}
			catch (Exception e) {
				break;
			}
		}
		
	}
	
	
	
}
