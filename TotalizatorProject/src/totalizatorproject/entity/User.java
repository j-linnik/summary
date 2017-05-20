package totalizatorproject.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
    private String mail;
    private int balance;
    private ArrayList<Bet> bets;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public ArrayList<Bet> getBets() {
        return bets;
    }

    public void setBets(ArrayList<Bet> bets) {
        this.bets = bets;
    }

    @Override
    public Object clone(){
        User us = new User();
        us.setMail(mail);
        us.setBalance(balance);
        us.setBets(bets);
        return us;
    }
    
    
}
