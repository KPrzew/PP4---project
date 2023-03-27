package pl.przewoznikkarol.creditcard;

import java.math.BigDecimal;

public class CreditCard {

    private BigDecimal balance;
    private BigDecimal limit;
    private String cardNumber;
    private int counter = 0;

    public CreditCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void withdraw(BigDecimal value) {
        if (value.compareTo(getLimit()) <= 0) {
            if (value.compareTo(getBalance()) <= 0){
                if (getCounter() >= 10) {
                    throw new WithdrawCounterException();
                } else {
                    this.setBalance(getBalance().subtract(value));
                    this.addCounter();
                    System.out.println("Withdrawn " + value);
                    System.out.println(counter);
                }
            }else {
                throw new WithdrawBiggerThanBalanceException();
            }
        }else {
            throw new WithdrawBiggerThanLimitException();
        }
    }

    private int getCounter() {
        return this.counter;
    }

    private void addCounter() {
        this.counter+=1;
    }

    public void assignLimit(BigDecimal creditAmount){
        if (getLimit() == null){
            if (isBelowThreshold(creditAmount)) {
                throw new CreditLimitBelowException();
            }
            this.limit = creditAmount;
            this.balance = creditAmount;
        }
        else{
            throw new LimitAlreadyAssignedException();
        }
    }

    private static boolean isBelowThreshold(BigDecimal creditAmount) {
        return creditAmount.compareTo(BigDecimal.valueOf(100)) < 0;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
