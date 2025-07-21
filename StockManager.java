
//The manager class, we manage two AVL tree one who class the stocks by ID and one who class them by price with ID as tiebreaker

public class StockManager {
    AVLTree<String, Stock> stocks;
    AVLTree<Float, Stock> priceStocks;

    public StockManager() {
        this.stocks = new AVLTree<>();
        this.priceStocks = new AVLTree<>();
    }

    // 1. Initialize the system
    public void initStocks() {
        this.stocks = new AVLTree<>();
        this.priceStocks = new AVLTree<>();
    }

    // 2. Add a new stock
    public void addStock(String stockId, long timestamp, Float price) {
        if (stockId == null) {
            throw new IllegalArgumentException("Stock id cannot be null");
        }
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Initial price must be positive");
        }
        if (timestamp < 0) {
            throw new IllegalArgumentException("Timestamp must be positive");
        }
        if (stocks.search(stockId) != null){
            throw new IllegalArgumentException("Stock ID already exists");
        }
        Stock stock = new Stock(stockId, price, timestamp);
        stocks.insert(stockId, stock);
        priceStocks.insert(price, stock);
    }

    // 3. Remove a stock
    public void removeStock(String stockId) {
        if (stockId == null) {
            throw new IllegalArgumentException("Stock id cannot be null");
        }
        Stock stock = stocks.search(stockId);
        if (stock == null) {
            throw new IllegalArgumentException("Stock does not exist, cannot remove");
        }
        stocks.delete(stockId, stock);
        priceStocks.delete(stock.getPrice(), stock);
    }


    // 4. Update a stock price
    public void updateStock(String stockId, long timestamp, Float priceDifference) {
        if (stockId == null) {
            throw new IllegalArgumentException("Stock id cannot be null");
        }
        if (timestamp < 0) {
            throw new IllegalArgumentException("Timestamp must be positive");
        }
        Stock stock = stocks.search(stockId);
        if (stock == null) {
            throw new IllegalArgumentException("Cannot update a non-existent stockId");
        }
        if (priceDifference == null || priceDifference == 0) {
            throw new IllegalArgumentException("priceDifference must be non-zero");
        }
        Update update = new Update(timestamp, priceDifference);
        stock.getUpdates().insert(timestamp, update);
        priceStocks.delete(stock.getPrice(), stock);
        stock.updatePrice(priceDifference);
        priceStocks.insert(stock.getPrice(), stock);
    }


    // 5. Get the current price of a stock
    public Float getStockPrice(String stockId) {
        if (stockId == null) {
            throw new IllegalArgumentException("Stock id cannot be null");
        }
        Stock stock = stocks.search(stockId);
        if (stock == null) {
            throw new IllegalArgumentException("Stock does not exist");
        }
        return stock.getPrice();
    }

    // 6. Remove a specific timestamp from a stock's history
    public void removeStockTimestamp(String stockId, long timestamp) {
        if (stockId == null) {
            throw new IllegalArgumentException("Stock id cannot be null");
        }
        if (timestamp < 0) {
            throw new IllegalArgumentException("Timestamp must be positive");
        }
        Stock stock = stocks.search(stockId);
        if (stock == null) {
            throw new IllegalArgumentException("Stock does not exist");
        }
        Update update = stock.getUpdates().search(timestamp);
        if (update == null) {
            throw new IllegalArgumentException("Update does not exist");
        }
        float priceDifference = update.getPriceDifference();
        stock.getUpdates().delete(timestamp, update);
        priceStocks.delete(stock.getPrice(), stock);
        stock.updatePrice(-priceDifference);
        priceStocks.insert(stock.getPrice(), stock);
    }


    // 7. Get the amount of stocks in a given price range
    public int getAmountStocksInPriceRange(Float price1, Float price2) {
        if (price1 == null || price2 == null) {
            throw new IllegalArgumentException("Price range cannot be null");
        }
        String upperBoundString = stocks.findMaximumKey() + 'a';
        Stock upperBoundStock = new Stock(upperBoundString,price2,0);
        if (stocks.findMinimumKey().equals("") && stocks.findMinimumObject().getPrice() == price1) {
            return priceStocks.countInRangeSpecial(price1, price2,stocks.findMinimumObject(),upperBoundStock);
        }
        Stock lowerBoundStock = new Stock("",price1,0);
        return priceStocks.countInRange(price1, price2,lowerBoundStock,upperBoundStock);
    }


    // 8. Get a list of stock IDs within a given price range
    public String[] getStocksInPriceRange(Float price1, Float price2) {
        if (price1 == null || price2 == null) {
            throw new IllegalArgumentException("Price range cannot be null");
        }
        int count = getAmountStocksInPriceRange(price1, price2);
        Stock lowerBoundStock = new Stock("",price1,0);
        String upperBoundString = stocks.findMaximumKey() + 'a';
        Stock upperBoundStock = new Stock(upperBoundString,price2,0);
        String[] result = priceStocks.getNodesInRange(price1, price2,lowerBoundStock ,upperBoundStock ,count);
        priceStocks.setRangeTableIndex(0);
        return result;
    }
}
