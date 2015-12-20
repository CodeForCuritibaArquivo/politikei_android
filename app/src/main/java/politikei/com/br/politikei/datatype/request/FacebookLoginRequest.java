package politikei.com.br.politikei.datatype.request;

public class FacebookLoginRequest {
    private String uuid;
    private String accessToken;

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}