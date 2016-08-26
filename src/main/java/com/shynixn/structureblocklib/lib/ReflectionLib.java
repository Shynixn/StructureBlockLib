package com.shynixn.structureblocklib.lib;

import org.bukkit.Bukkit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Shynixn
 */
public final class ReflectionLib
{
    public static <T> T createDefaultInstance(Class<T> clazz)
    {
        try
        {
            if(clazz == null)
                throw new RuntimeException("Clazz cannot be null!");
            return clazz.newInstance();
        }
        catch (InstantiationException | IllegalAccessException ignored)
        {

        }
        return null;
    }

    public static Object invokeConstructor(Class<?> clazz,Object... params)
    {
        do
        {
            for(Constructor constructor : clazz.getDeclaredConstructors())
            {
                try
                {
                    constructor.setAccessible(true);
                    return constructor.newInstance(params);
                }
                catch (Exception ex)
                {

                }
            }
            clazz = clazz.getSuperclass();
        }while (clazz != null);
        throw new RuntimeException("Cannot find correct constructor.");
    }

    public static Object invokeMethodByObject(String methodName, Object instance, Object... params)
    {
        try
        {
            return getMethodFromName(methodName, instance.getClass()).invoke(instance, params);
        }
        catch (Exception ex)
        {

        }
        return null;
    }

    public static Object invokeMethodByClass(String methodName, Class<?> clazz, Object... params)
    {
        try
        {
            return getMethodFromName(methodName, clazz).invoke(null, params);
        }
        catch (Exception ex)
        {

        }
        return null;
    }

    public static void setValueOfFieldByObject(String fieldName,Object instance, Object value)
    {
        try
        {
            getFieldFromName(fieldName, instance.getClass()).set(instance, value);
        }
        catch (Exception ex)
        {

        }
    }

    public static void setValueOfFieldByClazz(String fieldName, Class<?> clazz, Object value)
    {
        try
        {
            getFieldFromName(fieldName, clazz).set(null, value);
        }
        catch (Exception ex)
        {

        }
    }

    public static Object getValueFromFieldByClass(String fieldName, Class<?> clazz)
    {
        try
        {
           return getFieldFromName(fieldName, clazz).get(null);
        }
        catch (Exception ex)
        {

        }
        return null;
    }

    public static Object getValueFromFieldByObject(String fieldName, Object instance)
    {
        try
        {
            return getFieldFromName(fieldName, instance.getClass()).get(instance);
        }
        catch (Exception ex)
        {

        }
        return null;
    }

    public static Method getMethodFromName(String name, Class<?> clazz)
    {
        for(Method method : clazz.getDeclaredMethods())
        {
            try
            {
               if(method.getName().equals(name))
               {
                  return method;
               }
            }
            catch (Exception ex)
            {

            }
       }
       return null;
    }

    public static Field getFieldFromName(String name, Class<?> clazz)
    {
        for(Field field : clazz.getDeclaredFields())
        {
            try
            {
                if(field.getName().equals(name))
                {
                    return field;
                }
            }
            catch (Exception ex)
            {

            }
        }
        return null;
    }

    public static Class<?> createClass(String classPath)
    {
        try
        {
            classPath = classPath.replace("VERSION", getServerVersion());
            return Class.forName(classPath);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        throw new RuntimeException("Cannot find correct class.");
    }

    public static <T extends Annotation> T getAnnotation(Class<T> annotationClass, Class<?> clazz)
    {
        while (clazz != null)
        {
            for(Annotation annotation : clazz.getDeclaredAnnotations())
            {
                if(annotation.annotationType().getName().equalsIgnoreCase(annotationClass.getName()))
                {
                    return (T) annotation;
                }
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    public static String getServerVersion()
    {
        try
        {
            return Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
        }
        catch (Exception ex)
        {
            throw new RuntimeException("Version not found!");
        }
    }
}

