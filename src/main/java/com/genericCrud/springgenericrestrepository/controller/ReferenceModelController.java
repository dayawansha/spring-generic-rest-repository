package com.genericCrud.springgenericrestrepository.controller;
import com.genericCrud.springgenericrestrepository.dto.CommonResponse;
import com.genericCrud.springgenericrestrepository.dto.EntityCategoryData;
import com.genericCrud.springgenericrestrepository.service.ReferenceModelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

/**
 * *
 *
 * @author : Dushman Nalin
 * @version : 1.0
 * @date : September 23, 2019
 */
@CrossOrigin
@RestController
@RequestMapping("/commonObjects")
public class ReferenceModelController {

    @Autowired
    ReferenceModelServiceImpl referenceModelService;

    @GetMapping("/{tableName}/{id}")
//    public <T> T getObject(@PathVariable("tableName") String tableName, @PathVariable("id") Integer id) throws ClassNotFoundException {
    public <T> ResponseEntity<T> getObject(@PathVariable("tableName") String tableName, @PathVariable("id") Integer id) throws ClassNotFoundException {
        T commonResponse = referenceModelService.getEntityCategory(tableName, id);

        if (commonResponse != null) {
            return new ResponseEntity<>(commonResponse, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{tableName}")
    public Object getAll(@PathVariable("tableName") String tableName) {
        Object commonResponse = referenceModelService.getEntityCategoryList(tableName);
        return commonResponse;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<CommonResponse> postEntityCategory(@RequestBody EntityCategoryData entityCategoryData) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        CommonResponse commonResponse = referenceModelService.addEntityCategory(entityCategoryData);
        return new ResponseEntity<>(commonResponse, HttpStatus.valueOf(commonResponse.getStatusCode()));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> updateEntityCategory(@RequestBody EntityCategoryData entityCategoryData) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        CommonResponse commonResponse = referenceModelService.updateEntityCategory(entityCategoryData);
        return new ResponseEntity<>(commonResponse, HttpStatus.valueOf(commonResponse.getStatusCode()));
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteEntityCategory(@RequestBody EntityCategoryData entityCategoryData) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        CommonResponse commonResponse = referenceModelService.deleteEntityCategory(entityCategoryData);
        return new ResponseEntity<>(commonResponse, HttpStatus.valueOf(commonResponse.getStatusCode()));
    }


//    @DeleteMapping("/{tableName}/{id}")
//    public <T> ResponseEntity<T> deleteEntityCategory(@PathVariable("tableName") String tableName, @PathVariable("id") Integer id) throws ClassNotFoundException {
//        T  commonResponse = referenceModelService.deleteEntityCategory(tableName,id);
//
//        if (commonResponse != null) {
//            return new ResponseEntity<>(commonResponse, HttpStatus.FOUND);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


}
