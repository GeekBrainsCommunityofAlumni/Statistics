package com.gb.statistics.features.ai.interfaces;

import com.gb.statistics.features.ai.controllers.ListController;
import com.gb.statistics.features.ai.model.ModelListData;
import javafx.collections.ObservableList;
import org.springframework.web.client.HttpClientErrorException;

public interface ListInterface {

    void refreshList() throws HttpClientErrorException;

    boolean add(ModelListData modelListData) throws HttpClientErrorException;

    boolean update(ModelListData modelListData) throws HttpClientErrorException;

    boolean delete(ModelListData modelListData) throws HttpClientErrorException;

    ObservableList getList();

    void setController(ListController controller);
}
