package com.example.demo.util;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;

public class ClassUtil {

    public static <T> Collection<Class<? extends T>> getSubClasses(Class<T> parent) {
        URL url = parent.getResource("/");
        String fileName = url.getFile();
        String pathName = fileName.replaceFirst("/", "");
        File rootFile = new File(pathName);
        Collection<Class<? extends T>> subClasses = new LinkedList<>();
        setSubList(rootFile, rootFile.getPath() + "\\", parent, subClasses);
        return subClasses;
    }

    private static <T> void setSubList(File currentFile, String rootDirectory,
                                       Class<T> parent, Collection<Class<? extends T>> subClasses) {
        if (currentFile.isDirectory()) {
            File[] files = currentFile.listFiles();
            for (File file : files != null ? files : new File[0]) {
                setSubList(file, rootDirectory, parent, subClasses);
            }
        } else {
            try {
                if (currentFile.getPath().endsWith(".class")) {
                    String className = currentFile.getPath().replace(rootDirectory, "")
                            .replace(".class", "").replace("\\", ".");
                    Class<?> classObject = Class.forName(className);
                    Class<? extends T> subClass = classObject.asSubclass(parent);
                    // 要么是子类，要么是类本身
                    if (!className.equals(parent.getCanonicalName())) {
                        subClasses.add(subClass);
                    }
                }
            } catch (ClassNotFoundException | ClassCastException ignored) {
            }
        }
    }
}
