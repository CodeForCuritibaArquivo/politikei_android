package politikei.com.br.politikei;

/**
 * Created by Jean on 25/11/2015.
 */
public class BusinessLogic
{

    private static BusinessLogic businessLogic;
    private String accessToken = null;

    private BusinessLogic()
    {

    }

    public void setAccessToken(String token)
    {
        this.accessToken = token;
    }

    public String getAccessToken()
    {
        return this.accessToken;
    }

    public static BusinessLogic getInstance()
    {
        if(businessLogic == null)
        {
            businessLogic = new BusinessLogic();
        }
        return businessLogic;
    }
}
