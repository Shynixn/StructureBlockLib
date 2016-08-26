package com.shynixn.structureblocklib.lib;

import org.bukkit.plugin.java.JavaPlugin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.annotation.ElementType.*;

/**
 * Created by Shynixn
 */
public final class PluginHandler
{
    private PluginHandler() {}

    private static void invokeNewMethod(Method method)
    {
        try
        {
            method.setAccessible(true);
            method.invoke(null);
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static void load(JavaPlugin plugin, Class<?>... classes)
    {
        if(plugin == null)
            throw new IllegalArgumentException("Plugin cannot be null!");
        if(classes == null)
            throw new IllegalArgumentException("Clazz cannot be null!");
        for(Class<?> clazz : classes)
        {
            while (clazz != null)
            {
                for(Field field : clazz.getDeclaredFields())
                {
                    if(field.isAnnotationPresent(PluginLoad.class))
                    {
                        try
                        {
                            field.setAccessible(true);
                            field.set(null,plugin);
                        } catch (IllegalAccessException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                for(Method method : clazz.getDeclaredMethods())
                {
                    if(method.isAnnotationPresent(PluginLoad.class))
                    {
                        invokeNewMethod(method);
                    }
                }
                clazz = clazz.getSuperclass();
            }
        }
    }

    public static void unload(Class<?>... classes)
    {
        if(classes == null)
            throw new IllegalArgumentException("Clazz cannot be null!");
        for(Class<?> clazz : classes)
        {
            while (clazz != null)
            {
                for(Method method : clazz.getDeclaredMethods())
                {
                    if(method.isAnnotationPresent(PluginUnload.class))
                    {
                        invokeNewMethod(method);
                    }
                }
                clazz = clazz.getSuperclass();
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(value={CONSTRUCTOR, FIELD, METHOD})
    public @interface PluginLoad{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(value={METHOD})
    public @interface PluginUnload{}

    public static abstract class AsyncRunnable implements Runnable
    {
        @PluginLoad
        private static JavaPlugin plugin;
        private boolean isSynchrone = false;
        private Object[] paramcache;

        public <T> T getParam(int number)
        {
            if(paramcache.length > number && number >= 0)
                return (T) paramcache[number];
            return null;
        }

        public boolean isSynchrone()
        {
            return isSynchrone;
        }

        public static void toAsynchroneThread(final AsyncRunnable runnable, final Object... params)
        {
            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable()
            {
                @Override
                public void run()
                {
                    runnable.paramcache = params;
                    runnable.isSynchrone = false;
                    runnable.run();
                }
            });
        }

        public static void toSynchroneThread(final AsyncRunnable runnable, final Object... params)
        {
            plugin.getServer().getScheduler().runTask(plugin, new Runnable()
            {
                @Override
                public void run()
                {
                    runnable.paramcache = params;
                    runnable.isSynchrone = true;
                    runnable.run();
                }
            });
        }
    }
}
