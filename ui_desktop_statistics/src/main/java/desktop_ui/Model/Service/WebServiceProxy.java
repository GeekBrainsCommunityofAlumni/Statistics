/**
 * Created by Cappoocha on 20.06.2017.
 */

package desktop_ui.Model.Service;

import desktop_ui.Model.Dto.RestResponse.StatisticResultDto;
import desktop_ui.Model.Dto.RestResponse.PersonDto;
import desktop_ui.Model.Dto.RestResponse.SiteDto;
import desktop_ui.Model.Entity.RestApiMethod;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Прокси сервис для взаимодействия с REST
 *
 * TODO сделать обработку ошибок и ответов от сервиса с кодами 4хх и 5хх
 */
public class WebServiceProxy
{
    // TODO вынести в конфиг
    final String BASE_URI = "http://94.130.27.143:8080/api";

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Возвращает список доступных сайтов
     *
     * @return SiteDto[]
     *
     * @throws RuntimeException
     */
    public SiteDto[] getSites()
    {
        String uri = BASE_URI + RestApiMethod.SITES_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<SiteDto[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, SiteDto[].class);
        return response.getBody();
    }

    /**
     * Возвращает сайт по id
     *
     * @param id
     *
     * @return SiteDto
     *
     * @throws RuntimeException
     */
    public SiteDto getSiteById(int id)
    {
        String uri = BASE_URI + RestApiMethod.SITE_ID_PATH;

        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<SiteDto> response = restTemplate.exchange(uri, HttpMethod.GET, entity, SiteDto.class, params);
        return response.getBody();
    }

    /**
     * Возвращает список всех личностей
     *
     * @return PersonDto[]
     */
    public PersonDto[] getPersons()
    {
        String uri = BASE_URI + RestApiMethod.PERSONS_PATH;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<PersonDto[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, PersonDto[].class);
        return response.getBody();
    }

    /**
     * Возвращает личность, по переданному идентификатору
     *
     * @param id
     *
     * @return PersonDto
     */
    public PersonDto getPersonById(int id)
    {
        String uri = BASE_URI + RestApiMethod.PERSON_ID_PATH;

        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<PersonDto> response = restTemplate.exchange(uri, HttpMethod.GET, entity, PersonDto.class, params);
        return response.getBody();
    }

    /**
     * Возвращает общую статистику по сайту
     *
     * @param siteId
     *
     * @return StatisticResultDto[]
     */
    public StatisticResultDto[] getCommonStatistic(int siteId)
    {
        String uri = BASE_URI + RestApiMethod.COMMON_SITE_STAT_PATH;

        Map<String, Integer> params = new HashMap<>();
        params.put("siteId", siteId);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<StatisticResultDto[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, StatisticResultDto[].class, params);
        return response.getBody();
    }

    /**
     * Возвращает статистику по указанным датам
     *
     * @param siteId    Идентификатор сайта
     * @param personId  Идентификатор личности
     * @param dateFrom  Дата, с которой требуется получить статистику
     * @param dateTo    Дата, по которую требуется получить статистику
     *
     * @return StatisticResultDto[]
     */
    public StatisticResultDto[] getDailyStatistic(Integer siteId, Integer personId, String dateFrom, String dateTo)
    {
        String uri = BASE_URI + RestApiMethod.DAILY_SITE_STAT_PATH;

        Map<String, String> params = new HashMap<>();
        params.put("siteId", siteId.toString());
        params.put("personId", personId.toString());
        params.put("dateFrom", dateFrom);
        params.put("dateTo", dateTo);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<StatisticResultDto[]> response = restTemplate.exchange(
            uri,
            HttpMethod.GET,
            entity,
            StatisticResultDto[].class,
            params
        );

        return response.getBody();
    }
}
