package concurrency;

public interface Callback<Type> {
    public void onSuccess(Type result);

    // definendo il metodo come default e' possibile definirne il comportamento nell'interface 
    public default void onFailure(Throwable throwable) {
        throwable.printStackTrace();
    }
}
