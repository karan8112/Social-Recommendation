import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;

public class User implements Serializable{
	private int id;
	private String name;
	private String password;
	private String usertype;
	private String streetaddress;
	private String cityname;
	private String state;
	private String userid;
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getStreetaddress() {
		return streetaddress;
	}

	public void setStreetaddress(String streetaddress) {
		this.streetaddress = streetaddress;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLatlong() {
		return latlong;
	}

	public void setLatlong(String latlong) {
		this.latlong = latlong;
	}

	private String zipcode;
	private String country;
	private String latlong;
	
	public User(String name, String password, String usertype) {
		this.name=name;
		this.password=password;
		this.usertype=usertype;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public User(String name, String usertype, String streetaddress, String cityname, String state, String zipcode,
			String country, String latlong) {
		super();
		this.name = name;
		this.usertype = usertype;
		this.streetaddress = streetaddress;
		this.cityname = cityname;
		this.state = state;
		this.zipcode = zipcode;
		this.country = country;
		this.latlong = latlong;
	}

	public User(String latlong) {
		super();
		this.latlong = latlong;
	}

	public User(String name, String password, String usertype,String latlong,String userid) {
		this.name=name;
		this.password=password;
		this.usertype=usertype;
		this.latlong = latlong;
		this.userid=userid;
	}

	
}
