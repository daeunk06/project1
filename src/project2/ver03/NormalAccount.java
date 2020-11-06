package project2.ver03;

public class NormalAccount extends Account {

	int interest;
	
	public NormalAccount(String anum, String name, int balance ,int interest) {
		super(anum, name, balance);
		this.interest = interest;
	}
	public int getInterest() {
		return interest;
	}
	public void setInterest(int interest) {
		this.interest = interest;
	}
	@Override
	public int calinter(int add) {
		balance = balance + (balance * interest) + add;
		return balance;
	}
	@Override
	public void showAccInfo() {
		System.out.println("기본이자>"+interest);
	}

}
