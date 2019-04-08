package com.zejian.emotionkeyboard.utils;
/**
 * 聊天页面点击 + 号需要显示的View中的数据类型
 * @author aculearn
 *
 */
public class ChatMsgType {
	private String id;
	private String content;
	private int rid;
	private int type ;

	public static final int CHAT_OPTION = 0;
	public static final int CHAT_OPTION_PHOTO_FROMLOCAL = 1;
	public static final int CHAT_OPTION_PHOTO_FROMCAMARA = 2;
	public static final int CHAT_OPTION_VEDIO_FROMLOCAL = 3;
	public static final int CHAT_OPTION_VEDIO_FROMCAMARA = 4;
	public static final int CHAT_OPTION_VOICECALL = 5;
//	public static int CHAT_OPTION_CALLTYPE_VIDEO = 6;
//	public static int CHAT_OPTION_CALLTYPE_AUDIO = 7;
	public static final int CHAT_OPTION_LOCATION = 8;
	public static final int CHAT_OPTION_CONTACT = 9;
	public static final int CHAT_OPTION_LINK = 10;
	public static final int CHAT_OPTION_DESTRUCT_CHAT = 11;
	public static final int CHAT_OPTION_LOCATION_CHAT = 12;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ChatMsgType() {
		super();
	}

	public ChatMsgType(String id, String content, int rid) {
		super();
		this.id = id;
		this.content = content;
		this.rid = rid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}
	
}
