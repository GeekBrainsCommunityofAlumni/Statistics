package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.entity.Keyword;

import java.util.LinkedList;
import java.util.List;

public class MockKeywordRepository implements KeywordRepositiory {

    private static int count;
    private static List<Keyword> keywordList = new LinkedList<Keyword>();


    @Override
    public List<Keyword> getAll() {
        return keywordList;
    }

    @Override
    public List<Keyword> get(int personId) {
        LinkedList<Keyword> lst = new LinkedList<Keyword>();
        for (Keyword k : keywordList){
            if (k.getPersonId() == personId){
                lst.add(k);
            }
        }
        return lst;
    }

    @Override
    public Keyword add(Keyword keyword) {
        keyword.setId(++count);
        keywordList.add(keyword);
        return keyword;
    }

    @Override
    public Keyword update(Keyword keyword) {
        for (Keyword k : keywordList){
            if(keyword.getId()==k.getId()){
                k.setName(keyword.getName());
                return k;
            }
        }
        return null;
    }

    @Override
    public boolean delete(Keyword keyword) {
        if(keywordList.contains(keyword)){
            keywordList.remove(keyword);
            return true;
        }
        return false;
    }

    @Override
    public boolean isExists(Keyword keyword) {
        return keywordList.contains(keyword);
    }
}
