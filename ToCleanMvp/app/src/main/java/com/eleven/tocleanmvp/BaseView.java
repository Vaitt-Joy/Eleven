package com.eleven.tocleanmvp;

/**
 * @author vic
 * @time 2017-12-15 23:09
 * @des MVP 中的 V
 */

public interface BaseView<T  extends BasePersenter> {
    void setPresenter(T presenter);
}
