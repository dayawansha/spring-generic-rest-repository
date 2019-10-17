package com.genericCrud.springgenericrestrepository.dto;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityCategoryData{

    String className;
    ArrayList<HashMap<String,Object>> entityCategoryList;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<HashMap<String, Object>> getEntityCategoryList() {
        return entityCategoryList;
    }

    public void setEntityCategoryList(ArrayList<HashMap<String, Object>> entityCategoryList) {
        this.entityCategoryList = entityCategoryList;
    }
}
