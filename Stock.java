
//A class to represent a stock, each stock as his proper AVL tree to keep his updates

public class Stock implements Comparable<Stock> {
    private String stockId;
    private long timestamp;
    private float price;
    private AVLTree<Long, Update> updates;

    public Stock(String stockId, float price, long timestamp) {
        this.stockId = stockId;
        this.timestamp = timestamp;
        this.price = price;
        this.updates = new AVLTree<>();
    }

    @Override
    public int compareTo(Stock o) {
        return stockId.compareTo(o.stockId);
    }

    @Override
    public String toString() {
        return this.stockId;
    }

    public String getStockId() {
        return stockId;
    }

    public float getPrice() {
        return price;
    }

    public AVLTree<Long, Update> getUpdates() {
        return updates;
    }

    public void updatePrice(float change) {
        this.price += change;
    }
}
