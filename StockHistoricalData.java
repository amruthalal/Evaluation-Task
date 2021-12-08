public class StockHistoricalData {

    private String date;
    private double open;
    private double high;
    private double low;
    private double close;
    private double adjClose;
    private int volume;
    private double smaOpen;
    private double smaHigh;
    private double smaLow;
    private double smaClose;
    private double smaAdjustedClose;
    private double smaVolume;

    public String getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public double getAdjClose() {
        return adjClose;
    }

    public double getVolume() {
        return volume;
    }

    public double getSmaOpen() {
        return smaOpen;
    }

    public double getSmaHigh() {
        return smaHigh;
    }

    public double getSmaLow() {
        return smaLow;
    }

    public double getSmaClose() {
        return smaClose;
    }

    public double getSmaAdjustedClose() {
        return smaAdjustedClose;
    }

    public double getSmaVolume() {
        return smaVolume;
    }

    public void setSmaOpen(double smaOpen) {
        this.smaOpen = smaOpen;
    }

    public void setSmaHigh(double smaHigh) {
        this.smaHigh = smaHigh;
    }

    public void setSmaLow(double smaLow) {
        this.smaLow = smaLow;
    }

    public void setSmaClose(double smaClose) {
        this.smaClose = smaClose;
    }

    public void setSmaAdjustedClose(double smaAdjustedClose) {
        this.smaAdjustedClose = smaAdjustedClose;
    }

    public void setSmaVolume(double smaVolume) {
        this.smaVolume = smaVolume;
    }

    public StockHistoricalData(String date, double open, double high, double low, double close, double adjClose,
            int volume, double smaOpen, double smaHigh, double smaLow, double smaClose, double smaAdjustedClose,
            double smaVolume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjClose = adjClose;
        this.volume = volume;
        this.smaOpen = smaOpen;
        this.smaHigh = smaHigh;
        this.smaLow = smaLow;
        this.smaClose = smaClose;
        this.smaAdjustedClose = smaAdjustedClose;
        this.smaVolume = smaVolume;
    }

}