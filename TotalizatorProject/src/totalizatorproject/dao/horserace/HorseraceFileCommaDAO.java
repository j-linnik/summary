package totalizatorproject.dao.horserace;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import totalizatorproject.entity.Horserace;
import totalizatorproject.exceptions.TotalizatorDAOException;

public class HorseraceFileCommaDAO extends HorseraceFileDAO {

    private static final String FILE_NAME = "races.txt";

    @Override
    protected void saveCollection() throws TotalizatorDAOException {
        try {
            FileWriter fw = new FileWriter(FILE_NAME);
            try {
                BufferedWriter bw = new BufferedWriter(fw);
                //записываем каждую гонку - её дату и ай-ди
                for (Horserace race : races) {
                    bw.write(race.getRaceId() + "," + race.getDate()
                            + System.lineSeparator());

                    String file = String.valueOf(race.getRaceId() + ".txt");
                    try {
                        FileWriter horsesFW = new FileWriter(file);
                        try {
                            BufferedWriter horsesBW = new BufferedWriter(horsesFW);
                            //из каждой гонки достаём список лошадей и записываем отдельно 
                            //в файл, который называем "(ай-ди гонки) + ."txt"
                            for (String horse : race.getHorses()) {
                                horsesBW.write(horse + System.lineSeparator());
                            }
                            horsesBW.close();
                        } finally {
                            horsesFW.close();
                        }
                    } catch (IOException ex) {
                        throw new TotalizatorDAOException(ex);
                    }
                }
                bw.close();
            } finally {
                fw.close();
            }
        } catch (IOException ex) {
            throw new TotalizatorDAOException(ex);
        }
    }

    @Override
    protected void loadCollection() throws TotalizatorDAOException {
        races = new ArrayList<>();
        try {
            FileReader fr = new FileReader(FILE_NAME);
            try {
                BufferedReader br = new BufferedReader(fr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    
                    //вытаскиваем все гонки
                    String[] params = line.split("\\s*,\\s*");
                    Horserace race = new Horserace();
                    race.setRaceId(Long.parseLong(params[0]));
                    race.setDate(params[1]);

                    //вытаскиваем всех лощадей для каждой гонки(одна строка - одна лошадь)
                    String file = String.valueOf(race.getRaceId() + ".txt");
                    ArrayList<String> tmpHorses = new ArrayList<>();
                    try {
                        FileReader horsesFR = new FileReader(file);
                        try {
                            BufferedReader horsesBR = new BufferedReader(horsesFR);
                            String horse = null;
                            while ((horse = br.readLine()) != null) {
                                horse = br.readLine();
                                tmpHorses.add(horse);
                            }
                        } finally {

                        }
                    } catch (IOException ex) {
                        throw new TotalizatorDAOException(ex);
                    }
                    race.setHorses(tmpHorses);

                    races.add(race);
                }
            } finally {
                fr.close();
            }
        } catch (IOException ex) {
            throw new TotalizatorDAOException(ex);
        }
    }
}
