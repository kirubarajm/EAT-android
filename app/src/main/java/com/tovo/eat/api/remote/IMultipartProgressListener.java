package com.tovo.eat.api.remote;

public interface IMultipartProgressListener {
    void transferred(long transferred, int progress);
}