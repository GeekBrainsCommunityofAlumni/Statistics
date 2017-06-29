/**
 * Created by Cappoocha on 26.06.2017.
 */

package desktop_ui.Model.Dto.RestResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PageDto
{
    private String foundDateTime;

    public String getFoundDateTime()
    {
        return foundDateTime;
    }

    public void setFoundDateTime(String foundDateTime)
    {
        this.foundDateTime = foundDateTime;
    }
}
