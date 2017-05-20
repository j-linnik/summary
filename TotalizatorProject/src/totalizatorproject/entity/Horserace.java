package totalizatorproject.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Horserace implements Serializable{
    
    private long raceId;
    private ArrayList<String> horses;
    private Date date;
    private String winner;

    public long getRaceId() {
        return raceId;
    }
    public void setRaceId(long raceId) {
        this.raceId = raceId;
    }

    public ArrayList<String> getHorses() {
        return horses;
    }

    public void setHorses(ArrayList<String> horses) {
        this.horses = horses;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    //второй сеттер даты принимает в аргумент СТРОКУ (безобразие, но опыта работы с датами пока мало)
    public void setDate(String date) { 
        DateFormat format2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        try {
            this.date = format2.parse(date);
        } catch (ParseException ex) {
            System.out.println("setDate со стороковым аргументом не работает!");
        }
    }

    public String getWinner() {
        return winner;
    }
    public void setWinner(String winner) {
        this.winner = winner;
    }

    @Override
    public Object clone() {
        Horserace race = new Horserace();
        race.setRaceId(raceId);
        race.setDate(date);
        race.setHorses(horses);
        race.setWinner(winner);
        return race;
    }
    
   
}
