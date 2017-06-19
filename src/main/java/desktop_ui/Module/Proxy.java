package desktop_ui.Module;
import desktop_ui.Module.DTO.CommonStatisticResultItem;
import desktop_ui.Module.DTO.DailyStatisticResultItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

/**
 * Created by Cappoocha on 20.06.2017.
 */
public class Proxy
{
    /**
     * Возвращает список с названиями доступных сайтов
     *
     * @return ObservableList
     */
    public static ObservableList<String> getAvailableSiteList()
    {
        return FXCollections.observableArrayList("Lenta.ru", "Vk.com");
    }

    /**
     * Вовзращает список с именами доступных личностей
     *
     * @return ObservableList
     */
    public static ObservableList<String> getAvailablePersonNameList()
    {
        return FXCollections.observableArrayList("Путин", "Медведев", "Навальный");
    }

    /**
     * Возвращает общую статистику по сайту
     *
     * @param siteId
     *
     * @return ObservableList
     */
    public static ObservableList<CommonStatisticResultItem> getCommonStatistic(Integer siteId)
    {
        return FXCollections.observableArrayList(
                new CommonStatisticResultItem(1, "Путин"),
                new CommonStatisticResultItem(10, "Медведев"),
                new CommonStatisticResultItem(8, "Навальный")
        );
    }

    /**
     * Возвращает ежедневную статистику по сайту
     *
     * @param siteId
     * @param personId
     * @param startDate
     * @param endDate
     *
     * @return
     */
    public static ObservableList<DailyStatisticResultItem> getDailyStatistic(
            Integer siteId,
            Integer personId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return FXCollections.observableArrayList(
                new DailyStatisticResultItem(startDate, 12),
                new DailyStatisticResultItem(startDate, 10),
                new DailyStatisticResultItem(endDate, 14)
        );
    }
}
