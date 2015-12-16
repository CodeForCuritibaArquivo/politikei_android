package politikei.com.br.politikei.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Object response;
    private final Map<String, String> headers;
    private final Listener<T> listener;

    public GsonRequest(String url, Class<T> classType, Map<String, String> headers,
                       Response.Listener<T> listener, ErrorListener errorListener, Object response) {
        super(Method.POST, url, errorListener);
        this.clazz = classType;
        this.headers = headers;
        this.listener = listener;
        this.response = response;
    }

    public GsonRequest(String url, Class<T> classType,
                       Listener<T> listener, ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        headers = null;
        this.clazz = classType;
        this.listener = listener;
        this.response = null;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        return response != null ? "application/json" : super.getBodyContentType();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if(response != null) {
            try {
                Gson gson = new Gson();
                return gson.toJson(response,response.getClass()).getBytes(getParamsEncoding());
            } catch (UnsupportedEncodingException e) {
                Log.d("GsonRequest", e.toString());
            }
        }
        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            T parsedObject = gson.fromJson(json, clazz);
            return Response.success(parsedObject, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}