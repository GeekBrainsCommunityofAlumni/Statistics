package desktop_ui.Module;

import desktop_ui.Module.DTO.DailyStatisticResultItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

/**
 * Created by Cappoocha on 20.06.2017.
 */
public class Proxy
{
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
