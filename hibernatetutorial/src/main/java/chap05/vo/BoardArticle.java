package chap05.vo;

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
	private String content;
	private Date writeDate;

	public BoardArticle() {}

	public BoardArticle(String userId, String content, Date writeDate) {
		super();
		this.userId = userId;
		this.content = content;
		this.writeDate = writeDate;
	}

	public BoardArticle(int id, String userId, String content, Date writeDate) {
		super();
		this.id = id;
		this.userId = userId;
		this.content = content;
		this.writeDate = writeDate;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoardArticle [id=");
		builder.append(id);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", content=");
		builder.append(content);
		builder.append(", writeDate=");
		builder.append(writeDate);
		builder.append("]");
		return builder.toString();
	}
}
