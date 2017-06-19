package desktop_ui.Controller;
import desktop_ui.MainApp;
import desktop_ui.Module.DTO.DailyStatisticResultItem;
import desktop_ui.Module.Proxy;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.time.Period;

/**
 * Created by Cappoocha on 20.06.2017.
 */
public class DailyStatisticController
{
    @FXML
    private ChoiceBox sites;

    @FXML
    private ChoiceBox persons;

    @FXML
    private TableView statisticTable;

    @FXML
    private TableColumn<DailyStatisticResultItem, Integer> newPageCountColumn;

    @FXML
    private TableColumn<DailyStatisticResultItem, String> dateColumn;

    public DailyStatisticController()
    {
    }

    @FXML
    private void initialize()
    {
        sites.setItems(Proxy.getAvailableSiteList());
        sites.setValue(sites.getItems().get(0));
        persons.setItems(Proxy.getAvailablePersonNameList());
        persons.setValue(persons.getItems().get(0));

        newPageCountColumn.setCellValueFactory(cellData -> cellData.getValue().newPageCount.asObject());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().date);
    }

    @FXML
    private void setScene()
    {
        MainApp.setScene(MainApp.commonStatistic);
    }

    @FXML
    private void getDailyStatistic()
    {
        statisticTable.setItems(Proxy.getDailyStatistic(
                "test",
                "Навальный",
                Period.between(LocalDate.now(), LocalDate.now())
        ));
    }
}
