package stock_bot;

import java.util.List;
import stock_bot.StockDataCollector.StockData;

public class MovingAverageCalculator {
    public static void calculateMovingAverage(List<StockData> stockDataList, int window) {
        if (stockDataList == null || stockDataList.size() == 0 || window <= 0 || window > stockDataList.size()) {
            throw new IllegalArgumentException("Invalid input parameters for moving average calculation");
        }

        for (int i = window - 1; i < stockDataList.size(); i++) {
            double sum = 0;
            for (int j = 0; j < window; j++) {
                sum += stockDataList.get(i - j).getClose();
            }

            double movingAverage = sum / window;
            stockDataList.get(i).setMovingAverage(movingAverage);
        }
    }
}
