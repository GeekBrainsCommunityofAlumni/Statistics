package com.gb.statistics.features.ai.interfaces.impls;

import com.gb.statistics.features.ai.interfaces.KeyWordsInterface;
import com.gb.statistics.features.ai.model.KeyWord;
import com.gb.statistics.features.ai.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FakeKeyWordsList implements KeyWordsInterface {

    private ObservableList<KeyWord> keyWordsList = FXCollections.observableArrayList();

    @Override
    public void refreshKeyWordList() {

    }

    @Override
    public ObservableList<KeyWord> getKeyWordList() {
        return null;
    }

    @Override
    public boolean addKeyWord(KeyWord keyWord) {
        keyWordsList.add(keyWord);
        return false;
    }

    @Override
    public boolean updateKeyWord(KeyWord keyWord) {
        return false;
    }

    @Override
    public boolean deleteKeyWord(KeyWord keyWord) {
        keyWordsList.remove(keyWord);
        return false;
    }

    @Override
    public void setPerson(Person person) {

    }

    private void addTestData(int id) {
        keyWordsList.clear();
        if (id == 1) {
            addKeyWord(new KeyWord(1, 1, "Путину"));
            addKeyWord(new KeyWord(2, 1, "Путином"));
        } else if (id == 2) {
            addKeyWord(new KeyWord(3, 2, "Медведеву"));
            addKeyWord(new KeyWord(4, 2, "Медведевым"));
        }
    }
}
