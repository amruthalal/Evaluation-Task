import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Main {
    public static final String CSV_PATH = "./Data/reliance_nse.csv";
    public static final String OUTPUT_CSV_PATH = "./Data/reliance_nse_output.csv";

    // index of the fields
    public static final int DATE = 0;
    public static final int OPEN = 1;
    public static final int HIGH = 2;
    public static final int LOW = 3;
    public static final int CLOSE = 4;
    public static final int ADJACENT_CLOSE = 5;
    public static final int VOLUME = 6;
    public static final int SMA_OPEN = 7;
    public static final int SMA_HIGH = 8;
    public static final int SMA_LOW = 9;
    public static final int SMA_CLOSE = 10;
    public static final int SMA_ADJ_CLOSE = 11;
    public static final int SMA_VOLUME = 12;

    public static final int SMOOTHING = 2;
    public static final int MOVING_AVERAGE_PERIOD = 10;

    public static void main(String[] args) throws IOException {
        List<StockHistoricalData> stockHistoricalDataList = new ArrayList<>();
        stockHistoricalDataList = readCsv(CSV_PATH);
        stockHistoricalDataList = calculateSMA(stockHistoricalDataList);
        writeToCsv(OUTPUT_CSV_PATH, stockHistoricalDataList);

    }

    private static void writeToCsv(String outputCsvPath, List<StockHistoricalData> stockHistoricalDataList)
            throws IOException {
        FileWriter writer = new FileWriter(outputCsvPath);
        //first line of csv - heading
        writeLine(writer,
                Arrays.asList("Date", "Open", "High", "Low", "Close", "Adj_Close", "Volume", "SMA_Open", "SMA_High",
                        "SMA_Low", "SMA_Close", "SMA_Adj_Close", "SMA_Volume"));

        int length = stockHistoricalDataList.size();
        for (int i = 0; i < length; i = i + 1) {

            StockHistoricalData stockHistoricalData = stockHistoricalDataList.get(i);
            writeLine(writer,
                    Arrays.asList(stockHistoricalData.getDate(), Double.toString(stockHistoricalData.getOpen()),
                            Double.toString(stockHistoricalData.getHigh()),
                            Double.toString(stockHistoricalData.getLow()),
                            Double.toString(stockHistoricalData.getClose()),
                            Double.toString(stockHistoricalData.getAdjClose()),
                            Double.toString(stockHistoricalData.getVolume()),
                            Double.toString(stockHistoricalData.getSmaOpen()),
                            Double.toString(stockHistoricalData.getSmaHigh()),
                            Double.toString(stockHistoricalData.getSmaLow()),
                            Double.toString(stockHistoricalData.getSmaClose()),
                            Double.toString(stockHistoricalData.getSmaAdjustedClose()),
                            Double.toString(stockHistoricalData.getSmaVolume())));

        }
        writer.flush();
        writer.close();

    }

    private static List<StockHistoricalData> calculateSMA(List<StockHistoricalData> stockHistoricalDataList) {

        double smaOpen = 0;
        double smaHigh = 0;
        double smaLow = 0;
        double smaClose = 0;
        double smaAdjClose = 0;
        double smaVolume = 0;

        for (int i = MOVING_AVERAGE_PERIOD; i < stockHistoricalDataList.size(); i++) {

            smaOpen = getSmaOpen(stockHistoricalDataList, i);
            stockHistoricalDataList.get(i).setSmaOpen(smaOpen);

            smaHigh = getSmaHigh(stockHistoricalDataList, i);
            stockHistoricalDataList.get(i).setSmaHigh(smaHigh);

            smaLow = getSmaLow(stockHistoricalDataList, i);
            stockHistoricalDataList.get(i).setSmaLow(smaLow);

            smaClose = getSmaClose(stockHistoricalDataList, i);
            stockHistoricalDataList.get(i).setSmaClose(smaClose);

            smaAdjClose = getSmaAdjClose(stockHistoricalDataList, i);
            stockHistoricalDataList.get(i).setSmaAdjustedClose(smaAdjClose);

            smaVolume = getSmaVolume(stockHistoricalDataList, i);
            stockHistoricalDataList.get(i).setSmaVolume(smaVolume);

        }
        return stockHistoricalDataList;

    }

    private static double getSmaOpen(List<StockHistoricalData> stockHistoricalDataList, int index) {
        double sum = 0;
        for (int j = 1; j <= MOVING_AVERAGE_PERIOD; j++) {
            sum += stockHistoricalDataList.get(index - j).getOpen();
        }
        return sum / MOVING_AVERAGE_PERIOD;
    }

    private static double getSmaHigh(List<StockHistoricalData> stockHistoricalDataList, int index) {
        double sum = 0;
        for (int j = 1; j <= MOVING_AVERAGE_PERIOD; j++) {
            sum += stockHistoricalDataList.get(index - j).getHigh();
        }
        return sum / MOVING_AVERAGE_PERIOD;
    }

    private static double getSmaLow(List<StockHistoricalData> stockHistoricalDataList, int index) {
        double sum = 0;
        for (int j = 1; j <= MOVING_AVERAGE_PERIOD; j++) {
            sum += stockHistoricalDataList.get(index - j).getLow();
        }
        return sum / MOVING_AVERAGE_PERIOD;
    }

    private static double getSmaClose(List<StockHistoricalData> stockHistoricalDataList, int index) {
        double sum = 0;
        for (int j = 1; j <= MOVING_AVERAGE_PERIOD; j++) {
            sum += stockHistoricalDataList.get(index - j).getClose();
        }
        return sum / MOVING_AVERAGE_PERIOD;
    }

    private static double getSmaAdjClose(List<StockHistoricalData> stockHistoricalDataList, int index) {
        double sum = 0;
        for (int j = 1; j <= MOVING_AVERAGE_PERIOD; j++) {
            sum += stockHistoricalDataList.get(index - j).getAdjClose();
        }
        return sum / MOVING_AVERAGE_PERIOD;
    }

    private static double getSmaVolume(List<StockHistoricalData> stockHistoricalDataList, int index) {
        double sum = 0;
        for (int j = 1; j <= MOVING_AVERAGE_PERIOD; j++) {
            sum += stockHistoricalDataList.get(index - j).getVolume();
        }
        return sum / MOVING_AVERAGE_PERIOD;
    }

    private static List<StockHistoricalData> readCsv(String pathToCsv) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
        String row;
        List<StockHistoricalData> stockHistoricalDataList = new ArrayList<>();
        csvReader.readLine();
        while ((row = csvReader.readLine()) != null) {
            List<String> data = new ArrayList<>();
            data.addAll(Arrays.asList(row.split(",")));
            data = dataValidation(data);
            StockHistoricalData stockHistoricalData =
                    new StockHistoricalData(data.get(DATE), Double.parseDouble(data.get(OPEN)),
                            Double.parseDouble(data.get(HIGH)), Double.parseDouble(data.get(LOW)),
                            Double.parseDouble(data.get(CLOSE)), Double.parseDouble(data.get(ADJACENT_CLOSE)),
                            Integer.parseInt(data.get(VOLUME)), Double.parseDouble(data.get(SMA_OPEN)),
                            Double.parseDouble(data.get(SMA_HIGH)), Double.parseDouble(data.get(SMA_LOW)),
                            Double.parseDouble(data.get(SMA_CLOSE)), Double.parseDouble(data.get(SMA_ADJ_CLOSE)),
                            Double.parseDouble(data.get(SMA_VOLUME)));

            stockHistoricalDataList.add(stockHistoricalData);
        }
        csvReader.close();
        return stockHistoricalDataList;
    }

    private static List<String> dataValidation(List<String> data) {
        Collections.replaceAll(data, "null", "0");
        Collections.replaceAll(data, "\"\"", "0");
        return data;
    }

    private static void writeLine(Writer w, List<String> values) throws IOException {

        boolean first = true;

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            //To avoid comma before first value
            if (!first) {
                sb.append(',');
            }
            sb.append(value);
            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());

    }
}