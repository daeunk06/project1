package project2.ver05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Account {
	Scanner sc = new Scanner(System.in);
	public String Anum ;
	public String name;
	public int balance;
	int cnt= 0;
	Connection con;
	PreparedStatement psmt;
	Statement stmst;
	ResultSet rs;
	
	
	public Account(String anum, String name, int balance) {
		Anum = anum;
		this.name =name;
		this.balance = balance;
	}
	public Account[] AccountInfo;
	
	public Account(int n) {
		AccountInfo = new Account[n];
	}
	public void makeAccount() {
		try {

			Class.forName("oracle.jdbc.OracleDriver");
			con= DriverManager.getConnection(
					"jdbc:oracle:thin://@localhost:1521:orcl",
					"kosmo","1234");
			System.out.println("연결성공");
			
			String query = " INSERT INTO BANKING_TB VALUES (? ,? ,? ,seq_banking.nextVal)";
			psmt = con.prepareStatement(query);
			
			Scanner scan = new Scanner(System.in);
			System.out.println("***신규계좌개설***");
			System.out.println("계좌번호:");
			 Anum = scan.nextLine();
			System.out.println("이름:");
			 name = scan.nextLine();
			System.out.println("잔고:");
			balance = scan.nextInt();
			
			psmt.setString(1, Anum);
			psmt.setString(2, name);
			psmt.setInt(3, balance);
			
			int affected = psmt.executeUpdate();
			System.out.println(affected +"행이 입력되었습니다.");
			
			AccountInfo[cnt++] = new Account(Anum,name,balance);
			System.out.println("계좌계설이 완료되었습니다.");
			
		} catch (ClassNotFoundException e) {
			System.out.println("오라클 드라이버 로딩 실패");
		}
		catch (SQLException e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		}
		 catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			try {
				if(con != null)con.close();
				if(psmt != null) psmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	}    // 계좌개설을 위한 함수
	public void depositMoney() {
		try {

			Class.forName("oracle.jdbc.OracleDriver");
			con= DriverManager.getConnection(
					"jdbc:oracle:thin://@localhost:1521:orcl",
					"kosmo","1234");
			System.out.println("연결성공");
			String query = " UPDATE  BANKING_TB SET balance = balance+? "
					+ " WHERE Anum=? ";
			psmt = con.prepareStatement(query);
			sc.nextLine();
			System.out.println("***입  금***");
			System.out.println("계좌번호와 입금할 금액을 입력하세요");
			System.out.print("계좌번호:");
			String An = sc.nextLine();
			System.out.print("입금액:");
			int add = sc.nextInt();
			
			psmt.setInt(1, add);
			psmt.setNString(2, An);
			int affected = psmt.executeUpdate();
			System.out.println(affected+"행이 업데이트 되었습니다.");
			System.out.println("입금이 완료되었습니다.");
			
		} catch (ClassNotFoundException e) {
			System.out.println("오라클 드라이버 로딩 실패");
		}
		catch (SQLException e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		}
		 catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			try {
				if(con != null)con.close();
				if(psmt != null) psmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}    // 입    금
	public void withdrawMoney() {
		try {

			Class.forName("oracle.jdbc.OracleDriver");
			con= DriverManager.getConnection(
					"jdbc:oracle:thin://@localhost:1521:orcl",
					"kosmo","1234");
			System.out.println("연결성공");
			String query = " UPDATE  BANKING_TB SET balance = balance-? "
					+ " WHERE Anum=? ";
			psmt = con.prepareStatement(query);
			sc.nextLine();
			System.out.println("***출 금***");
			System.out.println("계좌번호와 출금할 금액을 입력하세요");
			System.out.print("계좌번호:");
			String An = sc.nextLine();
			System.out.print("출금액:");
			int add = sc.nextInt();
			
			psmt.setInt(1, add);
			psmt.setNString(2, An);
			int affected = psmt.executeUpdate();
			System.out.println(affected+"행이 업데이트 되었습니다.");
			System.out.println("출금이 완료되었습니다.");
			
		} catch (ClassNotFoundException e) {
			System.out.println("오라클 드라이버 로딩 실패");
		}
		catch (SQLException e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		}
		 catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			try {
				if(con != null)con.close();
				if(psmt != null) psmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	} // 출    금
	public void showAccInfo() {
		try {

			Class.forName("oracle.jdbc.OracleDriver");
			con= DriverManager.getConnection(
					"jdbc:oracle:thin://@localhost:1521:orcl",
					"kosmo","1234");
			System.out.println("연결성공");
			
			stmst = con.createStatement();
			String sql = " SELECT * FROM BANKING_TB ";
			
			rs = stmst.executeQuery(sql);
			System.out.println("===계좌정보를 출력합니다===");
			while (rs.next()) {
				
				String Anum = rs.getString(1);
				String name = rs.getString(2);
				int balance = rs.getInt(3);
				int ind = rs.getInt(4);
		
				System.out.printf("계좌번호:%s%n 이름:%s%n 잔고:%d%n 인덱스:%d \n===================\n"
						,Anum,name,balance,ind);
				}
			
		} catch (ClassNotFoundException e) {
			System.out.println("오라클 드라이버 로딩 실패");
		}
		catch (SQLException e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		}
		 catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			try {
				if(stmst != null) stmst.close();
				if(con != null)con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	// 전체계좌정보출력


}
=======
package project2.ver05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Account {
	Scanner sc = new Scanner(System.in);
	public String Anum ;
	public String name;
	public int balance;
	int cnt= 0;
	Connection con;
	PreparedStatement psmt;
	Statement stmst;
	ResultSet rs;
	
	
	public Account(String anum, String name, int balance) {
		Anum = anum;
		this.name =name;
		this.balance = balance;
	}
	public Account[] AccountInfo;
	
	public Account(int n) {
		AccountInfo = new Account[n];
	}
	public void makeAccount() {
		try {

			Class.forName("oracle.jdbc.OracleDriver");
			con= DriverManager.getConnection(
					"jdbc:oracle:thin://@localhost:1521:orcl",
					"kosmo","1234");
			System.out.println("연결성공");
			
			String query = " INSERT INTO BANKING_TB VALUES (? ,? ,? ,seq_banking.nextVal)";
			psmt = con.prepareStatement(query);
			
			Scanner scan = new Scanner(System.in);
			System.out.println("***신규계좌개설***");
			System.out.println("계좌번호:");
			 Anum = scan.nextLine();
			System.out.println("이름:");
			 name = scan.nextLine();
			System.out.println("잔고:");
			balance = scan.nextInt();
			
			psmt.setString(1, Anum);
			psmt.setString(2, name);
			psmt.setInt(3, balance);
			
			int affected = psmt.executeUpdate();
			System.out.println(affected +"행이 입력되었습니다.");
			
			AccountInfo[cnt++] = new Account(Anum,name,balance);
			System.out.println("계좌계설이 완료되었습니다.");
			
		} catch (ClassNotFoundException e) {
			System.out.println("오라클 드라이버 로딩 실패");
		}
		catch (SQLException e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		}
		 catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			try {
				if(con != null)con.close();
				if(psmt != null) psmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	}    // 계좌개설을 위한 함수
	public void depositMoney() {
		try {

			Class.forName("oracle.jdbc.OracleDriver");
			con= DriverManager.getConnection(
					"jdbc:oracle:thin://@localhost:1521:orcl",
					"kosmo","1234");
			System.out.println("연결성공");
			String query = " UPDATE  BANKING_TB SET balance = balance+? "
					+ " WHERE Anum=? ";
			psmt = con.prepareStatement(query);
			sc.nextLine();
			System.out.println("***입  금***");
			System.out.println("계좌번호와 입금할 금액을 입력하세요");
			System.out.print("계좌번호:");
			String An = sc.nextLine();
			System.out.print("입금액:");
			int add = sc.nextInt();
			
			psmt.setInt(1, add);
			psmt.setNString(2, An);
			int affected = psmt.executeUpdate();
			System.out.println(affected+"행이 업데이트 되었습니다.");
			System.out.println("입금이 완료되었습니다.");
			
		} catch (ClassNotFoundException e) {
			System.out.println("오라클 드라이버 로딩 실패");
		}
		catch (SQLException e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		}
		 catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			try {
				if(con != null)con.close();
				if(psmt != null) psmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}    // 입    금
	public void withdrawMoney() {
		try {

			Class.forName("oracle.jdbc.OracleDriver");
			con= DriverManager.getConnection(
					"jdbc:oracle:thin://@localhost:1521:orcl",
					"kosmo","1234");
			System.out.println("연결성공");
			String query = " UPDATE  BANKING_TB SET balance = balance-? "
					+ " WHERE Anum=? ";
			psmt = con.prepareStatement(query);
			sc.nextLine();
			System.out.println("***출 금***");
			System.out.println("계좌번호와 출금할 금액을 입력하세요");
			System.out.print("계좌번호:");
			String An = sc.nextLine();
			System.out.print("출금액:");
			int add = sc.nextInt();
			
			psmt.setInt(1, add);
			psmt.setNString(2, An);
			int affected = psmt.executeUpdate();
			System.out.println(affected+"행이 업데이트 되었습니다.");
			System.out.println("출금이 완료되었습니다.");
			
		} catch (ClassNotFoundException e) {
			System.out.println("오라클 드라이버 로딩 실패");
		}
		catch (SQLException e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		}
		 catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			try {
				if(con != null)con.close();
				if(psmt != null) psmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	} // 출    금
	public void showAccInfo() {
		try {

			Class.forName("oracle.jdbc.OracleDriver");
			con= DriverManager.getConnection(
					"jdbc:oracle:thin://@localhost:1521:orcl",
					"kosmo","1234");
			System.out.println("연결성공");
			
			stmst = con.createStatement();
			String sql = " SELECT * FROM BANKING_TB ";
			
			rs = stmst.executeQuery(sql);
			System.out.println("===계좌정보를 출력합니다===");
			while (rs.next()) {
				
				String Anum = rs.getString(1);
				String name = rs.getString(2);
				int balance = rs.getInt(3);
				int ind = rs.getInt(4);
		
				System.out.printf("계좌번호:%s%n 이름:%s%n 잔고:%d%n 인덱스:%d \n===================\n"
						,Anum,name,balance,ind);
				}
			
		} catch (ClassNotFoundException e) {
			System.out.println("오라클 드라이버 로딩 실패");
		}
		catch (SQLException e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		}
		 catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			try {
				if(stmst != null) stmst.close();
				if(con != null)con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	// 전체계좌정보출력


}
