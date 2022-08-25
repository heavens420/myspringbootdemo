package com.zlx.datajpa.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.springframework.beans.BeanUtils.getPropertyDescriptor;
import static org.springframework.beans.BeanUtils.getPropertyDescriptors;

@Service
public class CopyBeanService {

    public void copyProperties(Object source, Object target) throws BeansException {
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);  //得到属性数组
        for (PropertyDescriptor targetPd : targetPds) {//通过循环对属性一一赋值
            if (targetPd.getWriteMethod() != null) {//针对某属性，目标对象是否具有写方法（setter）
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {//源对象是否具有读方法（getter）
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);  //强制修改getter的修饰符为public
                        }
                        Object value = readMethod.invoke(source);//获取属性值
                        // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
                        if (value != null) {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);  //强制修改setter方法的修饰符为public
                            }
                            writeMethod.invoke(target, value);//赋值
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy properties from source to target", ex);
                    }
                }
            }
        }
    }
}
