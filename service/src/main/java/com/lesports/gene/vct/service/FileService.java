package com.lesports.gene.vct.service;

import com.lesports.gene.vct.common.model.File;


/**
 *
 * 版本文件对象服务类
 *
 * Created by radish on 9/11/15.
 *
 */
public interface FileService extends BaseService<String, File> {

	/**
	 * 新增
	 *
	 * @param file
	 * @return
	 */
	void addFile(File file);


	/**
	 * 获取最新版本的文件
	 *
	 * @param name
	 * @return
	 */
	File getLatestFile(String name,Integer type);

	/**
	 * 获取上一个版本的文件
	 *
	 * @param name
	 * @return
	 */
	File getPreviousFile(String name, Integer type);

}
