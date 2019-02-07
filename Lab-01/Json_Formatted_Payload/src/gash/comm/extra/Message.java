package gash.comm.extra;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import gash.comm.payload.BasicBuilder;
import gash.comm.payload.BasicBuilder.MessageType;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Message {
	private String type;
	private String source;
	private Date sent;
	private Date received;
	private String payload;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getReceived() {
		return received;
	}

	public void setReceived(Date received) {
		this.received = received;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String body) {
		this.payload = body;
	}
}
