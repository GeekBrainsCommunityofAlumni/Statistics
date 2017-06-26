/**
 * Created by Cappoocha on 20.06.2017.
 */

package desktop_ui.Controller;

import desktop_ui.MainApp;
import desktop_ui.Module.DTO.DailyStatisticResultItem;
import desktop_ui.Module.Proxy;
import desktop_ui.Module.Service.PersonService;
import desktop_ui.Module.Service.SiteService;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Контроллер для отображения ежедневной статистики
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

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    /**
     * Сервис для работы с сайтами
     */
    private SiteService siteService;

    /**
     * Сервис для работы с личностями
     */
    private PersonService personService;

    public DailyStatisticController()
    {
    }

    @FXML
    private void initialize()
    {
        this.siteService = new SiteService();
        this.personService = new PersonService();

        sites.setItems(this.siteService.getAvailableSiteList());
        sites.getSelectionModel().select(0);

        persons.setItems(this.personService.getAvailablePersonList());
        persons.getSelectionModel().select(0);

        newPageCountColumn.setCellValueFactory(cellData -> cellData.getValue().newPageCount.asObject());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().date);

        String pattern = "dd.MM.yyyy";
        startDate.setValue(LocalDate.now());

        startDate.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        endDate.setValue(LocalDate.now());
    }

    @FXML
    private void setScene()
    {
        MainApp.setScene(MainApp.commonStatistic);
    }

    @FXML
    private void getDailyStatistic()
    {
        // TODO добавить отображение ошибок
        if (datePeriodIsValid() && siteIsValid() && personIsValid()) {
            statisticTable.setItems(Proxy.getDailyStatistic(
                    this.sites.getSelectionModel().getSelectedIndex(),
                    this.persons.getSelectionModel().getSelectedIndex(),
                    this.startDate.getValue(),
                    this.endDate.getValue()
            ));
        }
    }

    /**
     * Валидация дат
     *
     * @return boolean
     */
    private boolean datePeriodIsValid()
    {
        LocalDate startDate = this.startDate.getValue();
        LocalDate endDate = this.endDate.getValue();

        return (startDate != null && endDate != null && (endDate.isAfter(startDate) || endDate.isEqual(startDate)));
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

    /**
     * Валидация личности
     *
     * @return boolean
     */
    private boolean personIsValid()
    {
        return this.persons.getSelectionModel() != null;
    }
}
