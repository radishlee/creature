package com.lesports.gene.vct.service;

import com.alibaba.fastjson.JSONObject;
import com.lesports.gene.vct.common.enumuration.FileTypeEnum;
import com.lesports.gene.vct.common.exception.ServiceException;
import com.lesports.gene.vct.common.model.File;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class FileServiceITTest extends BaseITTest {
    @Autowired
    private FileService fileService;

    private File file;

    @Before
    public void init() {
        file = new File();
    }


    @Test
    public void testAddFile() {
        try {
            fileService.addFile(file);
        } catch (ServiceException e) {
            Assert.isTrue("文件名称不能为空".equals(e.getMessage()));
        }


        String name = "test";
        file.setName(name);
        try {
            fileService.addFile(file);
        } catch (ServiceException e) {
            Assert.isTrue("文件保存路径不能为空".equals(e.getMessage()));
        }


        String url = "www.baidu.com";
        file.setUrl(url);
        try {
            fileService.addFile(file);
        } catch (ServiceException e) {
            Assert.isTrue("文件描述信息不能为空".equals(e.getMessage()));
        }


        String info = "balabala帅哥";
        file.setInfo(info);
        try {
            fileService.addFile(file);
        } catch (ServiceException e) {
            Assert.isTrue("文件类型信息不能为空".equals(e.getMessage()));
        }


        file.setType(-3);
        try {
            fileService.addFile(file);
        } catch (ServiceException e) {
            Assert.isTrue("文件类型信息不合法".equals(e.getMessage()));
        }

        file.setType(5);
        try {
            fileService.addFile(file);
        } catch (ServiceException e) {
            Assert.isTrue("文件类型信息不合法".equals(e.getMessage()));
        }


        int type = FileTypeEnum.IOS.getStatusValue();
        file.setType(type);
        try {
            fileService.addFile(file);
        } catch (ServiceException e) {
            Assert.isTrue("编辑人员不能为空".equals(e.getMessage()));
        }


        //第一次保存
        //版本号和pid都包含-1
        String auth = "radish";
        file.setAuth(auth);
        fileService.addFile(file);

        File file = fileService.getLatestFile(name, type);
        Assert.isTrue(file.getAuth().equals(auth));
        Assert.isTrue(file.getInfo().equals(info));
        Assert.isTrue(file.getUrl().equals(url));
        Assert.isTrue(file.isLatest() == true);


        //第二次保存
        File newfile = new File();
        String newAuth = "zhoujie";
        String newInfo = "infomation";
        String newUrl = "www.zhoujie.com";


        newfile.setAuth(newAuth);
        newfile.setInfo(newInfo);
        newfile.setUrl(newUrl);
        newfile.setName(name);
        newfile.setType(type);
        fileService.addFile(newfile);

        File newFile = fileService.getLatestFile(name, type);
        Assert.isTrue(newFile.getAuth().equals(newAuth));
        Assert.isTrue(newFile.getInfo().equals(newInfo));
        Assert.isTrue(newFile.getUrl().equals(newUrl));
        Assert.isTrue(newFile.getPid().equals(file.getId()));
        //Assert.isTrue(newFile.getCurVer().contains(file.getId()));
        Assert.isTrue(newFile.isLatest() == true);

        File oldFile = fileService.get(file.getId());
        Assert.isTrue(oldFile.isLatest() == false);
    }


    @Test
    public void testGetPreviousFile() {
        File files = new File();
        String name = "test1SS";
        files.setName(name);
        String url = "www.baidu.com";
        files.setUrl(url);
        String info = "balabala帅哥";
        files.setInfo(info);
        int type = FileTypeEnum.ANDROID.getStatusValue();
        files.setType(type);
        String auth = "radish";
        files.setAuth(auth);

        //第一次保存
        //版本号和pid都包含-1
        fileService.addFile(files);

        File oldFile = fileService.getLatestFile(name, type);
        logger.debug(JSONObject.toJSONString(oldFile));
        Assert.isTrue(oldFile.getAuth().equals(auth));
        Assert.isTrue(oldFile.getInfo().equals(info));
        Assert.isTrue(oldFile.getUrl().equals(url));
        Assert.isTrue(oldFile.getPid().equals("-1"));
        Assert.isTrue(oldFile.isLatest() == true);


        //第二次保存
        File newfile = new File();
        String newAuth = "zhoujie";
        String newInfo = "infomation";
        String newUrl = "www.zhoujie.com";
        newfile.setAuth(newAuth);
        newfile.setInfo(newInfo);
        newfile.setUrl(newUrl);
        newfile.setName(name);
        newfile.setType(type);
        fileService.addFile(newfile);

        File newFile = fileService.getLatestFile(name, type);
        Assert.isTrue(newFile.getAuth().equals(newAuth));
        Assert.isTrue(newFile.getInfo().equals(newInfo));
        Assert.isTrue(newFile.getUrl().equals(newUrl));
        Assert.isTrue(newFile.getPid().equals(oldFile.getId()));
        //Assert.isTrue(newFile.getCurVer().contains(oldFile.getId()));
        Assert.isTrue(newFile.isLatest() == true);

        File oldFile1 = fileService.get(oldFile.getId());
        Assert.isTrue(oldFile1.isLatest() == false);


        File filePre = fileService.getPreviousFile(name, type);
        Assert.isTrue(filePre.getId().equals(oldFile.getId()));
    }
}
