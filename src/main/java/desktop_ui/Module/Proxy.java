package desktop_ui.Module;
import desktop_ui.Module.DTO.CommonStatisticResultItem;
import desktop_ui.Module.DTO.DailyStatisticResultItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
     * @param siteName
     *
     * @return ObservableList
     */
    public static ObservableList<CommonStatisticResultItem> getCommonStatistic(String siteName)
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
     * @param siteName
     * @param personName
     * @param datePeriod
     *
     * @return ObservableList
     */
    public static ObservableList<DailyStatisticResultItem> getDailyStatistic(String siteName, String personName, Period datePeriod)
    {
        return FXCollections.observableArrayList(
                new DailyStatisticResultItem(new Date(), 12),
                new DailyStatisticResultItem(new Date(), 10),
                new DailyStatisticResultItem(new Date(), 14)
        );
    }
}
