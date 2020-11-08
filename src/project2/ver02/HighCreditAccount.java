package project2.ver02;

public class HighCreditAccount extends Account implements CustomSpecialRate{
	int interest;
	int Bint;
	String rank;
	public HighCreditAccount(String anum, String name, int balance, int in
			 , String r) {
		super(anum, name, balance);
		interest = in;
		rank = r;
	}
	@Override
	public int calinter(int add) {
		switch (rank) {
		case "A": case "a":{
			Bint = A; break;}
		case "B": case "b":{
			Bint = B; break;}
		case "C": case "c":{
			Bint = C; break;}
		}
		balance = balance + (balance * interest) + (balance * Bint)+ add;
		return balance;
	}
	@Override
	public void showAccInfo() {
		System.out.println("기본이자>%"+interest);
		System.out.println("신용등급>"+rank);
	}
	public int getInterest() {
		return interest;
	}
	public void setInterest(int interest) {
		this.interest = interest;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}


}
