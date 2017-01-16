package com.yourcloud.yourcloud.Presenter;

/**
 * Created by ritchie-huang on 17-1-15.
 */

public interface Presenter<V> {
    void attachView(V view);

    void detachView();
}
