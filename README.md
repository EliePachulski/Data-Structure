# Stock Management System using Augmented AVL Trees

This repository contains an advanced Java implementation of a stock management system built entirely on top of self-balanced AVL Trees.

The project was developed as part of a Data Structures and Algorithms course at the Technion – Israel Institute of Technology.

Rather than implementing a standalone AVL tree, the project demonstrates how augmented balanced trees can be used to build an efficient indexing system capable of supporting dynamic updates and logarithmic-time queries.

---

## Project Overview

The system manages a collection of stocks while maintaining multiple synchronized AVL-tree indexes that support efficient updates and range queries.

Each stock is identified by:

- Stock ID
- Current price
- Update history

Every modification automatically keeps all indexes consistent.

---

## Architecture

The project maintains three different AVL trees simultaneously.

```text
                     StockManager
                           │
        ┌──────────────────┴──────────────────┐
        │                                     │
 AVL indexed by Stock ID             AVL indexed by Price
        │                                     │
        └──────────────┬──────────────────────┘
                       │
                   Stock object
                       │
             AVL indexed by Timestamp
                (price update history)
```

### Primary Index

Stores stocks ordered by their unique Stock ID.

Used for:

- Fast stock lookup
- Insertions
- Deletions
- Price updates

---

### Secondary Index

Stores the same stock objects ordered by their current price.

A Stock ID is used as a tie-breaker, allowing multiple stocks to share the same price while preserving a total ordering.

This index enables efficient:

- Price range counting
- Ordered price queries
- Range retrieval

---

### Update History

Every stock owns its own AVL tree containing all historical price updates.

Each update stores:

- Timestamp
- Price difference

This allows updates to be inserted or removed while keeping the current stock price consistent.

---

## Features

### Stock Management

- Initialize the system
- Add new stocks
- Remove existing stocks
- Update stock prices
- Retrieve current stock prices

### Historical Updates

- Store every price modification
- Remove updates by timestamp
- Automatically restore the previous price after removing an update

### Range Queries

- Count stocks inside a price interval
- Retrieve all stock IDs inside a price interval
- Results returned in sorted price order

---

## AVL Tree Implementation

The project includes a fully generic AVL Tree implementation supporting:

- Insertion
- Deletion
- Search
- Left and right rotations
- Automatic balancing
- Minimum and maximum lookup
- Ordered traversal
- Range queries

Unlike a standard AVL implementation, every node is augmented with additional metadata used to answer order-statistics queries efficiently.

---

## Augmented Data Structure

Each AVL node maintains:

- Key
- Stored object
- Height
- Left subtree size
- Left child
- Right child

Maintaining the left subtree size during rotations allows the tree to support efficient range-count operations without traversing the entire structure.

---

## Design Highlights

### Generic Implementation

The AVL tree is fully generic.

It supports arbitrary comparable keys and arbitrary comparable stored objects.

### Multiple Indexes

Instead of duplicating information, both AVL trees reference the same Stock objects while maintaining different orderings.

### Consistency

Whenever a stock price changes:

1. The stock is removed from the price index.
2. Its price is updated.
3. The stock is reinserted into the price index.

This guarantees that both indexes remain synchronized at all times.

### Duplicate Price Handling

Multiple stocks may have identical prices.

To preserve deterministic ordering, comparisons first use the price and then the Stock ID as a tie-breaker.

---

## Technologies

- Java
- Object-Oriented Programming
- Generic Programming
- AVL Trees
- Self-Balancing Binary Search Trees
- Order Statistics
- Tree Rotations
- Dynamic Data Structures

---

## Repository Contents

- Generic AVL Tree implementation
- Stock management system
- Stock model
- Update history model
- Comprehensive testing suite

---

## Topics Covered

- Data Structures
- Algorithms
- AVL Trees
- Binary Search Trees
- Self-Balancing Trees
- Order Statistics
- Range Queries
- Generic Programming
- Object-Oriented Design
- Java

---

## Key Takeaways

This project demonstrates how an augmented self-balancing binary search tree can be used to build a high-performance indexing system.

By combining multiple synchronized AVL trees with additional node metadata, the system supports efficient dynamic updates, historical tracking, and logarithmic-time search and range operations while maintaining consistency across all indexes.
