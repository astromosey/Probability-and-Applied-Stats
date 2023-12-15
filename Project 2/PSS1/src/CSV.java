import java.io.*;
import java.lang.Math;
import java.util.*;

public class CSV {
    
    public void generatePlot(String fileName, int min, int max, double interval) {
        File csvFile = new File(fileName + ".csv");
        String header = "x, y, y = x^2 + 2x + 1";
        
        try {
            FileWriter fileWriter = new FileWriter(csvFile);
            fileWriter.write(header + System.lineSeparator());
            
            double x = min + interval;
            
            for (double counter = min; counter < max; counter += interval) {
                if (!(counter > min)) {
                    double y = Math.pow(min, 2) + 2 * min + 1;
                    fileWriter.write(min + "," + y + System.lineSeparator());
                }
                
                if (counter + interval <= max) {
                    double y = Math.pow(x, 2) + 2 * x + 1;
                    fileWriter.write(x + "," + y + System.lineSeparator());
                    x += interval;
                }
            }
            
            fileWriter.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSalt(String fileName, int saltMin, int saltMax) {
        ArrayList<String> xyValues = new ArrayList<String>();
        File saltedFile = new File("salted" + fileName);
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
            if (i % 2 == 1) {
                double saltValue = saltMin + (saltMax - saltMin) * rand.nextDouble();
                boolean decision = rand.nextBoolean();
                Double temp = Double.parseDouble(xyValues.get(i));

                if (decision)
                    temp += saltValue;
                else
                    temp -= saltValue;

                xyValues.set(i, temp.toString());
            }
        }

        try {
            FileWriter fileWriter = new FileWriter(saltedFile);

            for (int i = 0; i < xyValues.size(); i++) {
                fileWriter.write(xyValues.get(i) + ",");
                if (i % 2 != 0)
                    fileWriter.write(System.lineSeparator());
            }

            fileWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void smoothData(String fileName, int windowSize) {
        ArrayList<String> xyValues = new ArrayList<String>();
        File smoothedFile = new File("smoothedCSV.csv");

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            int count = 0;

            for (line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine(), count++) {
                String[] lineValues = line.split(",");
                xyValues.addAll(Arrays.asList(lineValues));
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < xyValues.size(); i++) {
            if (i % 2 == 1) {
                ArrayList<Double> yRanges = new ArrayList<Double>();
                Double yAverage;
                Double sum = 0.0;
                Double temp = Double.parseDouble(xyValues.get(i));
                int surroundingIndex = 0;
                int maxIndex = xyValues.size();
                yRanges.add(temp);

                for (int j = 1; j <= windowSize; j++) {
                    surroundingIndex += 2;

                    if (i + surroundingIndex <= maxIndex) {
                        yRanges.add(Double.parseDouble(xyValues.get(i + surroundingIndex)));
                    }

                    if (i - surroundingIndex >= 1) {
                        yRanges.add(Double.parseDouble(xyValues.get(i - surroundingIndex)));
                    }
                }

                for (int k = 0; k < yRanges.size(); k++) {
                    sum += yRanges.get(k);
                }

                yAverage = sum / yRanges.size();
                xyValues.set(i, yAverage.toString());
            }
        }

        try {
            FileWriter fileWriter = new FileWriter(smoothedFile);

            for (int i = 0; i < xyValues.size(); i++) {
                fileWriter.write(xyValues.get(i) + ",");
                if (i % 2 != 0)
                    fileWriter.write(System.lineSeparator());
            }

            fileWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
