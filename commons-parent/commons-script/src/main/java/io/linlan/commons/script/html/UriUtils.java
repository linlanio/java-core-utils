package io.linlan.commons.script.html;

import io.linlan.commons.core.ClassUtils;
import io.linlan.commons.core.StringUtils;
import io.linlan.commons.core.lang.Assert;
import io.linlan.commons.script.ScriptException;
import io.linlan.commons.script.abs.UrlConstants;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 *  
 * URI class to provide utils for use
 * Filename:UriUtils.java
 * Desc:URI utils include getUrl, url, deal with URI etc.
 *
 * CreateTime:2020-07-13 11:18 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class UriUtils extends UrlConstants {

    /**
     * constructor of self
     */
    private UriUtils() {

    }

    /**
     * create a URL from a URL whole path
     *
     * @param source URL
     * @return {@link URL}
     */
    public static URL url(String source){
        try {
            return new URL(source);
        } catch (MalformedURLException e) {
            throw new ScriptException(e.getMessage(), e);
        }
    }

    /**
     * get URL of source path, the source is relative of classpath
     *
     * @param source the source is relative of classpath
     * @return URL
     */
    public static URL getURL(String source) {
        return ClassUtils.getDefaultClassLoader().getResource(source);
    }


    /**
     * get URL of source path, the source is relative of classpath
     *
     * @param source the source is relative of classpath
     * @param clazz the input class
     * @return URL
     */
    public static URL getURL(String source, Class<?> clazz) {
        return clazz.getResource(source);
    }

    /**
     * get URL of source file
     *
     * @param file the source file
     * @return URL
     */
    public static URL getURL(File file) {
        Assert.notNull(file, "File is null !");
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new ScriptException("Error occured when get URL!", e);
        }
    }

    /**
     * get URL of source files
     *
     * @param files the source files
     * @return URL
     */
    public static URL[] getURL(File... files) {
        final URL[] urls = new URL[files.length];
        try {
            for(int i = 0; i < files.length; i++){
                urls[i] = files[i].toURI().toURL();
            }
        } catch (MalformedURLException e) {
            throw new ScriptException("Error occured when get URL!", e);
        }

        return urls;
    }

    /**
     * handleUrl to add http:// or add https://
     * if url is null, return null
     * if url startsWith http:// or https://, direct return url
     *
     * @param source the url source string
     * @return String the string
     */
    public static String formatUrl(String source) {
        return formatUrl(source, HTTP_URL);
    }

    /**
     * formatUrl to add http:// or add https://
     * if url is null, return null
     * if url startsWith http:// or https://, direct return url
     *
     * @param source the url source string
     * @param defStr the default head string
     * @return String the string
     */
    public static String formatUrl(String source, String defStr) {
        if (source == null) {
            return null;
        }
        source = StringUtils.trim(source);
        if (StringUtils.isBlank(source) || StringUtils.startsWith(source, HTTP_URL)
                || StringUtils.startsWith(source, HTTPS_URL)) {
            return source;
        } else if (StringUtils.startsWith(source, RTMP_URL)) {
            return source;
        }else{
            return defStr + source.trim();
        }
    }

    /**
     * complete the url if it is relative
     *
     * @param source the source absolute URL
     * @param relative the relative URL
     * @return absolute String
     * @exception ScriptException MalformedURLException
     */
    public static String complateUrl(String source, String relative) {
        source = formatUrl(source);
        if (StringUtils.isBlank(source)) {
            return null;
        }

        try {
            final URL absoluteUrl = new URL(source);
            final URL parseUrl = new URL(absoluteUrl, relative);
            return parseUrl.toString();
        } catch (MalformedURLException e) {
            throw new ScriptException(e);
        }
    }

    /**
     * encode the URL<br>
     * use the hex to encode the source url with input charset name
     * @see URLEncoder#encode
     *
     * @param url URL
     * @param charsetName charset name
     * @return the encode URL
     * @exception ScriptException UnsupportedEncodingException
     */
    public static String encode(String url, String charsetName) {
        try {
            return URLEncoder.encode(url, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new ScriptException(e);
        }
    }

    /**
     * decode the URL<br>
     * decode the hex string to URL
     * @see URLDecoder#decode
     *
     * @param url URL
     * @param charsetName charset name
     * @return the decode URL
     * @exception ScriptException UnsupportedEncodingException
     */
    public static String decode(String url, String charsetName) {
        try {
            return URLDecoder.decode(url, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new ScriptException(e);
        }
    }

    /**
     * get the path of URL string<br>
     *
     * @param source the source path of URI
     * @return path
     * @exception ScriptException wrap URISyntaxException
     */
    public static String getPath(String source){
        URI uri = null;
        try {
            uri = new URI(source);
        } catch (URISyntaxException e) {
            throw new ScriptException(e);
        }
        return uri.getPath();
    }


    /**
     * trans the source string to URI
     * @param source the source path of string
     * @return URI
     * @exception ScriptException wrap URISyntaxException
     */
    public static URI toURI(String source) {
        try {
            return new URI(source.replace(StringUtils.SPACE, "%20"));
        } catch (URISyntaxException e) {
            throw new ScriptException(e);
        }
    }
}
