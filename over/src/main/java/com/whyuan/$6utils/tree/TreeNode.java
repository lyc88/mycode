package com.whyuan.$6utils.tree;

import java.util.*;

public class TreeNode<T> implements Iterable<TreeNode<T>> {

    public T data;
    public T ID;
    public T layer;
    public T word;
    public TreeNode<T> parent;
    public List<TreeNode<T>> children;


    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

    private List<TreeNode<T>> elementsIndex;

    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<TreeNode<T>>();
        this.elementsIndex = new LinkedList<TreeNode<T>>();
        this.elementsIndex.add(this);
    }

    public TreeNode(T data, T ID, T layer,T word) {
        this.data = data;
        this.ID = ID;
        this.word = word;
        this.layer = layer;
        this.children = new LinkedList<TreeNode<T>>();
        this.elementsIndex = new LinkedList<TreeNode<T>>();
        this.elementsIndex.add(this);
    }

    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        this.registerChildForSearch(childNode);
        return childNode;
    }


    public TreeNode<T> addChild(T child, T ID, T layer, T word) {
        TreeNode<T> childNode = new TreeNode<T>(child, ID, layer,word);
        childNode.parent = this;
        this.children.add(childNode);
        this.registerChildForSearch(childNode);
        return childNode;
    }

    public int getLevel() {
        if (this.isRoot()) {
            return 0;
        } else {
            return parent.getLevel() + 1;
        }
    }

    public String getParentData() {
        if (this.isRoot()) {
            return "";
        } else {
            return this.data.toString() + " " + parent.getParentData().toString();
        }
    }

    public String getParentIDData() {
        if (this.isRoot()) {
            return "";
        } else {
            return this.ID.toString() + " " + parent.getParentIDData().toString();
        }
    }

    private void registerChildForSearch(TreeNode<T> node) {
        elementsIndex.add(node);
        if (parent != null) {
            parent.registerChildForSearch(node);
        }
    }

    public TreeNode<T> findTreeNode(Comparable<T> cmp) {
        for (TreeNode<T> element : this.elementsIndex) {
            T elData = element.data;
            if (cmp.compareTo(elData) == 0) {
                return element;
            }
        }
        return null;
    }

    public TreeNode<T> findTreeNodeByID(String id) {
        for (TreeNode<T> element : this.elementsIndex) {
            T ID = element.ID;
            if (ID.toString().equals(id)) {
                return element;
            }
        }
        return null;
    }

    public Map<String, List<TreeNode<T>>> findListTreeNode(Set<String> strings) {
        HashMap<String, List<TreeNode<T>>> map = new HashMap<>();

        for (TreeNode<T> element : this.elementsIndex) {
            T elData = element.data;
            if (strings.contains(elData.toString())) {
                List<TreeNode<T>> treeNodes = map.get(elData.toString());
                if (treeNodes == null || treeNodes.size() == 0) {
                    treeNodes = new ArrayList<TreeNode<T>>();
                    map.put(elData.toString(), treeNodes);
                }
                treeNodes.add(element);
            }

        }
        return map;
    }

    @Override
    public String toString() {
        return data != null ? data.toString() : "[data null]";
    }

    @Override
    public Iterator<TreeNode<T>> iterator() {
        TreeNodeIter<T> iter = new TreeNodeIter<T>(this);
        return iter;
    }

    public List<TreeNode<T>> getElementsIndex() {
        return elementsIndex;
    }


}
