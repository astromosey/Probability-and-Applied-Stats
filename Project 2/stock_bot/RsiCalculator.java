// RsiCalculator.java
package stock_bot;

import java.util.List;
import stock_bot.StockDataCollector.StockData;

public class RsiCalculator {

   public static void calculateRsi(List<StockData> stockDataList, int period) {
    if (stockDataList == null || stockDataList.size() < 2 || period <= 0 || stockDataList.size() <= period) {
        throw new IllegalArgumentException("Invalid input parameters for RSI calculation: " +
                "stockDataList=" + stockDataList + ", period=" + period);
    }

    double[] priceChanges = calculatePriceChanges(stockDataList);
    double[] gains = calculateGainsLosses(priceChanges, true);
    double[] losses = calculateGainsLosses(priceChanges, false);

    if (gains.length < period || losses.length < period) {
        throw new IllegalArgumentException("Not enough data for the specified period: " +
                "gains.length=" + gains.length + ", losses.length=" + losses.length + ", period=" + period);
    }

    double[] avgGains = calculateAverageGainsLosses(gains, period);
    double[] avgLosses = calculateAverageGainsLosses(losses, period);

    for (int i = period; i < stockDataList.size(); i++) {
        double avgGain = avgGains[i - 1];
        double avgLoss = avgLosses[i - 1];
        double currentGain = gains[i - 1];
        double currentLoss = losses[i - 1];

        avgGain = ((avgGain * (period - 1)) + currentGain) / period;
        avgLoss = ((avgLoss * (period - 1)) + currentLoss) / period;

        double relativeStrength = (avgLoss == 0) ? 100 : avgGain / avgLoss;
        double rsi = 100 - (100 / (1 + relativeStrength));

        stockDataList.get(i).setRsi(rsi);
    }
}



    private static double[] calculatePriceChanges(List<StockData> stockDataList) {
        double[] priceChanges = new double[stockDataList.size() - 1];
        for (int i = 1; i < stockDataList.size(); i++) {
            priceChanges[i - 1] = stockDataList.get(i).getClose() - stockDataList.get(i - 1).getClose();
        }
        return priceChanges;
    }

    private static double[] calculateGainsLosses(double[] priceChanges, boolean gains) {
        double[] gainsLosses = new double[priceChanges.length];
        for (int i = 0; i < priceChanges.length; i++) {
            gainsLosses[i] = (priceChanges[i] > 0 && gains) ? priceChanges[i] : ((priceChanges[i] < 0 && !gains) ? -priceChanges[i] : 0);
        }
        return gainsLosses;
    }

    private static double[] calculateAverageGainsLosses(double[] gainsLosses, int period) {
        if (gainsLosses.length < period) {
            throw new IllegalArgumentException("Not enough data for the specified period");
        }

        double[] avgGainsLosses = new double[gainsLosses.length];
        double sum = 0;
        for (int i = 0; i < period; i++) {
            sum += gainsLosses[i];
        }
        avgGainsLosses[period - 1] = sum / period;

        for (int i = period; i < gainsLosses.length; i++) {
            sum = sum - gainsLosses[i - period] + gainsLosses[i];
            avgGainsLosses[i] = sum / period;
        }

        return avgGainsLosses;
    }
}
