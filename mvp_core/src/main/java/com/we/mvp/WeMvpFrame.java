package com.we.mvp;

import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created to :
 *
 * @author cc.wang
 * @date 2020/5/6
 */
public class WeMvpFrame {

    private static final String METHOD_BEGIN = "register";
    private static final String INSTANCE_KEY = "instance";

    private static final Map<String, Object> M_METHODS = new HashMap<>();

    public static void findClass() {
        try {
            Class<?> aClass = Class.forName("cn.we.mvp.MvpRegister");
            Object instance = aClass.newInstance();
            M_METHODS.put(INSTANCE_KEY, instance);
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                String name = method.getName();
                if (name.startsWith(METHOD_BEGIN) || "provider".equals(name)) {
                    Log.e("cc.wang", "WeMvpFrame.findClass." + name);
                    M_METHODS.put(name, method);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean register(Fragment fragment) {
        return handler(fragment.getClass(), fragment);
    }

    public static boolean register(Activity activity) {
        return handler(activity.getClass(), activity);
    }

    private static boolean handler(Class<?> clz, Object target) {
        if (M_METHODS.size() <= 1) {
            return false;
        }
        try {
            String activityName = METHOD_BEGIN + clz.getSimpleName();
            Object obj = M_METHODS.get(activityName);
            Object instance = M_METHODS.get(INSTANCE_KEY);
            if (null != instance && obj instanceof Method) {
                Method method = (Method) obj;
                method.invoke(instance, target);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
