package com.celink.xieservice.app.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import com.celink.xieservice.utils.DateUtils;

import net.sf.json.JSONObject;


/**
 * @ClassName: User
 * @Description: TODO(用户实体)
 * @author lifaqiu
 * @date 2014-1-17 上午10:59:49
 */
public class Order implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer userId;
	private Integer workerId;
	private Integer packageServiceId;
	private Integer serviceAddressId;
	private Integer isUseVoucher;
	private Integer state;
	private Integer isComment;
	private Float price;
	private Float sumPrice;
    private String orderName;
    private String serviceTime;
    private String additionalRequirements;
    private Timestamp updateTime;
    private Timestamp createDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getWorkerId() {
		return workerId;
	}

	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}

	public Integer getPackageServiceId() {
		return packageServiceId;
	}

	public void setPackageServiceId(Integer packageServiceId) {
		this.packageServiceId = packageServiceId;
	}

	public Integer getServiceAddressId() {
		return serviceAddressId;
	}

	public void setServiceAddressId(Integer serviceAddressId) {
		this.serviceAddressId = serviceAddressId;
	}

	public Integer getIsUseVoucher() {
		return isUseVoucher;
	}

	public void setIsUseVoucher(Integer isUseVoucher) {
		this.isUseVoucher = isUseVoucher;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getIsComment() {
		return isComment;
	}

	public void setIsComment(Integer isComment) {
		this.isComment = isComment;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Float sumPrice) {
		this.sumPrice = sumPrice;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getAdditionalRequirements() {
		return additionalRequirements;
	}

	public void setAdditionalRequirements(String additionalRequirements) {
		this.additionalRequirements = additionalRequirements;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getCreateDateStr() {
		return DateUtils.dateToInputStrAppendTime(createDate);
	}
	public String getUpdateTimeStr() {
		return DateUtils.dateToInputStrAppendTime(updateTime);
	}

	@Override
	public String toString()
	{
		return JSONObject.fromObject(this).toString();
	}

}
