package com.celink.xieservice.app.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;


/**
 * @ClassName: User
 * @Description: TODO(用户实体)
 * @author lifaqiu
 * @date 2014-1-17 上午10:59:49
 */
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String account;
    private String password;
    private String nickName;
    private String icon;
    private String email;
    private String address;
    private Date birthday;
    private Date pwdAnswer;
    private Timestamp registDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getPwdAnswer() {
		return pwdAnswer;
	}

	public void setPwdAnswer(Date pwdAnswer) {
		this.pwdAnswer = pwdAnswer;
	}

	public Timestamp getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Timestamp registDate) {
		this.registDate = registDate;
	}

	@Override
	public String toString()
	{
		return JSONObject.fromObject(this).toString();
	}

}
