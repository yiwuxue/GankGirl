package com.dalingge.gankio.common.factory;

import android.support.annotation.Nullable;

import com.dalingge.gankio.common.base.BasePresenter;

/**
 * 这个类代表一个 {@link PresenterFactory} 创建一个Presenter {@link Class#newInstance()} 方法.
 *
 * @param <P> the type of the presenter.
 */
public class ReflectionPresenterFactory<P extends BasePresenter> implements PresenterFactory<P> {

    private Class<P> presenterClass;

    /**
     * This method returns a {@link ReflectionPresenterFactory} instance if a given view class has
     * a {@link RequiresPresenter} annotation, or null otherwise.
     *
     * @param viewClass a class of the view
     * @param <P>       a type of the presenter
     * @return a {@link ReflectionPresenterFactory} instance that is supposed to create a presenter from {@link RequiresPresenter} annotation.
     */
    @Nullable
    public static <P extends BasePresenter> ReflectionPresenterFactory<P> fromViewClass(Class<?> viewClass) {
        RequiresPresenter annotation = viewClass.getAnnotation(RequiresPresenter.class);
        //noinspection unchecked
        Class<P> presenterClass = annotation == null ? null : (Class<P>)annotation.value();
        return presenterClass == null ? null : new ReflectionPresenterFactory<>(presenterClass);
    }

    public ReflectionPresenterFactory(Class<P> presenterClass) {
        this.presenterClass = presenterClass;
    }

    @Override
    public P createPresenter() {
        try {
            return presenterClass.newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
