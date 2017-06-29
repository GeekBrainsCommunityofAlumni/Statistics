/**
 * Created by Cappoocha on 26.06.2017.
 */

package desktop_ui.Model.Entity;

/**
 * Класс помощник, для того, чтобы хранить id, в качестве индексов при отображении выпадающего списка
 */
public class Choice
{
    /**
     * Идентификатор сущности, как индекс в списке
     */
    private Integer id;

    /**
     * Наименование для записи в выпадающем списке
     */
    private String name;

    /**
     * Constructor
     *
     * @param id
     * @param name
     */
    public Choice(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     *
     * @return Integer
     */
    public Integer getId()
    {
        return this.id;
    }

    @Override
    public String toString()
    {
        return this.name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Choice choice = (Choice) o;

        return this.name != null && this.name.equals(choice.name) || this.id != null && this.id.equals(choice.id);
    }

    @Override
    public int hashCode()
    {
        int result = this.id != null ? this.id.hashCode() : 0;
        result = 31 * result + (this.name != null ? this.name.hashCode() : 0);

        return result;
    }
}
