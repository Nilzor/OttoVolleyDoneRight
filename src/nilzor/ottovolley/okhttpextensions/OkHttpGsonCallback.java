package nilzor.ottovolley.okhttpextensions;


public  interface OkHttpGsonCallback<T> {
    public void onResponse(OkHttpGsonResponse<T> response);
}