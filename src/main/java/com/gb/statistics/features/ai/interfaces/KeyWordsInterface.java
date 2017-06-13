package com.gb.statistics.features.ai.interfaces;

import com.gb.statistics.features.ai.model.KeyWord;
import com.gb.statistics.features.ai.model.Person;
import java.util.List;

public interface KeyWordsInterface {

    List<KeyWord> getKeyWordsByPerson(Person person);

    boolean addKeyWord(Person person);

    boolean updatePerson(Person person);

    boolean deletePerson(Person person);
}
