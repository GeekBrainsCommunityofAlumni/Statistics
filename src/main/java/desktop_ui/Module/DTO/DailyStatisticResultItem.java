package desktop_ui.Module.DTO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Cappoocha on 20.06.2017.
 */
public class DailyStatisticResultItem
{
    public StringProperty date;

    public IntegerProperty newPageCount;

    public DailyStatisticResultItem(Date date, Integer newPageCount)
    {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy");

        this.date = new SimpleStringProperty(sdfDate.format(date));
        this.newPageCount = new SimpleIntegerProperty(newPageCount);
    }
}
