package utils;

public class Response<T> {

	public enum Status {
		ERROR, WARNING, SUCCESS
	}

	private Status status;
	private T data;
	private String message;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return status == Status.SUCCESS;
	}
}
