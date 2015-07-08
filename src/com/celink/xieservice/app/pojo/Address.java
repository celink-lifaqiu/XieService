package com.celink.xieservice.app.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import com.celink.xieservice.utils.DateUtils;

import net.sf.json.JSONObject;


/**
 * @ClassName: Address
 * @Description: TODO(地址实体)
 * @author lifaqiu
 * @date 2014-1-17 上午10:59:49
 */
public class Address implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private Integer userId;
    private String reservation;
    private String phone;
    private String districtInformation;
    private String addresss;
    private Integer idDefaultServiceAddress;

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

	public String getReservation() {
		return reservation;
	}

	public void setReservation(String reservation) {
		this.reservation = reservation;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDistrictInformation() {
		return districtInformation;
	}

	public void setDistrictInformation(String districtInformation) {
		this.districtInformation = districtInformation;
	}

	public String getAddresss() {
		return addresss;
	}

	public void setAddresss(String addresss) {
		this.addresss = addresss;
	}

	public Integer getIdDefaultServiceAddress() {
		return idDefaultServiceAddress;
	}

	public void setIdDefaultServiceAddress(Integer idDefaultServiceAddress) {
		this.idDefaultServiceAddress = idDefaultServiceAddress;
	}

	@Override
	public String toString()
	{
		return JSONObject.fromObject(this).toString();
	}

}
