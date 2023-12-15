package stock_bot;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {
    public static void writeResultsToCsv(List<StockDataCollector.StockData> stockDataList, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write CSV header
            writer.append("Date,Close,RSI,MovingAverage,BuyingDecision,SellingDecision\n");

            // Write stock data entries
            for (StockDataCollector.StockData data : stockDataList) {
                writer.append(String.format("%s,%.2f,%.2f,%.2f,%b,%b\n",
                        data.getDate(), data.getClose(), data.getRsi(), data.getMovingAverage(),
                        (data.getRsi() < 30 && data.getClose() < data.getMovingAverage()),
                        (data.getRsi() > 70 || data.getClose() > data.getMovingAverage())));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
