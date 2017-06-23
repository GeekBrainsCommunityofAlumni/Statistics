package com.gb.statistics.features.ai.interfaces;

import com.gb.statistics.features.ai.model.KeyWord;
import com.gb.statistics.features.ai.model.ModelListData;
import com.gb.statistics.features.ai.model.Person;
import javafx.collections.ObservableList;

public interface KeyWordsInterface {

    void refreshKeyWordList();

    ObservableList<ModelListData> getKeyWordList();

    boolean addKeyWord(KeyWord keyWord);

    boolean updateKeyWord(KeyWord keyWord);

    boolean deleteKeyWord(KeyWord keyWord);

    void setPerson(Person person);
}
