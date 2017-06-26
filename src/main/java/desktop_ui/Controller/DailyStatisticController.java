/**
 * Created by Cappoocha on 20.06.2017.
 */

package desktop_ui.Controller;

import desktop_ui.MainApp;
import desktop_ui.Model.Dto.RestResponse.StatisticResultDto;
import desktop_ui.Model.Entity.Choice;
import desktop_ui.Model.Service.PersonService;
import desktop_ui.Model.Service.SiteService;
import desktop_ui.Model.Service.StatisticService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private TableColumn<StatisticResultDto, Integer> newPageCountColumn;

    @FXML
    private TableColumn<StatisticResultDto, String> dateColumn;

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

    /**
     * Сервис для получения статистики
     */
    private StatisticService statisticService;

    public DailyStatisticController()
    {
    }

    @FXML
    private void initialize()
    {
        this.siteService = new SiteService();
        this.personService = new PersonService();
        this.statisticService = new StatisticService();

        sites.setItems(this.siteService.getAvailableSiteList());
        sites.getSelectionModel().select(0);

        persons.setItems(this.personService.getAvailablePersonList());
        persons.getSelectionModel().select(0);

        newPageCountColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRank()).asObject());
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPage().getFoundDateTime()));

        this.setDates();
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
            Object selectedSite = this.sites.getSelectionModel().getSelectedItem();
            Object selectedPerson = this.persons.getSelectionModel().getSelectedItem();

            if (selectedSite instanceof Choice && selectedPerson instanceof Choice) {
                statisticTable.setItems(this.statisticService.getDailyStatisticInPeriod(
                        (Choice) selectedSite,
                        (Choice) selectedPerson,
                        this.startDate.getValue(),
                        this.endDate.getValue()
                ));
            }
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

    /**
     * Установка дат
     */
    private void setDates()
    {
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
}
