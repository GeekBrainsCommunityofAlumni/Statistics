/**
 * Created by Cappoocha on 26.06.2017.
 */

package desktop_ui.Module.Service;

import desktop_ui.Model.Dto.RestResponse.CommonStatisticResultDto;

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
     * @param siteId
     *
     * @return CommonStatisticResultDto[]
     */
    public CommonStatisticResultDto[] getCommonStatisticBySite(int siteId)
    {
        return this.webServiceProxy.getCommonStatistic(siteId);
    }
}
