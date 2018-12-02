/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.example.luisbb.botanasdivalapp.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DataCenter {

    private static DataCenter centerRepository;

    private ArrayList<Categories> listOfCategory = new ArrayList<>();
    private ConcurrentHashMap<String, ArrayList<Products>> mapOfProductsInCategory = new ConcurrentHashMap<>();
    private List<Products> listOfProductsInShoppingList = Collections.synchronizedList(new ArrayList<Products>());
    private List<Set<String>> listOfItemSetsForDataMining = new ArrayList<>();

    public static DataCenter getCenterRepository() {

        if (null == centerRepository) {
            centerRepository = new DataCenter();
        }
        return centerRepository;
    }


    public List<Products> getListOfProductsInShoppingList() {
        return listOfProductsInShoppingList;
    }

    public void setListOfProductsInShoppingList(ArrayList<Products> getShoppingList) {
        this.listOfProductsInShoppingList = getShoppingList;
    }

    public Map<String, ArrayList<Products>> getMapOfProductsInCategory() {

        return mapOfProductsInCategory;
    }

    public void setMapOfProductsInCategory(ConcurrentHashMap<String, ArrayList<Products>> mapOfProductsInCategory) {
        this.mapOfProductsInCategory = mapOfProductsInCategory;
    }

    public ArrayList<Categories> getListOfCategory() {

        return listOfCategory;
    }

    public void setListOfCategory(ArrayList<Categories> listOfCategory) {
        this.listOfCategory = listOfCategory;
    }

    public List<Set<String>> getItemSetList() {

        return listOfItemSetsForDataMining;
    }

    public void addToItemSetList(HashSet list) {
        listOfItemSetsForDataMining.add(list);
    }

}
