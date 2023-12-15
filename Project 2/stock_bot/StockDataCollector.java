package stock_bot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StockDataCollector {

  public static List<StockData> loadStockData(String filePath) throws IOException {
        List<StockData> stockDataList = new ArrayList<>();

        try (InputStream inputStream = StockDataCollector.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new FileNotFoundException("File not found: " + filePath);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                // Assuming the CSV format has headers: Date, Close, etc.
                br.readLine();
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    StockData stockData = new StockData(values[0], Double.parseDouble(values[4]));
                    stockDataList.add(stockData);
                }
            }
        }
        return stockDataList;
    }


    public static class StockData {
        private String date;
        private double close;
        private double rsi;
        private double movingAverage;

        public StockData(String date, double close) {
            this.date = date;
            this.close = close;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public double getClose() {
            return close;
        }

        public void setClose(double close) {
            this.close = close;
        }

        public double getRsi() {
            return rsi;
        }

        public void setRsi(double rsi) {
            this.rsi = rsi;
        }

        public double getMovingAverage() {
            return movingAverage;
        }

        public void setMovingAverage(double movingAverage) {
            this.movingAverage = movingAverage;
        }
    }
}
