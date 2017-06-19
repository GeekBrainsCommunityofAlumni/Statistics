package com.gb.statistics.features.ai.interfaces;

import com.gb.statistics.features.ai.model.KeyWord;
import com.gb.statistics.features.ai.model.Person;
import javafx.collections.ObservableList;

public interface KeyWordsInterface {

    ObservableList<KeyWord> getKeyWordsByPerson(Person person);

    ObservableList<KeyWord> getKeyWords();

    boolean addKeyWord(KeyWord keyWord);

    boolean updateKeyWord(KeyWord keyWord);

    boolean deleteKeyWord(KeyWord keyWord);


}
