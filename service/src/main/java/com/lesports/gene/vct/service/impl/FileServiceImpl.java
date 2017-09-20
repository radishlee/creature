package com.lesports.gene.vct.service.impl;

import com.google.common.base.Strings;
import com.lesports.gene.vct.common.enumuration.FileTypeEnum;
import com.lesports.gene.vct.common.exception.ServiceException;
import com.lesports.gene.vct.common.model.File;
import com.lesports.gene.vct.common.utils.VersionGenerator;
import com.lesports.gene.vct.service.FileService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


/**
 * 版本仓库服务类
 * <p>
 * Created by radish on 9/11/15.
 */
@Service
public class FileServiceImpl extends BaseServiceImpl<String, File> implements FileService {

    /**
     * 新增
     *
     * @param file
     * @return
     */
    @Override
    public void addFile(File file) {
        this.checkNull(file);//检查是否为空

        //获取是否有上一个版本
        File latestFile = this.getLatestFile(file.getName(), file.getType());
        if (latestFile == null) {//第一次保存
            //file.setCurVer(VersionGenerator.generate(file));//设置版本号
        } else {//已经有过版本
            //file.setCurVer(VersionGenerator.generate(latestFile));//设置版本号
            file.setPid(latestFile.getId());//设置pid

            //更新旧版本
            this.mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(latestFile.getId())),
                    new Update().set("isLatest", false), File.class);
        }


        //保存为最新版本
        file.setIsLast(true);
        this.mongoTemplate.save(file);
    }


    /**
     * 获取最新版本的文件
     *
     * @param name
     * @return
     */
    @Override
    public File getLatestFile(String name, Integer type) {
        if (type == null) {
            throw new ServiceException("文件类型信息不能为空");
        }
        if (type < FileTypeEnum.IOS.getStatusValue() || type > FileTypeEnum.C1.getStatusValue()) {
            throw new ServiceException("文件类型信息不合法");
        }

        Query query = new Query().addCriteria(Criteria.where("type").is(type))
                .addCriteria(Criteria.where("isLatest").is(true));
        return this.mongoTemplate.findOne(query, File.class);
    }


    /**
     * 获取上一个版本的文件
     *
     * @param name
     * @return
     */
    @Override
    public File getPreviousFile(String name, Integer type) {
        return get(getLatestFile(name,type).getPid());
    }


    /**
     * 检查参数是否为空
     *
     * @param file
     */
    private void checkNull(File file) {
        if (Strings.isNullOrEmpty(file.getName())) {
            throw new ServiceException("文件名称不能为空");
        }
        if (Strings.isNullOrEmpty(file.getUrl())) {
            throw new ServiceException("文件保存路径不能为空");
        }
        if (Strings.isNullOrEmpty(file.getInfo())) {
            throw new ServiceException("文件描述信息不能为空");
        }
        if (file.getType() == null) {
            throw new ServiceException("文件类型信息不能为空");
        }
        if (file.getType() < FileTypeEnum.IOS.getStatusValue() || file.getType() > FileTypeEnum.C1.getStatusValue()) {
            throw new ServiceException("文件类型信息不合法");
        }
        if (Strings.isNullOrEmpty(file.getAuth())) {
            throw new ServiceException("编辑人员不能为空");
        }
    }


    /**
     * 检查名称和类型是否为空
     *
     * @param name
     * @param type
     *
     */
    private void nameFileCheckNull(String name, Integer type) {
        if (Strings.isNullOrEmpty(name)) {
            throw new ServiceException("文件名称不能为空");
        }
        if (type == null) {
            throw new ServiceException("文件类型信息不能为空");
        }
        if (type < FileTypeEnum.IOS.getStatusValue() || type > FileTypeEnum.C1.getStatusValue()) {
            throw new ServiceException("文件类型信息不合法");
        }
    }


}
