import java.awt.*;

class HashMap{
    class Entity{
        int key;
        int value;
    }
    class Basket{
        Node head;
        class Node{
            Entity entity;
            Node next;
        }

        public Integer find(int key){ // O(1)
            Node node = head;
            while(node != null){
                if(node.entity.key == key){
                    return node.entity.value;
                }
                node = node.next;
            }
            return null;
        }

        public boolean push(Entity entity){ // O(1)
            Node node = new Node();
            node.entity = entity;

            if(head == null){
                head = node;
            }else{
                if(head.entity.key == entity.key){
                    return false;
                }else {
                    Node cur = head;
                    while (cur.next != null) {
                        if (cur.next.entity.key == entity.key) {
                            return false;
                        }
                        cur = cur.next;
                    }
                    cur.next = node;
                }
            }
            return true;
        }

        public boolean remove(int key){ // O(1)
            if(head != null) {
                if (head.entity.key == key) {
                    head = head.next;
                    return true;
                } else {
                    Node cur = head;
                    while (cur.next != null) {
                        if (cur.next.entity.key == key) {
                            cur.next = cur.next.next;
                            return true;
                        }
                        cur = cur.next;
                    }
                }
            }
            return false;
        }
    }

    static final int INIT_SIZE = 16;

    Basket[] baskets;

    public HashMap(){
        this(INIT_SIZE);
    }

    public HashMap(int size){
        baskets = new Basket[size];
    }

    private int getIndex(int key){
        return key % baskets.length;
    }

    public Integer find(int key){ // O(1)
        int index = getIndex(key);
        Basket basket = baskets[index];
        if(basket != null){
            return basket.find(key);
        }
        return null;
    }

    public boolean push(int key, int value){
        int index = getIndex(key);
        Basket basket = baskets[index];
        if(basket == null){
            basket = new Basket();
            baskets[index] = basket;
        }
        Entity entity = new Entity();
        entity.key = key;
        entity.value = value;
        return basket.push(entity);
    }

    public boolean remove(int key){
        int index = getIndex(key);
        Basket basket = baskets[index];
        if(basket != null){
            return basket.remove(key);
        }
        return false;
    }
}

class Tree{
    Node root;
    class Node{
        int value;
        private Color color;
        Node left;
        Node right;
    }

    private enum Color{
        RED,BLACK
    }

    private Node rebalnce(Node node){
        Node res = node;
        boolean needRebalance;
        do {
            needRebalance = false;
            if (res.right != null && res.right.color == Color.RED &&
                    (res.left == null || res.left.color == Color.BLACK)){
                needRebalance = true;
                res = rightSwap(res);
            }
            if (res.left != null && res.left.color == Color.RED &&
                    res.left.left != null && res.left.left.color == Color.RED){
               needRebalance = true;
               res = leftSwap(res);
            }
            if (res.left != null && res.left.color == Color.RED &&
                    res.right != null && res.right.color == Color.RED){
                needRebalance = true;
                colorSwap(res);
            }
        }
        while (needRebalance);
        return res;
    }

    private Node leftSwap(Node node) {
        Node left = node.left;
        Node between = left.right;
        left.right = node;
        node.left = between;
        left.color = node.color;
        node.color = Color.RED;
        return left;
    }

    private Node rightSwap(Node node) {
        Node right = node.right;
        Node between = right.left;
        right.left = node;
        node.right = between;
        right.color = node.color;
        node.color = Color.RED;
        return right;
    }

    private void colorSwap(Node node){
        node.right.color = Color.BLACK;
        node.left.color = Color.BLACK;
        node.color = Color.RED;
    }

    public Node find(int value){
        return find(root, value);
    }



    private Node find(Node node, int value){
        if(node == null){
            return null;
        }
        if(node.value == value){
            return node;
        }
        if(node.value < value){
            return find(node.right, value);
        }else{
            return find(node.left, value);
        }
    }

    public void insert(int value){
        if(root == null){
            root = new Node();
            root.value = value;
        }else {
            insert(root, value);
            root = rebalnce(root);
        }

        root.color = Color.BLACK;
    }

    public void insert(Node node, int value){
        if(node.value != value){
            if(node.value < value){
                if(node.right == null){
                    node.right = new Node();
                    node.right.value = value;
                    node.right.color = Color.RED;
                }else{
                    insert(node.right, value);
                    node.right = rebalnce(node.right);
                }
            }else{
                if(node.left == null){
                    node.left = new Node();
                    node.left.value = value;
                    node.left.color = Color.RED;
                }else{
                    insert(node.left, value);
                    node.left = rebalnce(node.left);
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
//        HashMap map = new HashMap();
//
//        map.push(1, 2);
//        map.push(3, 4);
//
//        System.out.println(map.find(3));
//        System.out.println(map.find(2));
//
//        map.remove(3);
//        map.push(2, 5);
//
//        System.out.println(map.find(3));
//        System.out.println(map.find(2));

        Tree tree = new Tree();

        for(int i=1; i<=5; i++){
            tree.insert(i);
        }

    }
}
