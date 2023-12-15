package stock_bot;

import java.io.IOException;
import java.util.List;

public class Stock_Bot {
   static String filePath = "Stock_Bot/TSLA.csv";

static String outputFilePath = "output.csv"; 



    public static void main(String[] args) {
        try {
            StockDataCollector stockDataCollector = new StockDataCollector();
            List<StockDataCollector.StockData> stockDataList = stockDataCollector.loadStockData(filePath);

            // Calculate RSI and moving average
            RsiCalculator.calculateRsi(stockDataList, 14);
            MovingAverageCalculator.calculateMovingAverage(stockDataList, 10);

            // Implement trade evaluator and simulation logic
            performSimulation(stockDataList);

            // Write results to CSV
        CsvWriter.writeResultsToCsv(stockDataList, outputFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void performSimulation(List<StockDataCollector.StockData> stockDataList) {
        double initialBalance = 10000; // Replace with your initial balance
        double balance = initialBalance;
        int numberOfShares = 0;

        for (int i = 0; i < stockDataList.size(); i++) {
            StockDataCollector.StockData currentData = stockDataList.get(i);

            System.out.println("Date: " + currentData.getDate() + ", RSI: " + currentData.getRsi() + ", Close: " + currentData.getClose());
            System.out.println("Buying decision: " + (currentData.getRsi() < 30 && currentData.getClose() < currentData.getMovingAverage()));
            System.out.println("Selling decision: " + (currentData.getRsi() > 70 || currentData.getClose() > currentData.getMovingAverage()));

            // Example: Buy if RSI is below 30 and the close price is below the moving average
            if (currentData.getRsi() < 30 && currentData.getClose() < currentData.getMovingAverage()) {
                double amountToInvest = 0.1 * balance; // 10% of balance
                numberOfShares += (int) (amountToInvest / currentData.getClose());
                balance -= amountToInvest;
            }

            // Example: Sell if RSI is above 70 or if a certain condition is met
            if (currentData.getRsi() > 70 || currentData.getClose() > currentData.getMovingAverage()) {
                double amountToSell = 0.1 * balance; // 10% of balance
                numberOfShares -= (int) (amountToSell / currentData.getClose());
                balance += amountToSell;
            }
        }

        // After simulation, print results
        System.out.println("Initial Balance: $" + initialBalance);
        System.out.println("Final Balance: $" + balance);
        System.out.println("Number of Shares: " + numberOfShares);
    }
}
