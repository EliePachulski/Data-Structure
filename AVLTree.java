
// A generic class to represent an AVL balanced tree

public class AVLTree<E extends Comparable<E>, T extends Comparable<T>> {

    private Node root;
    private int rangeTableIndex;


    //Inner class that represents a node in the tree
    class Node {
        E key;
        T object;
        int height;
        int leftSize;
        Node left, right;

        public Node(E key, T object) {
            this.key = key;
            this.object = object;
            this.height = 1;
            this.leftSize = 0;
            this.left = this.right = null;
        }

        // Define a comparison based on key and object as tie-breaker
        private int compare(E otherKey, T otherObject) {
            if (key.compareTo(otherKey) != 0) {
                return key.compareTo(otherKey);
            } else {
                return object.compareTo(otherObject);
            }
        }
    }


    //Constructor
    public AVLTree() {
        root = null;
    }


    // Sets the range table index, used in range queries
    public void setRangeTableIndex(int rangeTableIndex) {
        this.rangeTableIndex = rangeTableIndex;
    }


    // Performs a right rotation to balance the tree
    private Node rightRotate(Node node) {
        Node temp = node.left;
        Node tempRight = temp.right;

        temp.right = node;
        node.left = tempRight;

        node.height = 1 + Math.max(height(node.left), height(node.right));
        temp.height = 1 + Math.max(height(temp.left), height(temp.right));

        temp.leftSize = size(temp.left);
        node.leftSize = size(node.left);

        return temp;
    }


    // Performs a left rotation to balance the tree
    private Node leftRotate(Node node) {
        Node temp = node.right;
        Node tempLeft = temp.left;

        temp.left = node;
        node.right = tempLeft;

        node.height = 1 + Math.max(height(node.left), height(node.right));
        temp.height = 1 + Math.max(height(temp.left), height(temp.right));

        temp.leftSize = size(temp.left);
        node.leftSize = size(node.left);

        return temp;
    }


    // Calculates the balance factor of a node
    private int getBalance(Node node) {
        return node == null ? 0 : height(node.right) - height(node.left);
    }


    // Returns the height of a node
    private int height(Node node) {
        return node == null ? 0 : node.height;
    }


    // Returns the size of a node's subtree
    private int size(Node node) {
        return node == null ? 0 : node.leftSize + 1 + size(node.right);
    }


    // Finds the maximum key in a subtree
    public E findMaximumKey() {
        return findMaximumKey(root);
    }

    private E findMaximumKey(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node.key;
    }


    // Finds the minimum key in a subtree
    public E findMinimumKey() {
        return findMinimumKey(root);
    }

    private E findMinimumKey(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.key;
    }


    // Finds the minimum object in a subtree
    public T findMinimumObject() {
        return findMinimumObject(root);
    }

    private T findMinimumObject(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.object;
    }


    // Checks if the tree is empty
    public boolean isEmpty() {
        return root == null;
    }


    // Searches for an object by its key and returns it
    public T search(E key) {
        Node result = search(root, key);
        return (result != null) ? result.object : null;
    }


    // Helper function to search for a node by its key O(Log(n))
    private Node search(Node node, E key) {
        if (node == null || node.key.equals(key)) {
            return node;
        }
        if (key.compareTo(node.key) < 0) {
            return search(node.left, key);
        }
        return search(node.right, key);
    }


    // Inserts a key-object pair into the AVL tree
    public void insert(E key, T object) {
        root = insert(root, key, object);
    }


    // Helper function to insert a key-object pair into the AVL tree O(Log(n))
    private Node insert(Node node, E key, T object) {
        if (node == null) {
            return new Node(key, object);
        }
        if (node.compare(key, object) > 0) {
            node.left = insert(node.left, key, object);
            node.leftSize++;
        } else if (node.compare(key, object) < 0) {
            node.right = insert(node.right, key, object);
        } else {
            return node;
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        if (balance > 1) {
            if (height(node.right.right) > height(node.right.left)) {
                return leftRotate(node);
            } else {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        if (balance < -1) {
            if (height(node.left.left) > height(node.left.right)) {
                return rightRotate(node);
            } else {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        return node;
    }


    // Deletes a key-object pair from the AVL tree
    public void delete(E key, T object) {
        if (isEmpty()) {
            return;
        }
        root = delete(root, key, object);
    }


    // Helper function to delete a key-object pair from the AVL tree O(Log(n))
    private Node delete(Node node, E key, T object) {
        if (node == null) {
            return null;
        }
        if (node.compare(key, object) > 0) {
            node.left = delete(node.left, key, object);
            node.leftSize--;
        } else if (node.compare(key, object) < 0) {
            node.right = delete(node.right, key, object);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            node.key = findMinimumKey(node.right);
            node.object = findMinimumObject(node.right);
            node.right = delete(node.right, node.key, node.object);
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);
        if (balance > 1) {
            if (height(node.right.right) > height(node.right.left)) {
                return leftRotate(node);
            } else {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        if (balance < -1) {
            if (height(node.left.left) > height(node.left.right)) {
                return rightRotate(node);
            } else {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        return node;
    }


    // Return the rank (how many node have an inferior key) O(Log(n))
    private int getRank(Node node, E key, T object) {
        if (node == null) {
            throw new IllegalArgumentException();
        }
        if (node.compare(key, object) == 0) {
            return node.leftSize;
        } else if (node.compare(key, object) > 0) {
            return getRank(node.left, key, object);
        } else {
            int rightRank = getRank(node.right, key, object);
            return rightRank + 1 + (node.left != null ? node.leftSize : 0);
        }
    }


    // Counts the number of nodes within a specified key range in O(Log(n))
    public int countInRange(E lowerK, E upperK, T lowerObj, T upperObj) {
        insert(lowerK, lowerObj);
        insert(upperK, upperObj);
        int res = getRank(root, upperK, upperObj) - getRank(root, lowerK, lowerObj) - 1;
        delete(lowerK, lowerObj);
        delete(upperK, upperObj);
        return res;
    }


    // In a very very very rare and special case
    public int countInRangeSpecial(E lowerK, E upperK, T lowerObj, T upperObj) {
        insert(upperK, upperObj);
        int res = getRank(root, upperK, upperObj) - getRank(root, lowerK, lowerObj);
        delete(upperK, upperObj);
        return res;
    }


    // Retrieves all objects within a specified key range
    public String[] getNodesInRange(E lowerK, E upperK, T lowerObj, T upperObj, int size) {
        rangeTableIndex = 0;
        String[] result = new String[size];
        fillNodesInRange(root, lowerK, upperK, lowerObj, upperObj, result);
        return result;
    }


    // Helper function to fill an array with objects in the specified key range O(log(n) + K)
    private void fillNodesInRange(Node node, E lowerK, E upperK, T lowerObj, T upperObj, String[] result) {
        if (node == null) {
            return;
        }

        if (node.compare(lowerK, lowerObj) >= 0) {
            fillNodesInRange(node.left, lowerK, upperK, lowerObj, upperObj, result);
        }

        if (node.compare(lowerK, lowerObj) >= 0 && node.compare(upperK, upperObj) <= 0) {
            result[rangeTableIndex] = node.object.toString();
            rangeTableIndex++;
        }

        if (node.compare(upperK, upperObj) <= 0) {
            fillNodesInRange(node.right, lowerK, upperK, lowerObj, upperObj, result);
        }
    }
}



