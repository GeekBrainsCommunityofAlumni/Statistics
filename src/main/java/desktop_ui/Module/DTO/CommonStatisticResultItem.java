package desktop_ui.Module.DTO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Cappoocha on 20.06.2017.
 */
public class CommonStatisticResultItem
{
    public IntegerProperty count;

    public StringProperty name;

    public CommonStatisticResultItem(Integer count, String name)
    {
        this.count = new SimpleIntegerProperty(count);
        this.name = new SimpleStringProperty(name);
    }
}
