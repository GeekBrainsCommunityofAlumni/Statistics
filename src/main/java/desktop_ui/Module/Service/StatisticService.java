/**
 * Created by Cappoocha on 26.06.2017.
 */

package desktop_ui.Module.Service;

import desktop_ui.Model.Dto.RestResponse.StatisticResultDto;
import desktop_ui.Model.Entity.Choice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Сервис для работы со статистикой
 */
public class StatisticService
{
    private WebServiceProxy webServiceProxy = new WebServiceProxy();

    /**
     * Возвращает общую статистику для сайта
     * TODO: добавить обработку ошибок от прокси, возаразть пустой массив
     *
     * @param site
     *
     * @return StatisticResultDto[]
     */
    public ObservableList<StatisticResultDto> getCommonStatisticBySite(Choice site)
    {
        StatisticResultDto[] commonStatisticResult = this.webServiceProxy.getCommonStatistic(site.getId());
        return FXCollections.observableArrayList(commonStatisticResult);
    }
}
