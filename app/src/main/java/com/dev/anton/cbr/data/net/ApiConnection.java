package com.dev.anton.cbr.data.net;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dev.anton.cbr.data.exception.SerializeException;
import com.dev.anton.cbr.data.model.core.BaseError;
import com.dev.anton.cbr.data.model.core.BaseResponse;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

class ApiConnection<T> {

    private static final String GET = "GET";

    private final String defaultCharset = "windows-1251";
    private final URL url;
    private final Class<T> typeResponse;
    private BaseResponse<T> response;

    private ApiConnection(@NonNull String url, @NonNull Class<T> typeResponse) throws MalformedURLException {
        Objects.requireNonNull(url);
        Objects.requireNonNull(typeResponse);
        this.url = new URL(url);
        this.typeResponse = typeResponse;
    }

    @SuppressWarnings("unchecked")
    static <T> ApiConnection createGET(@NonNull String url, @NonNull Class<T> responseType) throws MalformedURLException {
        return new ApiConnection(url, responseType);
    }

    @Nullable
    BaseResponse<T> requestSyncCall() {
        connectToApi();
        return response;
    }

    private void connectToApi() {
        try (AutoClosableConnection connectionWrapper =
                     new AutoClosableConnection((HttpURLConnection) url.openConnection())) {
            connectionWrapper.getConnection().setRequestMethod(GET);
            connectionWrapper.getConnection().connect();

            String strResponse;
            try (BufferedInputStream in =
                         new BufferedInputStream(connectionWrapper.getConnection().getInputStream())) {
                strResponse = readStream(in);
            }

            T typedResponse = serialize(strResponse);
            response = BaseResponse.success(typedResponse);
        } catch (IOException | SerializeException e) {
            response = BaseResponse.error(new BaseError(e));
        }
    }

    private T serialize(String response) throws SerializeException {
        Serializer serializer = new Persister();
        T result;
        try {
            result = serializer.read(typeResponse, response);
            if (result == null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException npe) {
            throw new SerializeException(npe);
        } catch (Exception e) {
            throw new SerializeException(e);
        }
        return result;
    }

    private String readStream(BufferedInputStream in) throws IOException {
        byte[] contents = new byte[20000];
        int bytesRead;
        String strStreamContents = "";
        while ((bytesRead = in.read(contents)) != -1) {
            strStreamContents = new String(contents, 0, bytesRead, defaultCharset);
        }
        return strStreamContents;
    }

}
