package com.celink.xieservice.app.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import com.celink.xieservice.utils.DateUtils;

import net.sf.json.JSONObject;


/**
 * @ClassName: Advertise
 * @Description: TODO(广告实体)
 * @author lifaqiu
 * @date 2014-1-17 上午10:59:49
 */
public class Advertise implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String type;
    private String title;
    private String image;
    private String link;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString()
	{
		return JSONObject.fromObject(this).toString();
	}

}
