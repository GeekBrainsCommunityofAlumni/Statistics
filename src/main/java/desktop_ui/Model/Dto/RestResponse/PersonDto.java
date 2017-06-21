/**
 * Created by Cappoocha on 20.06.2017.
 */
package desktop_ui.Model.Dto.RestResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO личности
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDto
{
    private int id;

    private String name;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
