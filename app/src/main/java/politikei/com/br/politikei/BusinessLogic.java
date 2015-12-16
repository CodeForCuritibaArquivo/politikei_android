package politikei.com.br.politikei;

public class BusinessLogic {
    private static BusinessLogic mInstance;
    private String mAccessToken = null;

    private BusinessLogic() {
    }

    public void setAccessToken(String token)
    {
        this.mAccessToken = token;
    }

    public String getAccessToken()
    {
        return this.mAccessToken;
    }

    public static BusinessLogic getInstance() {
        if(mInstance == null) {
            mInstance = new BusinessLogic();
        }
        return mInstance;
    }
}
