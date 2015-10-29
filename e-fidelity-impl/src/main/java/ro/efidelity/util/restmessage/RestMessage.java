package ro.efidelity.util.restmessage;

public class RestMessage {

	private String title;
	private Object detail;

	public RestMessage(String title) {
		super();
		this.title = title;
	}

	public RestMessage(String title, Object detail) {
		super();
		this.title = title;
		this.detail = detail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Object getDetail() {
		return detail;
	}

	public void setDetail(Object detail) {
		this.detail = detail;
	}

}
