package com.dev.anton.cbr.data.net;

import java.net.HttpURLConnection;

class AutoClosableConnection implements AutoCloseable {

    private final HttpURLConnection httpURLConnection;

    AutoClosableConnection(HttpURLConnection httpURLConnection) {
        this.httpURLConnection = httpURLConnection;
    }

    HttpURLConnection getConnection() {
        return httpURLConnection;
    }

    @Override
    public void close() {
        httpURLConnection.disconnect();
    }
}
