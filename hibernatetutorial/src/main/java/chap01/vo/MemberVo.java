package chap01.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity		// javax.persistence에서 import함. 테이블에 매핑하겠다는 의미
public class MemberVo {
	@Id					// primary key
	@GeneratedValue		// auto increase
	private int id;
	private String name;
	private String message;

	public MemberVo() {}	// default constructor 필요

	public MemberVo(String name, String message) {
		super();
		this.name = name;
		this.message = message;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Member [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
}