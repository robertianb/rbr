package lbc.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;

public class OfferItem implements Item {

	final static SimpleDateFormat dateFormat = new SimpleDateFormat();

	private String title;
	private String description;
	private String placeName;
	private String zipCode;
	private String category;
	private String id;
	private String url;
	private Float price;
	private Date creationDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getId() {
		return (id == null?url:id);
	}

	public void setId(String id) {
		this.id = id;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	static OfferItem fromJSon(JSONObject json) {
		OfferItem item = new OfferItem();

		item.title = (String) json.get("title");
		item.description = (String) json.get("description");
		item.placeName = (String) json.get("placeName");
		item.zipCode = (String) json.get("zipCode");
		item.category = (String) json.get("category");
		item.id = (String) json.get("id");
		item.url = (String) json.get("url");
		Number priceS = ((Number) json.get("price"));
		if (priceS != null) {
			item.price = priceS.floatValue();
		}
		String creationDateS = (String) json.get("creationDate");
		if (creationDateS != null) {
			try {
				item.creationDate = dateFormat.parse(creationDateS);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return item;
	}

	static JSONObject toJSon(OfferItem item) {
		JSONObject json = new JSONObject();

		if (item.title != null) {
			json.put("title", item.title);
		}
		if (item.description != null) {
			json.put("description", item.description);
		}
		if (item.placeName != null) {
			json.put("placeName", item.placeName);
		}
		if (item.zipCode != null) {
			json.put("zipCode", item.zipCode);
		}
		if (item.category != null) {
			json.put("category", item.category);
		}
		if (item.getId() != null) {
			json.put("id", item.getId());
		}
		if (item.url != null) {
			json.put("url", item.url);
		}
		if (item.price != null) {
			json.put("price", item.price);
		}
		if (item.creationDate != null) {
			json.put("creationDate", dateFormat.format(item.creationDate));
		}
		return json;
	}

	@Override
	public String toString() {
		return "OfferItem [" + title + ", Id:" + getId() + ", cat:" + category + ", price=" + price + (creationDate != null?", date=" + dateFormat.format(creationDate):"") + "]";
	}
	
	

}
