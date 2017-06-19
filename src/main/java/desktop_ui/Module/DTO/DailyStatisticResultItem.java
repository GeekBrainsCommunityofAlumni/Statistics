package desktop_ui.Module.DTO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Created by Cappoocha on 20.06.2017.
 */
public class DailyStatisticResultItem
{
    public StringProperty date;

    public IntegerProperty newPageCount;

    public DailyStatisticResultItem(LocalDate date, Integer newPageCount)
    {
        this.date = new SimpleStringProperty(date.toString());
        this.newPageCount = new SimpleIntegerProperty(newPageCount);
    }
}
