package com.github.mdsimmo.pixxit.utils;

/**
 * Used to provide a delayed callback for when something happens
 */
public interface Callback<T> {

    void notify(T obj);

}
