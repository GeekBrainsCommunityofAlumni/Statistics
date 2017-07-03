package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.ModalControllerInterface;
import com.gb.statistics.features.ai.model.ModelListData;
import com.gb.statistics.features.ai.window.ModalWindow;
import com.gb.statistics.features.ai.interfaces.ListInterface;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import java.io.IOException;
import java.lang.reflect.Method;

public abstract class ListController {

    protected final int CLICK_COUNT = 2;
    protected final int MODAL_WIDTH = 300;
    protected final int MODAL_HEIGHT = 100;
    protected final String ADD_TITLE = "Создание записи";
    protected final String EDIT_TITLE = "Редактирование записи";
    protected final String DELETE_TITLE = "Удаление записи";
    protected final String PERSON_TITLE = "Личности";
    protected final String KEYWORD_TITLE = "Ключевые слова";
    protected final String SITE_TITLE = "Сайты";
    protected final String EMPTY_LIST_MESSAGE = "Список пуст";
    protected final String ERROR_404 = "404 Not Found";
    protected final String COUNT_LABEL = "Всего записей:  ";
    protected final String EDIT_FXML_URL = "/fxml/editModalWindow.fxml";
    protected final String DELETE_FXML_URL = "/fxml/deleteModalWindow.fxml";
    protected final String URL = "http://94.130.27.143:8080/api";

    protected FXMLLoader loaderAdd = new FXMLLoader();
    protected FXMLLoader loaderEdit = new FXMLLoader();
    protected FXMLLoader loaderDelete = new FXMLLoader();
    protected Parent parentAdd;
    protected Parent parentEdit;
    protected Parent parentDelete;
    protected EditWindowController addController;
    protected EditWindowController editController;
    protected DeleteWindowController deleteController;
    protected ConnectionController connectionController;
    protected ModalWindow addWindow;
    protected ModalWindow editWindow;
    protected ModalWindow deleteWindow;
    protected Stage mainStage;

    @FXML
    protected Label listTitle;

    @FXML
    protected Label errorMessage;

    @FXML
    protected Label listCount;

    @FXML
    protected CustomTextField search;

    @FXML
    protected TableView<ModelListData> dataTableView;

    @FXML
    private TableColumn<ModelListData, String> columnName;

    @FXML
    protected Button editButton;

    @FXML
    protected Button deleteButton;

    @FXML
    protected Button refreshButton;

    @FXML
    protected void initialize(String title) throws Exception {
        listTitle.setText(title);
        columnName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        dataTableView.setPlaceholder(new Label(EMPTY_LIST_MESSAGE));
        initModalWindow(DELETE_TITLE, loaderDelete, DELETE_FXML_URL, new DeleteWindowController());
        initModalWindow(EDIT_TITLE, loaderEdit, EDIT_FXML_URL, new EditWindowController());
        initModalWindow(ADD_TITLE, loaderAdd, EDIT_FXML_URL, new EditWindowController());
        setupClearButtonField(search);
    }

    protected void initModalWindow(String windowType, FXMLLoader loader, String fxmlUrl, ModalControllerInterface controller) {
        try {
            loader.setLocation(getClass().getResource(fxmlUrl));
            loader.setController(controller);
            switch (windowType) {
                case ADD_TITLE:
                    parentAdd = loader.load();
                    addController = loader.getController();
                    break;
                case EDIT_TITLE:
                    parentEdit = loader.load();
                    editController = loader.getController();
                    break;
                case DELETE_TITLE:
                    parentDelete = loader.load();
                    deleteController = loader.getController();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected abstract void actionButtonAdd();

    @FXML
    protected abstract void actionButtonEdit();

    @FXML
    protected abstract void actionButtonDelete();

    @FXML
    protected abstract void actionButtonRefresh();

    protected void disableButtons(boolean value) {
        editButton.setDisable(value);
        deleteButton.setDisable(value);
    }

    protected void setFocus() {
        dataTableView.getSelectionModel().select(0);
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    protected void updateListCount(ListInterface list) {
        listCount.setText(COUNT_LABEL + String.valueOf(list.getList().size()));
    }

    protected void setActivityButtons(ListInterface list) {
        if (list.getList().isEmpty()) disableButtons(true);
        else disableButtons(false);
    }

    public void setErrorMessage(String message) {
        visibleErrorMessage(true);
        if (message.equals("")) {
            errorMessage.setText(message);
        } else if (message.length() > 30) {
            errorMessage.setText(ERROR_404);
        } else errorMessage.setText("Ошибка: " + message.substring(message.indexOf(':') + 2, message.indexOf('}') - 1));
    }

    public void visibleErrorMessage(boolean value) {
        errorMessage.setVisible(value);
    }

    public void setConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    public ConnectionController getConnectionController() {
        return connectionController;
    }

    public String getSearchText() {
        return search.getText();
    }

    public void setSearchText(String text) {
        search.setText(text);
    }

    protected void initFilter(ListInterface list) {
        search.textProperty().addListener(o -> {
            if(search.textProperty().get().isEmpty()) {
                dataTableView.setItems(list.getList());
                setFocus();
                return;
            }
            ObservableList<ModelListData> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<ModelListData, ?>> cols = dataTableView.getColumns();
            for(int i=0; i<list.getList().size(); i++) {
                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(list.getList().get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(search.textProperty().get().toLowerCase())) {
                        tableItems.add((ModelListData) list.getList().get(i));
                        break;
                    }
                }
            }
            dataTableView.setItems(tableItems);
            setFocus();
        });
    }

    private void setupClearButtonField(CustomTextField customTextField)  throws Exception {
        Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
        m.setAccessible(true);
        m.invoke(null, customTextField, customTextField.rightProperty());
    }
}