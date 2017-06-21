/**
 * Created by Cappoocha on 22.06.2017.
 */

package desktop_ui.Module.Service;

import desktop_ui.Model.Dto.RestResponse.PersonDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Сервис для работы с личностями
 */
public class PersonService
{
    private RestWebService restWebService = new RestWebService();

    /**
     * Возвращает список с названиями доступных сайтов
     *
     * @return ObservableList
     */
    public ObservableList<String> getAvailablePersonList()
    {
        ObservableList<String> persons = FXCollections.observableArrayList();

        for (PersonDto person: this.restWebService.getPersons()) {
            //TODO добавить индексы
            persons.add(person.getName());
        }

        return persons;
    }
}
