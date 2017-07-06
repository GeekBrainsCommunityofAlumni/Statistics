/**
 * Created by Cappoocha on 20.06.2017.
 */

package desktop_ui.Controller;

import desktop_ui.MainApp;
import desktop_ui.Model.Dto.RestResponse.StatisticResultDto;
import desktop_ui.Model.Entity.Choice;
import desktop_ui.Model.Service.SiteService;
import desktop_ui.Model.Service.StatisticService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Контроллер для отображения страницы общей статистики
 */
public class CommonStatisticController
{
    @FXML
    private ComboBox<Choice> sites;

    @FXML
    private TableView<StatisticResultDto> statisticTable;

    @FXML
    private TableColumn<StatisticResultDto, Integer> rankColumn;

    @FXML
    private TableColumn<StatisticResultDto, String> nameColumn;

    /**
     * Сервис для работы с сайтами
     */
    private SiteService siteService;

    /**
     * Сервис для получения статистики
     */
    private StatisticService statisticService;

    public CommonStatisticController()
    {
    }

    @FXML
    private void initialize()
    {
        this.siteService = new SiteService();
        this.statisticService = new StatisticService();

        sites.setItems(this.siteService.getAvailableSiteList());
        sites.getSelectionModel().select(0);

        rankColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRank()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPerson().getName()));
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
            Object selectedSite = this.sites.getSelectionModel().getSelectedItem();

            if (selectedSite instanceof Choice) {
                statisticTable.setItems(this.statisticService.getCommonStatisticBySite((Choice) selectedSite));
            }
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
