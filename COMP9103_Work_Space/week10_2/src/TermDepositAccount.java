public class TermDepositAccount extends BankAccount{
    private int months ;
    private double penalty;
    private double rate;
    public TermDepositAccount(){}
    
    public TermDepositAccount(int months, double penalty,double rate ,double balance){
        super(balance);
        this.months = months;
        this.penalty = penalty;
        this.rate = rate;
    }

    public void AddInterest(int months) {
        if (this.months > months) {
            deposit(super.getBalance() * rate * months - penalty);
        }
        else
            deposit(super.getBalance() * rate * months);
    }
}
