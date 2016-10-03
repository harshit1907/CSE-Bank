package csebank_objectmodel.asu.edu;

public class Device {

	private String deviceId;
	private String userId;
	
	public Device() {
		this.deviceId = null;
		this.userId = null;
	}
	
	public Device(String deviceId, String userId) {
		this.deviceId = deviceId;
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}