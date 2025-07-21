
//A class to represent an update of a stock

public class Update implements Comparable<Update> {
    private long timestamp;
    private float priceDifference;

    public Update(long timestamp, float priceDifference) {
        this.timestamp = timestamp;
        this.priceDifference = priceDifference;
    }

    public float getPriceDifference() {
        return priceDifference;
    }

    @Override
    public int compareTo(Update o) {
        return (int) (this.timestamp - o.timestamp);
    }
}



