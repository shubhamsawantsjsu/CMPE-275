package gash.comm.payload;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import gash.comm.extra.JsonBuilder;
import gash.comm.extra.Message;
import gash.comm.payload.BasicBuilder.MessageType;

/**
 * the builder to construct commands that both the client (BasicSocketClient)
 * and server (BasicSocketServer) understands/accepts
 * 
 * @author gash
 * 
 */
public class BasicBuilder {
	public enum MessageType {
		ping, join, leave, msg, list, stat
	}

	protected static final String sMsgMarkerStart = "[";
	protected static final String sMsgMarkerEnd = "]";
	protected static final String sHeaderMarker = "!h!";
	protected static final String sBodyMarker = "!b!";

	protected static final String sMsgMarkerStartRX = "\\[";

	private String incompleteBuffer;

	public BasicBuilder() {
	}
	
	
	/*
	 * 
	 * private MessageType type;
	private String source;
	private Date sent;
	private Date received;
	private String payload;
	
	 */
	
	/*public String encode(MessageType type, String source, String body, Date received) {
		
		Map<String, String> inputMap = new HashMap<>();
		if(type!=null)
			inputMap.put("type", type.toString());
		else 
			inputMap.put("type", null);
		
		inputMap.put("source", source);
		
		if(body==null || body.isEmpty())
			inputMap.put("payload", "");
		else 
			inputMap.put("payload", body);
		
		/*
		 * if(received!=null) inputMap.put("received", received.toString()); else
		 * inputMap.put("received", null);
		 
		
		System.out.println("Came inside the encode method....");
			
		String encodedOutput = JsonBuilder.encode(inputMap);
		System.out.println("--------------------------Encoded Output starts ------------------------------------");
		System.out.println(encodedOutput);
		System.out.println("--------------------------Encoded Output Ends ------------------------------------");
		
		return encodedOutput;
	}*/
	
	public String encode(MessageType type, String source, String body, Date received) {
		Message messageObject = new Message();
		
		messageObject.setPayload(body);
		messageObject.setReceived(received);
		messageObject.setType(type.toString());
		
		String encodedOutput = JsonBuilder.encode(messageObject);
		
		return encodedOutput;
	}
	
	/*
	public String encode(MessageType type, String source, String body, Date received) {
		String payload = null;
		if (body != null)
			payload = body.trim();

		DecimalFormat fmt = new DecimalFormat("0000");
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(sHeaderMarker);
		sb.append(type);
		sb.append(',');
		sb.append(System.currentTimeMillis());
		sb.append(',');
		if (received != null)
			sb.append(received);
		else 
			sb.append("null-received");
		sb.append(',');
		if (source != null)
			sb.append(source.trim());
		else 
			sb.append("null-source");
		sb.append(',');
		if (payload == null) {
			sb.append(0);
			sb.append(sBodyMarker);
		} else {
			sb.append(fmt.format(payload.length()));
			sb.append(sBodyMarker);
			if (payload != null)
				sb.append(payload);
		}

		String msg = sb.toString();

		sb = new StringBuilder();
		sb.append(sMsgMarkerStart);

		sb.append(fmt.format(msg.length()));
		sb.append(msg);
		sb.append(sMsgMarkerEnd);

		return sb.toString();
	}
	*/
	
	public List<Message> decode(byte[] raw) throws Exception {
		if (raw == null || raw.length == 0)
			return null;

		String s = new String(raw);
		if (incompleteBuffer != null) {
			s = incompleteBuffer + s;
		}
		
		List<Message> messages = new ArrayList<>();
		
		for (String str : getSeparateJsonObjects(s)) {
			messages.add(JsonBuilder.decode(str));
			
		}
		
		return messages;
	}
	
	private List<String> getSeparateJsonObjects(String str) {
		
		List<String> output = new ArrayList<>();
		
		int index = 0;
		int len = str.length();
		
		while(index<len) {
			
			int start = index;
			
			while(str.charAt(index)!='}') {
				index++;
			}
			
			output.add(str.substring(start, index+1));
			index+=1;
			start = index;
		}
		
		return output;
	}
	
	/*
	public List<Message> decode(byte[] raw) throws Exception {
		if (raw == null || raw.length == 0)
			return null;

		String s = new String(raw);
		if (incompleteBuffer != null) {
			s = incompleteBuffer + s;
		}

		String[] msgs = s.split(sMsgMarkerStartRX);
		ArrayList<Message> rtn = new ArrayList<Message>();
		for (String m : msgs) {
			if (m.length() == 0)
				continue;

			// incomplete message
			if (!m.endsWith(sMsgMarkerEnd)) {
				incompleteBuffer = sMsgMarkerStart + m;
				break;
			} else
				incompleteBuffer = null;

			// TODO use slf4j
			System.out.println("--> m (size = " + m.length() + "): " + m);

			String[] hdr = m.split(sHeaderMarker);
			if (hdr.length != 2)
				throw new RuntimeException("Unexpected message format");

			// TODO handle condition where the message size does not match. Note
			// end marker

			String t = hdr[1];
			String[] bd = t.split(sBodyMarker);
			if (bd.length != 2)
				throw new RuntimeException("Unexpected message format (2)");

			String body = bd[1].substring(0, bd[1].length() - 1);
			String header = bd[0];

			String[] hparts = header.split(",");
			if (hparts.length != 5)
				throw new RuntimeException("Unexpected message format (3)");

			Message bo = new Message();
			bo.setType(MessageType.valueOf(hparts[0]));

			if (hparts[1].length() > 0)
				bo.setReceived(new Date(Long.parseLong(hparts[1])));

			// entry 2 is not used

			bo.setSource(hparts[3]);

			int bodySize = Integer.parseInt(hparts[4]);
			if (bodySize != body.length())
				throw new RuntimeException("Body does not match checksum");

			bo.setPayload(body);

			// TODO use slf4j
			System.out.println("--> h: " + header);
			System.out.println("--> b: " + body);

			rtn.add(bo);
		}

		return rtn;
	}
	*/

	public void reset() {
		incompleteBuffer = null;
	}

	public boolean isComplete() {
		return (incompleteBuffer == null);
	}
}
