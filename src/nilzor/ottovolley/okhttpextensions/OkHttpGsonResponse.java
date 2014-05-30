package nilzor.ottovolley.okhttpextensions;


public  class OkHttpGsonResponse<T> {
    public com.squareup.okhttp.Request Request;
    public com.squareup.okhttp.Response Response;
    public T Payload;
    public Throwable Error;
}
