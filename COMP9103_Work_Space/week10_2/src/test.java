public class test {
    public static void main(String args[]){
        TermDepositAccount termDepositAccount = new TermDepositAccount(12,1500,0.03,100000);
        System.out.println(termDepositAccount.getBalance());
        termDepositAccount.AddInterest(7);
        System.out.println(termDepositAccount.getBalance());
    }
}
