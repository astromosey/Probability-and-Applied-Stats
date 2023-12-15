import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ApacheJFreeChart {

    public void plotGraph(String fileName, int min, int max, double interval) {
        File csvPlot = new File(fileName + ".csv");
        String header = "x, y, y = x^2 + 2x + 1";
        ArrayList<Double> yValues = new ArrayList<>();
        ArrayList<Double> xValues = new ArrayList<>();
        double[] plotFunction = new double[]{1, 2, 1};
        PolynomialFunction poly = new PolynomialFunction(plotFunction);

        try {
            FileWriter fileWriter = new FileWriter(csvPlot);
            fileWriter.write(header + System.lineSeparator());

            double x = min + interval;

            for (double counter = min; counter < max; counter += interval) {
                if (!(counter > min)) {
                    double y = poly.value(min);
                    fileWriter.write(min + "," + y);
                    fileWriter.write(System.lineSeparator());
                    yValues.add(y);
                }

                if (counter + interval <= max) {
                    double y = poly.value(x);
                    fileWriter.write(x + "," + y);
                    fileWriter.write(System.lineSeparator());
                    x += interval;
                    yValues.add(y);
                }

                xValues.add(counter);
            }

            fileWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Graph plot = new Graph("Plotter", "Population Size of " + max + " with an Increment of " + interval, xValues, yValues);
        plot.pack();
        plot.setVisible(true);
    }

    public void addSaltNoise(String fileName, int saltRangeMin, int saltRangeMax) {
        ArrayList<String> xyValues = new ArrayList<>();
        ArrayList<Double> yValues = new ArrayList<>();
        ArrayList<Double> xValues = new ArrayList<>();
        File saltedCSV = new File("salted" + fileName);
        Random rand = new Random();

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int count = 0;

            for (line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine(), count++) {
                if (count > 0) {
                    String[] lineValues = line.split(",");
                    xyValues.addAll(Arrays.asList(lineValues));
                }
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < xyValues.size(); i++) {
            if (i % 2 == 0)
                xValues.add(Double.parseDouble(xyValues.get(i)));

            if (i % 2 == 1) {
                double saltValue = saltRangeMin + (saltRangeMax - saltRangeMin) * rand.nextDouble();
                boolean decision = rand.nextBoolean();
                Double temp = Double.parseDouble(xyValues.get(i));

                if (decision)
                    temp += saltValue;
                else
                    temp -= saltValue;

                yValues.add(temp);
                xyValues.set(i, temp.toString());
            }
        }

        try {
            FileWriter fileWriter = new FileWriter(saltedCSV);

            for (int i = 0; i < xyValues.size(); i++) {
                fileWriter.write(xyValues.get(i) + ",");
                if (i % 2 == 0)
                    fileWriter.write(System.lineSeparator());
            }

            fileWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Graph plot = new Graph("Salter", "Salted Data With a Salt Range of [" + saltRangeMin + ", " + saltRangeMax + "]", xValues, yValues);
        plot.pack();
        plot.setVisible(true);
    }

    public void smoothData(String fileName, int windowValue) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        ArrayList<String> xyValues = new ArrayList<>();
        ArrayList<Double> doubleYValues = new ArrayList<>();
        ArrayList<Double> xValues = new ArrayList<>();
        File smoothedCSV = new File("smoothedCSV.csv");

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            for (line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                String[] lineValues = line.split(",");
                xyValues.addAll(Arrays.asList(lineValues));
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stats.setWindowSize(windowValue);

        for (int i = 0; i < xyValues.size(); i++) {
            if (i % 2 == 
