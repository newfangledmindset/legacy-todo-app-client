package com.lsb;

public class Config {
    static final String URL = "https://tall-savory-frost.glitch.me/";
    public static final String URL_LOGIN = URL + "auth/login";
    public static final String URL_REGISTER = URL + "auth/register";
    public static final String URL_GET_MEMO = URL + "api/get";
    public static final String URL_SET_MEMO = URL + "api/set";
    public static final String URL_DELETE_MEMO = URL + "api/delete";

    public static final String URL_LOCALDB = "jdbc:sqlite:offline.db";
}
