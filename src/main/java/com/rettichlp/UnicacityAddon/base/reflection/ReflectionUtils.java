package com.rettichlp.UnicacityAddon.base.reflection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
public class ReflectionUtils {

    public static List<Method> getMethodsAnnotatedWith(Class<?> annotationClass, String subPackage) {
        List<Method> methods = new ArrayList<>();
        getClassesInPackage(subPackage).forEach(clazz -> Arrays.stream(clazz.getMethods()).forEach(method -> {
            if (method.isAnnotationPresent((Class<? extends Annotation>) annotationClass)) methods.add(method);
        }));
        return methods;
    }

    public static List<Class<?>> getClassesAnnotatedWith(Class<?> annotation, String subPackage) {
        return getClassesInPackage(subPackage).stream().filter(clazz -> clazz.isAnnotationPresent((Class<? extends Annotation>) annotation)).collect(Collectors.toList());
    }

    /*
    public static Class getClassInPackage(String className, String packageName) {
        return getClassesInPackage(packageName).stream()
                .filter(clazz -> className.equals(clazz.getSimpleName())).findFirst().get();
    }
    */

    private static Set<Class<?>> getClassesInPackage(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
        if (stream == null) return new HashSet<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines().filter(line -> line.endsWith(".class")).map(line -> getClass(line, packageName)).collect(Collectors.toSet());
    }

    private static Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // TODO: handle the exception
        }
        return null;
    }

    public static <T, V> T getValue(V object, Class<T> type) {
        try {
            Field field = getField(object.getClass(), type);
            if (field == null) return null;

            return (T) field.get(object);
        } catch (IllegalAccessException e) {
            // TODO: handle the exception
        }

        return null;
    }

    public static void setValue(Class<?> clazz, Class<?> type, Object value) {
        try {
            Field field = getField(clazz, type);
            if (field == null) return;

            field.set(null, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Field getField(Class<?> clazz, Class<?> type) {
        for (Field declaredField : clazz.getDeclaredFields()) {
            if (declaredField.getType().equals(type)) {
                makeAccessible(declaredField);
                return declaredField;
            }
        }

        if (clazz.getSuperclass() != null) {
            return getField(clazz.getSuperclass(), type);
        }

        return null;
    }

    public static void makeAccessible(Field field) {
        field.setAccessible(true);

        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}