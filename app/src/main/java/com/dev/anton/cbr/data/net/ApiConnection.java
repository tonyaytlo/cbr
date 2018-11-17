package com.dev.anton.cbr.data.net;

import android.support.annotation.Nullable;

import com.dev.anton.cbr.data.entity.base.BaseError;
import com.dev.anton.cbr.data.entity.base.BaseResponse;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class ApiConnection<T> {

    private static final String GET = "GET";
    private final String DEFAULT_CHARSET = "windows-1251";
    private final URL url;

    private final Class<T> typeResponse;
    private BaseResponse<T> response;

    private ApiConnection(String url, Class<T> typeResponse) throws MalformedURLException {
        this.url = new URL(url);
        this.typeResponse = typeResponse;
    }

    @SuppressWarnings("unchecked")
    static <T> ApiConnection createGET(String url, Class<T> responseType) throws MalformedURLException {
        return new ApiConnection(url, responseType);
    }

    @Nullable
    BaseResponse<T> requestSyncCall() {
        connectToApi();
        return response;
    }

    private void connectToApi() {
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(GET);
            urlConnection.connect();
            in = new BufferedInputStream(urlConnection.getInputStream());
            String strResponse = readStream(in);

            Serializer serializer = new Persister();
            T result = serializer.read(typeResponse, strResponse);

            response = BaseResponse.success(result);
        } catch (Exception e) {
            response = BaseResponse.error(new BaseError(e));
        } finally {
            safeCloseStream(in);
            safeDisconnect(urlConnection);
        }
    }

    private void safeDisconnect(HttpURLConnection urlConnection) {
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
    }

    private void safeCloseStream(BufferedInputStream in) {
        try {
            in.close();
        } catch (Exception ignored) {
        }
    }

    private String readStream(BufferedInputStream in) throws IOException {
        byte[] contents = new byte[20000];
        int bytesRead;
        String strFileContents = "";
        while ((bytesRead = in.read(contents)) != -1) {
            strFileContents = new String(contents, 0, bytesRead, DEFAULT_CHARSET);
            System.out.print(strFileContents);
        }
        return strFileContents;
    }

}
