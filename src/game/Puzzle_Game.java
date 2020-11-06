package game;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class Puzzle_Game {
	static Scanner sc = new Scanner(System.in);
	static Random ran = new Random();
	static void puzzle() {
		String[][] span = {{"1","2","3"},{"4","5","6"},{"7","8","X"}};

		int x=0,y=0;
		String temp = "";
		int cnt=0;
		while(cnt!=3) {
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					if(span[i][j].equalsIgnoreCase("X")) 
					{	x=i; y=j;	}
				}
			}
			try {
				int[] r = {-1,1};
				int a=ran.nextInt(2) , b=ran.nextInt(2);
				a = r[a];
				if(b==0) {
					temp = span[x+a][y];
					span[x+a][y]=span[x][y];
					span[x][y]=temp;
				}
				else {
					temp = span[x][y+a];
					span[x][y+a]=span[x][y];
					span[x][y]=temp;
				}
				cnt++;
			} 
			catch (ArrayIndexOutOfBoundsException e) {}
	}
		move(span);
	}
	static void showArray(String[][] Span) {
		System.out.println("===========");
		for(int i =0; i<Span.length;i++) {
			for(int j=0; j<Span[i].length;j++) {
				System.out.print(Span[i][j]);
			}
			System.out.println();
		}
		System.out.println("===========");
		
	}
	static void move(String S[][]) {
		String[][] pan = 
			{{"1","2","3"},{"4","5","6"},{"7","8","X"}};
		showArray(S);
		String temp = "";
		int x=0,y=0;
		while(true) {
			try {
				for(int i =0; i<=2;i++) {
					for(int j=0; j<=2;j++) {
						if(S[i][j].equals("X")) 
							{x=i; y=j;}
					}
				}
				System.out.println("[ 이동 ] a:Left d:Right w:Up s:Down");
				System.out.println("[ 종료] x : Exit");
				System.out.print("키를 입력해 주세요");
				String key = sc.nextLine();

				switch (key) {
				case "w":{temp = S[x][y]; S[x][y]=S[x-1][y];
				S[x-1][y]=temp; break;}
				case "a":{temp = S[x][y]; S[x][y]=S[x][y-1];
				S[x][y-1]=temp; break;}
				case "s":{temp = S[x][y]; S[x][y]=S[x+1][y];
				S[x+1][y]=temp; break;}
				case "d":{temp = S[x][y]; S[x][y]=S[x][y+1];
				S[x][y+1]=temp; break;}
				case "x": System.exit(0); break;
				}
				showArray(S);
				if(Arrays.deepEquals(S, pan)) {System.out.println("==정답ヾ(≧▽≦*)o=="); break;}
			}
			catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("xxxxxxxxxxxxxxxx\nxxxxx이동불가xxxxx\nxxxxxxxxxxxxxxxx");
			} 
		}
	}
	public static void main(String[] args) {
		String infi = "y";
		while(infi.equalsIgnoreCase("y")) {
			puzzle();
			System.out.println("재시작하시겠습니까 ?(y 누르면 재시작, 나머지 종료)");
			String i = sc.nextLine();
			infi = i;
		}
	}
}
