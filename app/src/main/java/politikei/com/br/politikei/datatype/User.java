package politikei.com.br.politikei.datatype;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String token;
    private boolean is_fb = false; //By default is not facebook

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isFacebookAccount() {
        return is_fb;
    }

    public void setForFacebook() {
        this.is_fb = true;
    }
}
