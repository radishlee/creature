/**
 * 
 */
package com.lunchtech.vlbs.common.model;

/**
 * 角色
 */
public class Role extends BaseEntity{
	
	private String name; 	// 角色名称
	private String status; 		//是否是可用
	private String pid; //父亲角色id
	private String leval; //角色层级


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getLeval() {
		return leval;
	}

	public void setLeval(String leval) {
		this.leval = leval;
	}
}
