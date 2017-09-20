package com.lesports.gene.vct.api.controller;

import com.lesports.gene.vct.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/preFile", method = RequestMethod.GET)
    public Object getPrevicousFile(String name, Integer type) {
        return fileService.getPreviousFile(name, type);
    }


    @RequestMapping(value = "/latFile", method = RequestMethod.GET)
    public Object getLatestFile(String name, Integer type) {
        return fileService.getLatestFile(name, type);
    }
}
