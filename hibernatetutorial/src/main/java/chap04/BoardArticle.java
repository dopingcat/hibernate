package chap04;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BoardArticle {
	@Id
	@GeneratedValue
	private int id;
	private String userId;
	private String message;
	private Date dateWrite;

	public BoardArticle() {}

	public BoardArticle(String userId, String message, Date dateWrite) {
		super();
		this.userId = userId;
		this.message = message;
		this.dateWrite = dateWrite;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDateWrite() {
		return dateWrite;
	}

	public void setDateWrite(Date dateWrite) {
		this.dateWrite = dateWrite;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoardArticle [id=");
		builder.append(id);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", message=");
		builder.append(message);
		builder.append(", dateWrite=");
		builder.append(dateWrite);
		builder.append("]");
		return builder.toString();
	}
}
