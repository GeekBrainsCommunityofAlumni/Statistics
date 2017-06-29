/**
 * Created by Cappoocha on 20.06.2017.
 */

package desktop_ui.Model.Service;

import desktop_ui.Model.Dto.RestResponse.SiteDto;
import desktop_ui.Model.Entity.Choice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Сервис для работы с сайтами
 */
public class SiteService
{
    private WebServiceProxy webServiceProxy = new WebServiceProxy();

    /**
     * Возвращает список с названиями доступных сайтов
     *
     * @return ObservableList
     */
    public ObservableList<Choice> getAvailableSiteList()
    {
        ObservableList<Choice> sites = FXCollections.observableArrayList();

        for (SiteDto site: this.webServiceProxy.getSites()) {
            sites.add(new Choice(site.getId(), site.getName()));
        }

        return sites;
    }
}
