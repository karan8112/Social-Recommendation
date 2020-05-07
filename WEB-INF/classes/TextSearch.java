import com.google.gson.annotations.SerializedName;

public class TextSearch {
	
	@SerializedName("formatted_address")
	private String formatted_address;
	
	@SerializedName("icon")
	private String icon;
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("rating")
	private String rating;
	
	@SerializedName("user_ratings_total")
	private String user_ratings_total;
	
	@SerializedName("photo_url")
	private String photo_url;
	
	@SerializedName("lat")
	private String lat;

	@SerializedName("lng")
	private String lng;

	@SerializedName("gmap")
	private String gmap;

	@SerializedName("placeId")
	private String placeId;
	
	public String getPhoto_url() {
		return photo_url;
	}
	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}
	public String getFormatted_address() {
		return formatted_address;
	}
	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getUser_ratings_total() {
		return user_ratings_total;
	}
	public void setUser_ratings_total(String user_ratings_total) {
		this.user_ratings_total = user_ratings_total;
	}
	
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getGmap() {
		return gmap;
	}
	public void setGmap(String gmap) {
		this.gmap = gmap;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	
	
}
