package com.lesports.gene.vct.service.dto;

public class S3File {
	//一个标准的s3文件地址为
	//http://s3-cdn.lecloud.com/{bucketname}/{objectname}
	private String bucketName;
	private String objectName;
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	
}
