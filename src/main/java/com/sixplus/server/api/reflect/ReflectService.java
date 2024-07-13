package com.sixplus.server.api.reflect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.sixplus.server.api.reflect.model.Child;

@Service
public class ReflectService {
    private static final String packName = "com.sixplus.server.api.reflect.models.Child";
    private static final Logger log = LoggerFactory.getLogger(ReflectService.class);

    public void trigger(String[] args) throws NoSuchMethodException {
        // print args with index
        for (int i = 0; i < args.length; i++) {
            System.out.println("args[" + i + "]: " + args[i]);
        }
        try {
            ReflectService.findMethod();
            ReflectService.handleFields();
            ReflectService.handleStatic();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            log.error("Error occurred while invoking method: ", e);
        } catch (NoSuchFieldException e) {
            log.error("Error occurred while handling field: ", e);
        }

//        SpringApplication.run(DemoAopRelectionApplication.class, args);
    }

    public static void handleStatic() {
        try {
            Class<?> clazz = Class.forName("com.sixplus.server.api.reflect.model.StaticExample");
            Field field = clazz.getField("EXAMPLE");
            System.out.println("Static field: " + field.get(null));

            Method method = clazz.getMethod("getSquare", int.class);
            System.out.println("Static method: " + method.invoke(null, 5));
        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static void handleFields() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        Class<?> clazz = ReflectService.findClass();
        Child child = new Child();
        // handle specific field
        Field singleField = clazz.getDeclaredField("cstr1");
        singleField.setAccessible(true);
        log.debug("current field: {}", singleField.getName());
        log.debug("original value: {}", singleField.get(child));
        singleField.set(child, "single modified value");
        log.debug("modified value: {}", singleField.get(child));

        // handle all fields
        Field[] fields2 = clazz.getFields();
        for (Field field : fields2) {
            System.out.println("Get public fields in both Parent and Child: " + field);
            field.setAccessible(true);
            log.debug("current field: {}", field.getName());
            log.debug("original value: {}", field.get(child));
            field.set(child, "modified value");
            log.debug("modified value: {}", field.get(child));
        }


    }

    public static void findMethod() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<?> clazz = ReflectService.findClass();
        // get specific method
        Method m4 = clazz.getDeclaredMethod("method4", int.class);
        m4.setAccessible(true); // allow to access private method
        Child childInstance = (Child) clazz.getDeclaredConstructor().newInstance();
        int result = (int) m4.invoke(childInstance, 5);
        System.out.println("Result of method4: " + result);

        Class<?>[] partTypes = new Class[2];
        partTypes[0] = int.class;
        partTypes[1] = int.class;
        Method mma = clazz.getDeclaredMethod("methodMultiArgs", partTypes);

        // not includes inherited methods
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("Get methods in Child: " + method);
        }
        // includes inherited methods
        Method[] methods2 = clazz.getMethods();
        for (Method method : methods2) {
            System.out.println("Get methods in Child: " + method);
        }
    }

    public static Class<?> findClass() throws ClassNotFoundException, NoSuchMethodException {
        // 변수를 이용한 클래스 로딩
        Class<?> clazz = Child.class;
        System.out.println(clazz.getName()); // Class name: Child

        // Class.forName()을 이용한 클래스 로딩
        Class<?> clazz2 = null;

        clazz2 = Class.forName(packName);
        System.out.println("Class name: " + clazz2.getName());

        Constructor<?> constructor2 = clazz2.getDeclaredConstructor(String.class);
        System.out.println("Constructor(String): " + constructor2.getName()); // Constructor(String): Child

        Constructor<?>[] constructors = clazz2.getDeclaredConstructors();
        for (Constructor<?> cons : constructors) {
            System.out.println("Get constructors in Child: " + cons);
        }
        return clazz2;
    }
}
