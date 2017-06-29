/**
 * Created by Cappoocha on 26.06.2017.
 */

package desktop_ui.Model.Dto.RestResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO ответа для статистики
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticResultDto
{
    private Integer rank;

    private PersonDto person;

    private PageDto page;

    public Integer getRank()
    {
        return rank;
    }

    public void setRank(Integer rank)
    {
        this.rank = rank;
    }

    public PersonDto getPerson()
    {
        return person;
    }

    public void setPerson(PersonDto person)
    {
        this.person = person;
    }

    public PageDto getPage()
    {
        return page;
    }

    public void setPage(PageDto page)
    {
        this.page = page;
    }
}
