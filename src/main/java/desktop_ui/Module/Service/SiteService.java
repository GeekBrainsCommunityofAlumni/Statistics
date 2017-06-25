/**
 * Created by Cappoocha on 20.06.2017.
 */

package desktop_ui.Module.Service;

import desktop_ui.Model.Dto.RestResponse.SiteDto;
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
    public ObservableList<String> getAvailableSiteList()
    {
        ObservableList<String> sites = FXCollections.observableArrayList();

        for (SiteDto site: this.webServiceProxy.getSites()) {
            //TODO добавить индексы
            sites.add(site.getName());
        }

        return sites;
    }
}
