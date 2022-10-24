import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*Создайте класс ClientLog для сохранения всех операций, которые ввёл пользователь.
Т.е. покупатель добавил покупку, то это действие должно быть там сохранено.
Для этого создайте там метод log(int productNum, int amount).*/
public class ClientLog {
    private final List<String> logList = new ArrayList<>();

    public void log(int productNum, int amount) {
        logList.add(productNum + "," + amount);
    }

    /*Также у объекта этого класса должен быть метод exportAsCSV(File txtFile)
    для сохранения всего журнала действия в файл в формате csv.
    В конце работы программы сохраняйте журнал действий в файл log.csv*/
    public void exportAsCSV(File txtFile) throws IOException {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(txtFile, true),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.RFC4180_LINE_END)) {
            for (String s : logList) {
                csvWriter.writeNext(s.split(","));
            }
        }
    }
}