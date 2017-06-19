package desktop_ui.Controller;

import desktop_ui.MainApp;
import desktop_ui.Module.DTO.CommonStatisticResultItem;
import desktop_ui.Module.Proxy;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by Cappoocha on 20.06.2017.
 */
public class CommonStatisticController
{
    @FXML
    private ChoiceBox sites;

    @FXML
    private TableView statisticTable;

    @FXML
    private TableColumn<CommonStatisticResultItem, Integer> countColumn;

    @FXML
    private TableColumn<CommonStatisticResultItem, String> nameColumn;

    public CommonStatisticController()
    {
    }

    @FXML
    private void initialize()
    {
        sites.setItems(Proxy.getAvailableSiteList());
        sites.setValue(sites.getItems().get(0));

        countColumn.setCellValueFactory(cellData -> cellData.getValue().count.asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().name);
    }

    @FXML
    private void setScene()
    {
        MainApp.setScene(MainApp.dailyStatistic);
    }

    @FXML
    private void getCommonStatistic()
    {
        statisticTable.setItems(Proxy.getCommonStatistic("test"));
    }
}
