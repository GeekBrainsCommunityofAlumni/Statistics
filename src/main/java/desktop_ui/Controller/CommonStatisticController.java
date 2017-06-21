/**
 * Created by Cappoocha on 20.06.2017.
 */

package desktop_ui.Controller;

import desktop_ui.MainApp;
import desktop_ui.Module.DTO.CommonStatisticResultItem;
import desktop_ui.Module.Proxy;
import desktop_ui.Module.Service.SiteService;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Контроллер для отображения страницы общей статистики
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

    /**
     * Сервис для работы с сайтами
     */
    private SiteService siteService;

    public CommonStatisticController()
    {
    }

    @FXML
    private void initialize()
    {
        this.siteService = new SiteService();

        sites.setItems(this.siteService.getAvailableSiteList());
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
        if (siteIsValid()) {
            statisticTable.setItems(Proxy.getCommonStatistic(this.sites.getSelectionModel().getSelectedIndex()));
        }
    }

    /**
     * Валидация сайта
     *
     * @return boolean
     */
    private boolean siteIsValid()
    {
        return this.sites.getSelectionModel() != null;
    }
}
