package com.git.hui.rabbit.reflect;

import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ParameterMetaData;

/**
 * Created by yihui in 18:54 18/5/29.
 */
public class ReflectUtil {

    public static Class getGenericClz(Object obj, Class basicClass, int index) {
        Class clz = obj.getClass();
        Class rawType;
        while (true) {
            Type type = clz.getGenericSuperclass();
            if (type instanceof Class) {
                if (Object.class.equals(type)) {
                    break;
                } else {
                    clz = (Class) type;
                    continue;
                }
            }

            if (type instanceof ParameterizedType) {
                rawType = ((ParameterizedTypeImpl) type).getRawType();
                if (!basicClass.equals(rawType)) {
                    clz = rawType;
                    continue;
                }

                Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                if (types.length > index) {
                    if (types[index] instanceof Class) {
                        return (Class) types[index];
                    } else {
                        return null;
                    }
                }
            }
        }

        throw new IllegalArgumentException("get obj's class info error! obj: " + obj.getClass() + " index: " + index);
    }


    public static boolean checkType(Type[] types, Class rawType, Class expectedType, int index) {
        for (Type type : types) {
            if ((type instanceof ParameterizedType) &&
                    rawType.isAssignableFrom((Class<?>) ((ParameterizedType) type).getRawType())) {
                if (expectedType.equals(((ParameterizedType) type).getActualTypeArguments()[index])) {
                    // 如果最后一个bolt的返回值类型不是boolean，则抛异常
                    return true;
                }
            }
        }

        return false;
    }

    // 目标是获取基础的接口上的泛型实际类型
    public String getDeclareType(Class source, Class target, int index) {
        if (!target.isAssignableFrom(source.getSuperclass())) { // 表示Source类是直接实现的目标接口
            Type[] types = source.getGenericInterfaces();
            for (Type type : types) {
                if (type instanceof ParameterizedType) {
                    ParameterizedType ptype = (ParameterizedType) type;
                    if (target.isAssignableFrom((Class<?>) ptype.getRawType())) {
                        if (target.equals(ptype.getRawType())) {
                            return ptype.getActualTypeArguments()[index].getTypeName();
                        } else { // 接口的继承关系, 嵌套的获取，这个就比较繁琐了
                            Type[] args = ptype.getActualTypeArguments();
                        }
                    }
                }
            }
        }
        return null;
    }


    @Test
    public void test() {
        getDeclareType(RealABolt.class, IBolt.class, 1);
        System.out.println("-----");
    }


    @Test
    public void testGetTypes() {
        Class basicClz = ReabBBolt.class;
//        Class basicClz = RealABolt.class;
        Type[] types;
        ParameterizedType ptype;
        types = basicClz.getGenericInterfaces();
        boolean loop = false;
        while (true) {
            if (types.length == 0) {
                break;
            }

            for (Type type : types) {
                if (type instanceof Class) {
                    if (IBolt.class.isAssignableFrom((Class<?>) type)) {
                        // 即表示有一个继承了IBolt的接口，完成了IBolt的泛型参数定义
                        // 如: public interface ARBolt extends IBolt<String, Boolean>
                        types = ((Class) type).getGenericInterfaces();
                        loop = true;
                        break;
                    } else { // 不是预期的类，直接pass掉
                        continue;
                    }
                }

                ptype = (ParameterizedType) type;
                if (ptype.getRawType() instanceof Class) {
                    if (!IBolt.class.isAssignableFrom((Class<?>) ptype.getRawType())) {
                        continue;
                    }

                    if (IBolt.class.equals(ptype.getRawType())) {
                        // 如果正好是我们需要获取的IBolt对象，则直接获取
                        Type[] parTypes = ptype.getActualTypeArguments();
                        for (Type par : parTypes) {
                            System.out.println(par.getTypeName());
                        }
                        return;
                    } else { // 需要根据父类来获取参数信息，重新进入循环
                        types = ((Class) ptype.getRawType()).getGenericInterfaces();
                        loop = true;
                        break;
                    }
                }
            }

            if (!loop) {
                break;
            }
        }
    }

    @Test
    public void testGetAbsTypes() {
        Class basicClz = RealAbsDefaultBolt.class;
        Type type;
        ParameterizedType ptype;
        while (true) {
            if (Object.class.equals(basicClz)) {
                break;
            }

            type = basicClz.getGenericSuperclass();
            if (!(type instanceof ParameterizedType)) {
                basicClz = basicClz.getSuperclass();
                continue;
            }

            ptype = (ParameterizedType) type;

            if (Object.class.equals(basicClz.getSuperclass().getSuperclass())) { // 如果ptype的父类为Object，则直接分析这个
                Type[] parTypes = ptype.getActualTypeArguments();
                for (Type par : parTypes) {
                    System.out.println(par.getTypeName());
                }
                break;
            } else {
                basicClz = basicClz.getSuperclass();
            }

        }
    }
}
