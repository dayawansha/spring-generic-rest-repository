package com.genericCrud.springgenericrestrepository.service;


import com.genericCrud.springgenericrestrepository.dto.CommonResponse;
import com.genericCrud.springgenericrestrepository.dto.EntityCategoryData;
import com.genericCrud.springgenericrestrepository.repository.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * *
 *
 * @author : Dushman Nalin
 * @version : 1.0
 * @date : September 23, 2019
 */
@Service
public class ReferenceModelServiceImpl {


    @Autowired
    CommonRepository commonRepository;

    @Transactional
    public <T> T getEntityCategory(String tableName, Integer primaryKey) throws ClassNotFoundException {
        Class<?> cls = Class.forName("com.iil.ibu.life.admin.repository.model." + tableName);
        T entityObject = (T) commonRepository.select(cls, primaryKey);
        return entityObject;
    }

    @Transactional
    public Object getEntityCategoryList(String tableName) {

        try {
            Class<?> cls = Class.forName("com.iil.ibu.life.admin.repository.model.model." + tableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "testing";
    }

    @Transactional
    public CommonResponse addEntityCategory(EntityCategoryData entityCategoryData) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        ArrayList entityArrayList = getEntity(entityCategoryData);
        if (entityArrayList.size() > 0) {
            commonRepository.saveList(entityArrayList);
            return new CommonResponse("Common Object Added Successfully", true, HttpStatus.OK.value(), "", new Date(), "");
        } else {
            return new CommonResponse("Common object not Created", true, HttpStatus.OK.value(), "", new Date(), "");
        }
    }

    @Transactional
    public CommonResponse updateEntityCategory(EntityCategoryData entityCategoryData) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        ArrayList entityArrayList = getEntity(entityCategoryData);
        if (entityArrayList.size() > 0) {
            commonRepository.update(entityArrayList);
            return new CommonResponse("Common Object Updated Successfully", true, HttpStatus.OK.value(), "", new Date(), "");
        } else {
            return new CommonResponse("Common object not Updated", true, HttpStatus.OK.value(), "", new Date(), "");
        }
    }

    @Transactional
    public CommonResponse deleteEntityCategory(EntityCategoryData entityCategoryData) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        ArrayList entityArrayList = getEntity(entityCategoryData);
        commonRepository.delete(entityArrayList);
        return new CommonResponse("Common Object Deleted Successfully", true, HttpStatus.OK.value(), "", new Date(), "");
    }


    private ArrayList getEntity(EntityCategoryData entityCategoryData) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {

        Class<?> cls = Class.forName("com.iil.ibu.life.admin.repository.model." + entityCategoryData.getClassName());

        ArrayList<HashMap<String, Object>> hashMapArrayList = entityCategoryData.getEntityCategoryList();
        ArrayList clsArrayList = new ArrayList();
        Method[] methods = cls.getDeclaredMethods();

        Field[] primaryClassFields = cls.getDeclaredFields();

        for (HashMap<String, Object> hashMap : hashMapArrayList) {
            Object clsInstance = cls.newInstance();
            for (Map.Entry<String, Object> objectHashMap : hashMap.entrySet()) {
                String entryFields = objectHashMap.getKey();
                Object entryFieldsValue = objectHashMap.getValue();

                for (Field f : primaryClassFields) {
                    if (f.isAnnotationPresent(GeneratedValue.class)) {
                        f.setAccessible(true);
                        f.set(clsInstance, hashMap.get("Id"));
                        break;
                    }
                }
                for (Method m : methods) {
                    if (m.getName().toLowerCase().endsWith("set" + entryFields.toLowerCase())) {
                        Object don = manyToOneObject(cls, entryFieldsValue, entryFields);
                        m.invoke(clsInstance, don);
                    }
                }
            }
            clsArrayList.add(clsInstance);
        }
        return clsArrayList;
    }

    ////////this method returns the forign key objects with thir id
    private Object manyToOneObject(Class<?> rootObject, Object entryFieldsValue, String entryFieldsName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Object secondoryObject = new Object();
        Field[] fields = rootObject.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(ManyToOne.class) && (f.getName().toLowerCase().equals(entryFieldsName.toLowerCase()))) {
                rootObject = Class.forName(f.getType().getName());
                secondoryObject = rootObject.newInstance();
                Field[] fields2 = rootObject.getDeclaredFields();
                for (Field f2 : fields2) {
                    if (f2.isAnnotationPresent(GeneratedValue.class)) {
                        f.setAccessible(true);
                        f2.setAccessible(true);
                        f2.set(secondoryObject, entryFieldsValue);
                    }
                }
                return secondoryObject;
            } else {
                secondoryObject = entryFieldsValue;
            }
        }
        return secondoryObject;
    }


    //if one day wont to add values with out considering foreign key values, this method should use instated of getEntity
    // because "getEntity" method have heavy time complexity.
    private ArrayList getEntityBasic(EntityCategoryData entityCategoryData) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {

        Class<?> cls = Class.forName("com.iil.ibu.life.admin.repository.model." + entityCategoryData.getClassName());

        ArrayList<HashMap<String, Object>> hashMapArrayList = entityCategoryData.getEntityCategoryList();
        ArrayList clsArrayList = new ArrayList();
        Method[] methods = cls.getDeclaredMethods();
        for (HashMap<String, Object> hashMap : hashMapArrayList) {
            Object clsInstance = cls.newInstance();
            for (Map.Entry<String, Object> objectHashMap : hashMap.entrySet()) {
                String entryFields = objectHashMap.getKey();
                Object entryFieldsValue = objectHashMap.getValue();

                for (Method m : methods) {
                    if (m.getName().toLowerCase().endsWith("set" + entryFields.toLowerCase())) {
                        m.invoke(clsInstance, entryFieldsValue);
                    }
                }
            }
            clsArrayList.add(clsInstance);
        }
        return clsArrayList;
    }

}
