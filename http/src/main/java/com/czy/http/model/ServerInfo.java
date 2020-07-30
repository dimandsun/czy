package com.czy.http.model;

import com.czy.util.model.Par;

/**
 * @author chenzy
 * @date 2020-07-28
 */
public record ServerInfo(int port, String address, int timeout, Par<String> charSet) {
    public void charSet(String charSet) {
        this.charSet.set(charSet);
    }
}
