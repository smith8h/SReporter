package smith.lib.net.reporter;

public interface ReporterCallBack {
    
	public void onSuccess(String message);
	public void onFail(String message);
    
}