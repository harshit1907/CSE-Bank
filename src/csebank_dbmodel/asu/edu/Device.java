package csebank_dbmodel.asu.edu;

public class Device {

	private String DeviceId;
	private String UserId;
	
	public Device() {
		this.DeviceId = null;
		this.UserId = null;
	}
	
	public Device(String deviceId, String userId) {
		this.DeviceId = deviceId;
		this.UserId = userId;
	}

	public String getDeviceId() {
		return DeviceId;
	}

	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}
	
	
	
}
