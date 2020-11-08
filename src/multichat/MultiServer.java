package multichat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.activation.URLDataSource;

public class MultiServer {
//대화 차단 처리중임
	static ServerSocket serverSocket = null;
	static Socket socket = null;
	public String n = "";
	//클라이언트 정보저장을 위한 Map컬렉션생성
	Map<String, PrintWriter> clientMap;
	Map<String,String> room;
	HashSet<String> blacklist= new HashSet<String>();
	HashSet<String> pWords= new HashSet<String>();
	Connection con;
	PreparedStatement psmt;
	Statement stmst;
	ResultSet rs;
	
	//생성자
	public MultiServer() {
		//클라이언트의 이름과 출력스트림을 저장할 HashMap 컬렉션생성
		clientMap = new HashMap<String, PrintWriter>();
		//HashMap 동기화설정. 쓰레드가 사용자정보에 동시에 접근하는것을 차단
		Collections.synchronizedMap(clientMap);
		blacklist.add("생강");
		blacklist.add("가지");
		pWords.add("병");
		pWords.add("쨔식");
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con= DriverManager.getConnection(
					"jdbc:oracle:thin://@localhost:1521:orcl",
					"kosmo","1234");
			System.out.println("연결성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void init() {
		try {
			/*
			9999번 포트를 설정하여 서버객체를 생성하고 클라이언트의
			접속을 대기한다. 
			 */
			serverSocket = new ServerSocket(9999);
			System.out.println("서버가 시작되었습니다.");

			while(true) {
				socket = serverSocket.accept();
				System.out.println(
					socket.getInetAddress()+"(클라이언트)의 "
					+socket.getPort()+" 포트를통해 "
					+socket.getLocalAddress()+"(서버)의"
					+socket.getLocalPort()+" 포트로 연결됨");
				
				/*
				클라이언트의 메세지를 모든 클라이언트에게 전달하기 위한 
				쓰레드 생성 및 시작. 한명당 하나씩의 쓰레드가 생성된다. 
				 */
				Thread mst = new MultiServerT(socket);
				mst.start();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				serverSocket.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		MultiServer ms = new MultiServer();
		ms.init();
	}
	
	//접속된 모든 클라이언트에게 메세지를 전달하는 메소드
	public void sendAllMsg(String name, String msg, String flag)
	{
		//Map에 저장된 객체의 키값(접속자명)을 먼저 얻어온다.
		Iterator<String> it = clientMap.keySet().iterator();
		
		//저장된 객체(클라이언트)의 갯수만큼 반복한다. 
		while(it.hasNext()) {
			try {
				//컬렉션의 key는 클라이언트의 접속자명이다. 
				String clientName = it.next();
				
				//각 클라이언트의 PrintWriter객체를 얻어온다. 
				PrintWriter it_out = 
						(PrintWriter) clientMap.get(clientName);
				
				
				
				if(flag.equals("One")) {
					//flag가 One이면 해당 클라이언트 한명에게만 전송
					if(name.equals(clientName)) {
						//컬렉션에 저장된 접속자명과 일치하는경우 메세지 전송
						it_out.println(URLEncoder.encode("< "+"[귓속말]"+msg,"UTF-8"));
					}
				}
				else if(flag.equals("no")) {
					if(name.equals(clientName)) {
						it_out.println(URLEncoder.encode("< "+ msg,"UTF-8"));
					}
				}else if(flag.equals("block")) {
					if(name.equals(clientName)) {
						it_out.println(URLEncoder.encode("b "+msg,"UTF-8"));
					}
				}
				else if(flag.equals("unblock")) {
					if(name.equals(clientName)) {
						it_out.println(URLEncoder.encode("u "+msg,"UTF-8"));
					}
				}
				else{
					//그 외에는 모든 클라이언트에게 전송
					if(name.equals("")) {
						//접속, 퇴장에서 사용되는 부분
						it_out.println(URLEncoder.encode("< "+msg,"UTF-8"));
					}
					else {
						//메세지를 보낼때 사용되는 부분
						
						it_out.println(URLEncoder.encode("[ "+ name +" ]"+ msg,"UTF-8"));
					}
				}
				
				
				 
			}
			catch(Exception e) {
				System.out.println("예외:"+e);
			}
		}
	}


	//내부클래스
	class MultiServerT extends Thread {

		//멤버변수
		Socket socket;
		PrintWriter out = null;
		BufferedReader in = null;
		//생성자 : socket을 기반으로 입출력 스트림 생성
		public MultiServerT(Socket socket) {
			this.socket = socket;
			try {
				out = new PrintWriter(this.socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(),"UTF-8"));
			}
			catch (Exception e) {
				System.out.println("예외:"+ e);
			}
		}

		
		@Override
		public void run() {
			
			boolean same = true;
			String name = "";
			String s = "";

			try {
				//클라이언트의 이름을 읽어와서 저장
				name = in.readLine();
				name = URLDecoder.decode(name,"UTF-8");
				
				if(clientMap.size()==limit.MAX) {
					try {
						clientMap.put(name, out);
						sendAllMsg(name, "접속이 차단되었습니다", "no");
						in.close();
						out.close();
						socket.close();
						return;
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					
				}
				if(blacklist.contains(name)==true) {
					try {
						same=false;
						clientMap.put(name, out);
						sendAllMsg(name, "당신은 블랙리스트 입니다.", "no");
						in.close();
						out.close();
						socket.close();
						return;
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
				if(clientMap.containsKey(name)==true) {
					try {
						same=false;
						clientMap.put(name+"", out);
						sendAllMsg(name+"", "중복된 이름입니다.", "no");
						in.close();
						out.close();
						socket.close();
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
				else {
					same = true;
					sendAllMsg("", name + "님이 입장하셨습니다.", "All");
					
					//현재 접속자의 정보를 HashMap에 저장한다. 
					clientMap.put(name, out);
					sendAllMsg(name, "대기실에 입장하셨습니다.", "no");
					//HashMap에 저장된 객체의 수로 접속자수를 파악할수 있다.
					System.out.println(name + " 접속");
					System.out.println("현재 접속자 수는 "+ clientMap.size()
					+"명 입니다.");
				}
				int fUnf = 0;
				while (in!=null) {
					s = in.readLine();
					s = URLDecoder.decode(s,"UTF-8");
					if ( s == null )
						break;
					String query = " INSERT INTO chat_talking VALUES (seq_chat.nextVal,? ,? ,sysdate)";
					psmt = con.prepareStatement(query);
					psmt.setNString(1, name);
					psmt.setNString(2, s);
					psmt.executeUpdate();
					
					System.out.println(name + " >> " + s);
					
					/*
					클라이언트가 전송한 메세지가 명령어인지 판단한다. 
					 */
					if(fUnf == 0) {
						if(s.charAt(0)=='/') {
						
							String[] strArr = s.split(" ");
						
							String msgContent = "";
							for(int i=2 ; i<strArr.length ; i++)
								msgContent += strArr[i]+" ";
							
							if(strArr[0].equals("/to")) {
								sendAllMsg(strArr[1], msgContent, "One");
							}
							else if(strArr[0].equals("/fixto")) {
								fUnf = 1;
								n = strArr[1];
							}
							else if(strArr[0].equals("/block")) {
								sendAllMsg(name, strArr[1]+" 님을 차단했습니다", "block");
							}
							else if(strArr[0].equals("/unblock")) {
								sendAllMsg(name, strArr[1]+" 님 차단해제", "unblock");
							}
							else if(strArr[0].equals("/list")) {
								Set<String> key = clientMap.keySet();
								for(String k : key) {
									if(k.equals(name)==false) {
										sendAllMsg(name, String.format("%s", k), "no");
									}
								}
							}
						}
						else if(pWords.contains(s)==true) {
							sendAllMsg(name, "XX-(예쁜말 쓰세요)", "All");
						}
						else {
							//만약 /로 시작하지 않으면 일반 메세지이다. 
							sendAllMsg(name, s, "All");//All이면 전체전송
						}
					}else if(fUnf==1) {
						String[] strArr = s.split(" ");
						if(strArr[0].equals("/unfixto")) {
							fUnf=0;
						}
						else
						sendAllMsg(n, s , "One");
					}
				}
			}
			catch (Exception e) {
				System.out.println("예외:"+ e);
			}
			finally {
				/*
				클라이언트가 접속을 종료하면 Socket예외가 발생하게 되어
				finally절로 진입하게된다. 이때 "대화명"을 통해 정보를 
				삭제한다. 
				 */
				try {
					if(con != null)con.close();
					if(psmt != null)psmt.close();
				} catch (Exception e2) {
				
				}
					if(same==true) {
					clientMap.remove(name);
					sendAllMsg("", name + "님이 퇴장하셨습니다.", "All");
					//퇴장하는 클라이언트의 쓰레드명을 보여준다. 
					System.out.println(name + " [" + Thread.currentThread().getName() +  "] 퇴장");
					System.out.println("현재 접속자 수는 "+clientMap.size()+"명 입니다.");
					
					try {
						in.close();
						out.close();
						socket.close();
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}

=======
package multichat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.activation.URLDataSource;

public class MultiServer {
//대화 차단 처리중임
	static ServerSocket serverSocket = null;
	static Socket socket = null;
	public String n = "";
	//클라이언트 정보저장을 위한 Map컬렉션생성
	Map<String, PrintWriter> clientMap;
	HashSet<String> blacklist= new HashSet<String>();
	HashSet<String> pWords= new HashSet<String>();
	Connection con;
	PreparedStatement psmt;
	Statement stmst;
	ResultSet rs;
	
	//생성자
	public MultiServer() {
		//클라이언트의 이름과 출력스트림을 저장할 HashMap 컬렉션생성
		clientMap = new HashMap<String, PrintWriter>();
		//HashMap 동기화설정. 쓰레드가 사용자정보에 동시에 접근하는것을 차단
		Collections.synchronizedMap(clientMap);
		blacklist.add("생강");
		blacklist.add("가지");
		pWords.add("병");
		pWords.add("쨔식");
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con= DriverManager.getConnection(
					"jdbc:oracle:thin://@localhost:1521:orcl",
					"kosmo","1234");
			System.out.println("연결성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void init() {
		try {
			/*
			9999번 포트를 설정하여 서버객체를 생성하고 클라이언트의
			접속을 대기한다. 
			 */
			serverSocket = new ServerSocket(9999);
			System.out.println("서버가 시작되었습니다.");

			while(true) {
				socket = serverSocket.accept();
				System.out.println(
					socket.getInetAddress()+"(클라이언트)의 "
					+socket.getPort()+" 포트를통해 "
					+socket.getLocalAddress()+"(서버)의"
					+socket.getLocalPort()+" 포트로 연결됨");
				
				/*
				클라이언트의 메세지를 모든 클라이언트에게 전달하기 위한 
				쓰레드 생성 및 시작. 한명당 하나씩의 쓰레드가 생성된다. 
				 */
				Thread mst = new MultiServerT(socket);
				mst.start();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				serverSocket.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		MultiServer ms = new MultiServer();
		ms.init();
	}
	
	//접속된 모든 클라이언트에게 메세지를 전달하는 메소드
	public void sendAllMsg(String name, String msg, String flag)
	{
		//Map에 저장된 객체의 키값(접속자명)을 먼저 얻어온다.
		Iterator<String> it = clientMap.keySet().iterator();
		
		//저장된 객체(클라이언트)의 갯수만큼 반복한다. 
		while(it.hasNext()) {
			try {
				//컬렉션의 key는 클라이언트의 접속자명이다. 
				String clientName = it.next();
				
				//각 클라이언트의 PrintWriter객체를 얻어온다. 
				PrintWriter it_out = 
						(PrintWriter) clientMap.get(clientName);
				
				
				
				if(flag.equals("One")) {
					//flag가 One이면 해당 클라이언트 한명에게만 전송
					if(name.equals(clientName)) {
						//컬렉션에 저장된 접속자명과 일치하는경우 메세지 전송
						it_out.println(URLEncoder.encode("< "+"[귓속말]"+msg,"UTF-8"));
					}
				}
				else if(flag.equals("no")) {
					if(name.equals(clientName)) {
						it_out.println(URLEncoder.encode("< "+ msg,"UTF-8"));
					}
				}else if(flag.equals("block")) {
					if(name.equals(clientName)) {
						it_out.println(URLEncoder.encode("b "+msg,"UTF-8"));
					}
				}
				else if(flag.equals("unblock")) {
					if(name.equals(clientName)) {
						it_out.println(URLEncoder.encode("u "+msg,"UTF-8"));
					}
				}
				else{
					//그 외에는 모든 클라이언트에게 전송
					if(name.equals("")) {
						//접속, 퇴장에서 사용되는 부분
						it_out.println(URLEncoder.encode("< "+msg,"UTF-8"));
					}
					else {
						//메세지를 보낼때 사용되는 부분
						
						it_out.println(URLEncoder.encode("[ "+ name +" ]"+ msg,"UTF-8"));
					}
				}
				
				
				 
			}
			catch(Exception e) {
				System.out.println("예외:"+e);
			}
		}
	}


	//내부클래스
	class MultiServerT extends Thread {

		//멤버변수
		Socket socket;
		PrintWriter out = null;
		BufferedReader in = null;
		//생성자 : socket을 기반으로 입출력 스트림 생성
		public MultiServerT(Socket socket) {
			this.socket = socket;
			try {
				out = new PrintWriter(this.socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(),"UTF-8"));
			}
			catch (Exception e) {
				System.out.println("예외:"+ e);
			}
		}

		
		@Override
		public void run() {
			
			boolean same = true;
			String name = "";
			String s = "";

			try {
				//클라이언트의 이름을 읽어와서 저장
				name = in.readLine();
				name = URLDecoder.decode(name,"UTF-8");
				
				if(clientMap.size()==limit.MAX) {
					try {
						clientMap.put(name, out);
						sendAllMsg(name, "접속이 차단되었습니다", "no");
						in.close();
						out.close();
						socket.close();
						return;
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					
				}
				if(blacklist.contains(name)==true) {
					try {
						same=false;
						clientMap.put(name, out);
						sendAllMsg(name, "당신은 블랙리스트 입니다.", "no");
						in.close();
						out.close();
						socket.close();
						return;
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
				if(clientMap.containsKey(name)==true) {
					try {
						same=false;
						clientMap.put(name+"", out);
						sendAllMsg(name+"", "중복된 이름입니다.", "no");
						in.close();
						out.close();
						socket.close();
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
				else {
					same = true;
					sendAllMsg("", name + "님이 입장하셨습니다.", "All");
					
					//현재 접속자의 정보를 HashMap에 저장한다. 
					clientMap.put(name, out);
					sendAllMsg(name, "서버와 연결되었습니다", "no");
					//HashMap에 저장된 객체의 수로 접속자수를 파악할수 있다.
					System.out.println(name + " 접속");
					System.out.println("현재 접속자 수는 "+ clientMap.size()
					+"명 입니다.");
				}
				int fUnf = 0;
				while (in!=null) {
					s = in.readLine();
					s = URLDecoder.decode(s,"UTF-8");
					if ( s == null )
						break;
					String query = " INSERT INTO chat_talking VALUES (seq_chat.nextVal,? ,? ,sysdate)";
					psmt = con.prepareStatement(query);
					psmt.setNString(1, name);
					psmt.setNString(2, s);
					psmt.executeUpdate();
					
					System.out.println(name + " >> " + s);
					
					/*
					클라이언트가 전송한 메세지가 명령어인지 판단한다. 
					 */
					if(fUnf == 0) {
						if(s.charAt(0)=='/') {
						
							String[] strArr = s.split(" ");
						
							String msgContent = "";
							for(int i=2 ; i<strArr.length ; i++)
								msgContent += strArr[i]+" ";
							
							if(strArr[0].equals("/to")) {
								sendAllMsg(strArr[1], msgContent, "One");
							}
							else if(strArr[0].equals("/fixto")) {
								fUnf = 1;
								n = strArr[1];
							}
							else if(strArr[0].equals("/block")) {
								sendAllMsg(name, strArr[1]+" 님을 차단했습니다", "block");
							}
							else if(strArr[0].equals("/unblock")) {
								sendAllMsg(name, strArr[1]+" 님 차단해제", "unblock");
							}
							else if(strArr[0].equals("/list")) {
								Set<String> key = clientMap.keySet();
								for(String k : key) {
									if(k.equals(name)==false) {
										sendAllMsg(name, String.format("%s", k), "no");
									}
								}
							}
						}
						else if(pWords.contains(s)==true) {
							sendAllMsg(name, "XX-(예쁜말 쓰세요)", "All");
						}
						else {
							//만약 /로 시작하지 않으면 일반 메세지이다. 
							sendAllMsg(name, s, "All");//All이면 전체전송
						}
					}else if(fUnf==1) {
						String[] strArr = s.split(" ");
						if(strArr[0].equals("/unfixto")) {
							fUnf=0;
						}
						else
						sendAllMsg(n, s , "One");
					}
				}
			}
			catch (Exception e) {
				System.out.println("예외:"+ e);
			}
			finally {
				/*
				클라이언트가 접속을 종료하면 Socket예외가 발생하게 되어
				finally절로 진입하게된다. 이때 "대화명"을 통해 정보를 
				삭제한다. 
				 */
				try {
					if(con != null)con.close();
					if(psmt != null)psmt.close();
				} catch (Exception e2) {
				
				}
					if(same==true) {
					clientMap.remove(name);
					sendAllMsg("", name + "님이 퇴장하셨습니다.", "All");
					//퇴장하는 클라이언트의 쓰레드명을 보여준다. 
					System.out.println(name + " [" + Thread.currentThread().getName() +  "] 퇴장");
					System.out.println("현재 접속자 수는 "+clientMap.size()+"명 입니다.");
					
					try {
						in.close();
						out.close();
						socket.close();
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}

