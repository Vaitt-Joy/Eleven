package com.eleven.http2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * @author vic Zhou
 * @time 2017-12-19 1:36
 * @des 响应类
 */

public class Response {
    private int code = -1;

    private HttpURLConnection connection;

    private String errorMsg = "";

    public Response(HttpURLConnection httpURLConnection) throws IOException {
        connection = httpURLConnection;
        code = connection.getResponseCode();
    }

    @Override
    public String toString() {
        return " response code : " + code + "  \n errorMsg : " + errorMsg;
    }

    public boolean isError() {
        return code >= 400;
    }

    public int getResponseCode() {
        return code;
    }

    public String getResponseBodyAsString() {
        if (code >= 400) {
            throw new RuntimeException("response error , code = " + code);
        }
        InputStream inputStream = null;
        ByteArrayOutputStream bos = null;
        try {
            inputStream = connection.getInputStream();
            bos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
            String body = new String(bos.toByteArray());
            return body;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect();
        }
        return "";
    }

    public String getResponseErrorMsg(){
        if (code < 400) {
            throw new RuntimeException("not error , code = " + code);
        }
        InputStream inputStream = null;
        ByteArrayOutputStream bos = null;
        try {
            inputStream = connection.getErrorStream();
            bos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
            String body = new String(bos.toByteArray());
            return body;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect();
        }
        return "";
    }
}
