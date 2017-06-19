package com.gb.statistics.features.ai.interfaces.impls;

import com.gb.statistics.features.ai.interfaces.KeyWordsInterface;
import com.gb.statistics.features.ai.model.KeyWord;
import com.gb.statistics.features.ai.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FakeKeyWordsList implements KeyWordsInterface {

    private ObservableList<KeyWord> keyWordsList = FXCollections.observableArrayList();

    @Override
    public ObservableList<KeyWord> getKeyWordsByPerson(Person person) {
        addTestData(person.getId());
        return keyWordsList;
    }

    @Override
    public ObservableList<KeyWord> getKeyWords() {
        return keyWordsList;
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

    private void addTestData(int id) {
        keyWordsList.clear();
        if (id == 1) {
            addKeyWord(new KeyWord(1, "Путину", 1));
            addKeyWord(new KeyWord(2, "Путином", 1));
        } else if (id == 2) {
            addKeyWord(new KeyWord(3, "Медведеву", 2));
            addKeyWord(new KeyWord(4, "Медведевым", 2));
        }
    }
}
