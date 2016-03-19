package com.perfectljy.ersanshi.Base;

import android.os.Bundle;

import com.perfectljy.ersanshi.Event.NotifyInfo;
import com.perfectljy.ersanshi.Event.imple.EventObserver;
import com.perfectljy.ersanshi.Event.imple.EventSubject;

import java.lang.ref.WeakReference;


/**
 * 带观察者模式的fragment
 *
 * @author zhiwu_yan
 * @version 1.0
 * @since 2015-08-31  17:50
 */
public abstract class BaseObserverFragment extends BaseFragment {
    private FragmentEventObserver mFragmentEventObserver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentEventObserver = new FragmentEventObserver(this);
        registerObserver(mFragmentEventObserver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeObserver(mFragmentEventObserver);
    }

    public void registerObserver(EventObserver observer) {
        final String[] observerEventTypes = getObserverEventType();//获取所有需要监听的业务类型
        if (observerEventTypes != null && observerEventTypes.length > 0) {
            final EventSubject eventSubject = EventSubject.getInstance();
            for (String eventType : observerEventTypes) {
                eventSubject.registerObserver(eventType, observer);
            }


        }

    }

    public void removeObserver(EventObserver observer) {
        final String[] observerEventTypes = getObserverEventType();//获取所有需要监听的业务类型
        if (observerEventTypes != null && observerEventTypes.length > 0) {
            final EventSubject eventSubject = EventSubject.getInstance();
            for (String eventType : observerEventTypes) {
                eventSubject.removeObserver(eventType, observer);
            }

        }
    }

    /**
     * 该方法会在具体的观察者对象中调用，可以根据事件的类型来更新对应的UI，这个方法在UI线程中被调用，
     * 所以在该方法中不能进行耗时操作，可以另外开线程
     *
     * @param notifyInfo 事件传递信息
     */
    protected abstract void onChange(NotifyInfo notifyInfo);

    /**
     * 通过这个方法来告诉具体的观察者需要监听的业务类型
     *
     * @return
     */
    protected abstract String[] getObserverEventType();

    private static class FragmentEventObserver extends EventObserver {
        //添加弱引用，防止对象不能被回收
        private final WeakReference<BaseObserverFragment> mFragment;

        public FragmentEventObserver(BaseObserverFragment activity) {
            super();
            mFragment = new WeakReference<BaseObserverFragment>(activity);
        }

        @Override
        public void onChange(NotifyInfo notifyInfo) {
            BaseObserverFragment activity = mFragment.get();
            if (activity != null) {
                activity.onChange(notifyInfo);
            }
        }
    }
}
