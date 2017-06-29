/**
 * Created by Cappoocha on 22.06.2017.
 */

package desktop_ui.Model.Entity;

/**
 * Класс, хранящий названия методов REST API
 */
public class RestApiMethod
{
    public static final String SITES_PATH = "/site";
    public static final String SITE_ID_PATH = "/site/{id}";
    public static final String PERSONS_PATH = "/person";
    public static final String PERSON_ID_PATH = "/person/{id}";
    public static final String COMMON_SITE_STAT_PATH = "/stat/{siteId}";
    public static final String DAILY_SITE_STAT_PATH = "/stat/{siteId}/{personId}/{dateFrom}/{dateTo}";
}
