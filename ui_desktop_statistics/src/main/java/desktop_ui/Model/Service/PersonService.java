/**
 * Created by Cappoocha on 22.06.2017.
 */

package desktop_ui.Model.Service;

import desktop_ui.Model.Dto.RestResponse.PersonDto;
import desktop_ui.Model.Entity.Choice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Сервис для работы с личностями
 */
public class PersonService
{
    private WebServiceProxy webServiceProxy = new WebServiceProxy();

    /**
     * Возвращает список с названиями доступных сайтов
     *
     * @return ObservableList
     */
    public ObservableList<Choice> getAvailablePersonList()
    {
        ObservableList<Choice> persons = FXCollections.observableArrayList();

        for (PersonDto person: this.webServiceProxy.getPersons()) {
            persons.add(new Choice(person.getId(), person.getName()));
        }

        return persons;
    }
}
