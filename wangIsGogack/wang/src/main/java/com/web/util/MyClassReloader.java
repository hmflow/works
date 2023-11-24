package com.web.util;

public class MyClassReloader {

    public static void main(String[] args) throws Exception {
        // 클래스를 로드할 ClassLoader 인스턴스 생성
        ClassLoader classLoader = MyClassReloader.class.getClassLoader();

        // 클래스 이름
        String className = "com.web.controller.ViewController"; // 변경된 클래스 이름으로 변경해야 합니다.

        // 클래스 로드
        Class<?> clazz = classLoader.loadClass(className);

        // 변경된 클래스를 다시 로드하기 위해 ClassLoader의 clearCache() 메서드 호출
        clearClassLoaderCache(classLoader);

        // 변경된 클래스를 다시 로드
        Class<?> reloadedClazz = classLoader.loadClass(className);

        // 로드된 클래스들이 같은지 비교
        System.out.println("Is the reloaded class the same as the original one? " + (clazz == reloadedClazz));
    }

    // ClassLoader의 clearCache() 메서드 호출하는 함수
    private static void clearClassLoaderCache(ClassLoader classLoader) throws Exception {
        java.lang.reflect.Method method = ClassLoader.class.getDeclaredMethod("clearCache");
        method.setAccessible(true);
        method.invoke(classLoader);
    }
}