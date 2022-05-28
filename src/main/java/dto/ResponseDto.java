package dto;

public class ResponseDto {

	private String status;
	private String mensage;
	
	
	
	public ResponseDto(String status, String mensage) {
		this.status = status;
		this.mensage = mensage;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMensage() {
		return mensage;
	}
	public void setMensage(String mensage) {
		this.mensage = mensage;
	}
	
	
	
}
