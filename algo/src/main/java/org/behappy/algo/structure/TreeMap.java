package org.behappy.algo.structure;

import org.behappy.algo.structure.BinarySearchTree.INodeCreator;
import org.behappy.algo.structure.BinarySearchTree.Node;
import org.behappy.algo.structure.interfaces.IMap;

import java.util.ArrayList;
import java.util.List;

/**
 * An tree used to store key->values pairs, this is an implementation of an associative array.
 * <p>
 * This implementation is a composition of a AVLTree as the backing structure.
 * <p>
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 * @see <a href="https://en.wikipedia.org/wiki/AVL_tree">AVL Tree (Wikipedia)</a>
 * @see <a href="https://en.wikipedia.org/wiki/Associative_array">Associative Array (Wikipedia)</a>
 * <br>
 */
@SuppressWarnings("unchecked")
public class TreeMap<K extends Comparable<K>, V> implements IMap<K, V> {

    private final BinarySearchTree.INodeCreator<K> creator = new BinarySearchTree.INodeCreator<K>() {
        /**
         * {@inheritDoc}
         */
        @Override
        public BinarySearchTree.Node<K> createNewNode(BinarySearchTree.Node<K> parent, K id) {
            return (new TreeMapNode<K, V>(parent, id, null));
        }
    };

    private AVLTree<K> tree = null;

    public TreeMap() {

        tree = new AVLTree<K>(creator);
    }

    /**
     * Constructor with external Node creator.
     */
    public TreeMap(INodeCreator<K> creator) {
        tree = new AVLTree<K>(creator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K key, V value) {
        V prev = null;
        BinarySearchTree.Node<K> node = tree.addValue(key);

        TreeMapNode<K, V> treeMapNode = (TreeMapNode<K, V>)node;
        if (treeMapNode.value != null)
            prev = treeMapNode.value;
        treeMapNode.value = value;

        return prev;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(K key) {
        BinarySearchTree.Node<K> node = tree.getNode(key);
        TreeMapNode<K, V> mapNode = (TreeMapNode<K, V>)node;
        return mapNode.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(K key) {
        return tree.contains(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(K key) {
        Node<K> node = tree.removeValue(key);
        TreeMapNode<K, V> treeMapNode = (TreeMapNode<K, V>)node;
        V value = null;
        if (treeMapNode != null) {
            value = treeMapNode.value;
            treeMapNode.id = null;
            treeMapNode.value = null;
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        tree.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return tree.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        java.util.Set<K> keys = new java.util.HashSet<K>();
        Node<K> node = tree.root;
        if (node != null && !validate(node, keys))
            return false;
        return (keys.size() == size());
    }

    @SuppressWarnings("rawtypes")
    private boolean validate(Node<K> node, java.util.Set<K> keys) {
        if (!(node instanceof TreeMap.TreeMapNode tmn))
            return false;

        K k = (K)tmn.id;
        V v = (V)tmn.value;
        if (k == null || v == null)
            return false;
        if (keys.contains(k))
            return false;
        keys.add(k);
        if (tmn.lesser != null && !validate(tmn.lesser, keys))
            return false;
        return tmn.greater == null || validate(tmn.greater, keys);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Map<K, V> toMap() {
        return (new JavaCompatibleTreeMap<K, V>(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TreeMapPrinter.getString(this);
    }

    protected static class TreeMapNode<K extends Comparable<K>, V> extends AVLTree.AVLNode<K> {

        protected V value = null;

        protected TreeMapNode(RedBlackTree.Node<K> parent, K key, V value) {
            super(parent, key);
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            String builder = super.toString() +
                    "value = " + value + "\n";
            return builder;
        }
    }

    protected static class TreeMapPrinter {

        public static <K extends Comparable<K>, V> String getString(TreeMap<K, V> map) {
            if (map.tree.root == null) return "Tree has no nodes.";
            return getString((TreeMapNode<K, V>)map.tree.root, "", true);
        }

        private static <K extends Comparable<K>, V> String getString(TreeMapNode<K, V> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            builder.append(prefix + (isTail ? "└── " : "├── ") + ((node.id != null) ? (node.id + " = " + node.value) : node.id) + "\n");
            List<TreeMapNode<K, V>> children = null;
            if (node.lesser != null || node.greater != null) {
                children = new ArrayList<TreeMapNode<K, V>>(2);
                if (node.lesser != null) children.add((TreeMapNode<K, V>)node.lesser);
                if (node.greater != null) children.add((TreeMapNode<K, V>)node.greater);
            }
            if (children != null) {
                for (int i = 0; i < children.size() - 1; i++) {
                    builder.append(getString(children.get(i), prefix + (isTail ? "    " : "│   "), false));
                }
                if (children.size() >= 1) {
                    builder.append(getString(children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "), true));
                }
            }

            return builder.toString();
        }
    }

    private static class JavaCompatibleIteratorWrapper<K extends Comparable<K>, V> implements java.util.Iterator<java.util.Map.Entry<K, V>> {

        private TreeMap<K, V> map = null;
        private java.util.Iterator<java.util.Map.Entry<K, V>> iter = null;
        private java.util.Map.Entry<K, V> lastEntry = null;

        public JavaCompatibleIteratorWrapper(TreeMap<K, V> map, java.util.Iterator<java.util.Map.Entry<K, V>> iter) {
            this.map = map;
            this.iter = iter;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            if (iter == null) return false;
            return iter.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Map.Entry<K, V> next() {
            if (iter == null) return null;

            lastEntry = iter.next();
            return lastEntry;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            if (iter == null || lastEntry == null) return;

            map.remove(lastEntry.getKey());
            iter.remove();
        }
    }

    private static class JavaCompatibleMapEntry<K extends Comparable<K>, V> extends java.util.AbstractMap.SimpleEntry<K, V> {

        private static final long serialVersionUID = 3282082818647198608L;

        public JavaCompatibleMapEntry(K key, V value) {
            super(key, value);
        }
    }

    private static class JavaCompatibleTreeMap<K extends Comparable<K>, V> extends java.util.AbstractMap<K, V> {

        private TreeMap<K, V> map = null;

        protected JavaCompatibleTreeMap(TreeMap<K, V> map) {
            this.map = map;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V put(K key, V value) {
            return map.put(key, value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V remove(Object key) {
            return map.remove((K)key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            map.clear();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsKey(Object key) {
            return map.contains((K)key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return map.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Set<Entry<K, V>> entrySet() {
            java.util.Set<Entry<K, V>> set = new java.util.HashSet<Entry<K, V>>() {

                private static final long serialVersionUID = 1L;

                /**
                 * {@inheritDoc}
                 */
                @Override
                public java.util.Iterator<Entry<K, V>> iterator() {
                    return (new JavaCompatibleIteratorWrapper<K, V>(map, super.iterator()));
                }
            };
            if (map.tree != null && map.tree.root != null) {
                Node<K> n = map.tree.root;
                levelOrder(n, set);
            }
            return set;
        }

        private void levelOrder(Node<K> node, java.util.Set<Entry<K, V>> set) {
            TreeMapNode<K, V> tmn = null;
            if (node instanceof TreeMapNode) {
                tmn = (TreeMapNode<K, V>)node;
                Entry<K, V> entry = new JavaCompatibleMapEntry<K, V>(tmn.id, tmn.value);
                set.add(entry);
            }

            if (node.lesser != null) levelOrder(node.lesser, set);
            if (node.greater != null) levelOrder(node.greater, set);
        }
    }
}
