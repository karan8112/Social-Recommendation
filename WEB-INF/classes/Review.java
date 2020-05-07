import java.io.*;

public class Review implements Serializable{
	private String Name;
	private String streetaddress;
	private String reviewRating;
	private String comment;
	private String userid;
	public Review (String Name,String streetaddress,String reviewRating,String comment,String userid) {
		this.Name = Name;
		this.streetaddress = streetaddress;
		this.reviewRating = reviewRating;
		this.comment=comment;
		this.userid= userid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Review() {}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getStreetaddress() {
		return streetaddress;
	}
	public void setStreetaddress(String streetaddress) {
		this.streetaddress = streetaddress;
	}
	public String getReviewRating() {
		return reviewRating;
	}
	public void setReviewRating(String reviewRating) {
		this.reviewRating = reviewRating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}