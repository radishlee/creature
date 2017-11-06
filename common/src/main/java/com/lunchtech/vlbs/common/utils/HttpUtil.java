package com.lunchtech.vlbs.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

public class HttpUtil {
	/**
	 * 生成签名
	 * @param params
	 * @param secret
	 * @return
	 */
	public static String digest(Map<String, String> params, String secret) {
        StringBuilder result = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());// map中的参数是区分大小写的
        Collections.sort(keys);
        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            if (!"sign".equalsIgnoreCase(key)) { // 必须这么写，set里调用remove方法，无法忽略大小写;request里的map无法remove
                result.append(key).append(params.get(key));
            }
        }
        result.append(secret);
        return HttpUtil.MD5(result.toString());
    }
	
	public static String MD5(String src) {
        try {
            if (src == null) {
                return "";
            }
            byte[] result = null;
            MessageDigest alg = MessageDigest.getInstance("MD5");
            result = alg.digest(src.getBytes("utf-8"));
            return byte2hex(result);
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }
	private static String byte2hex(byte[] b) {
        if (b == null) {
            return "";
        }
        StringBuffer hs = new StringBuffer();
        String stmp = null;
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append("0");
            }
            hs.append(stmp);
        }
        return hs.toString();
    }
	/**
	 * 从返回中读取数据
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public static String getResponseBody(HttpResponse response) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
	/**
	 * 将 map 中的参数及对应值转换为查询字符串
	 * @param params
	 * @return
	 */
    public static String mapToQueryString(Map<String, String> params) {
        Object[] array = params.keySet().toArray();
        java.util.Arrays.sort(array);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            String key = array[i].toString();
            try {
            	sb.append(key).append("=").append(URLEncoder.encode((String) params.get(key), "UTF-8"));
                if (i != array.length - 1) {
                	sb.append("&");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
