package nilzor.ottovolley.messages;

public class VolleyRequestSuccess<T> {
    public T response;
    public int requestId;
    public VolleyRequestSuccess(int requestId, T response) {
        this.requestId = requestId;
        this.response = response;
    }
}
