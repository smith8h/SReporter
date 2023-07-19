package smith.lib.net.reporter;

public interface ReporterCallBack {
	public void onSuccess();
	public void onFailure(String failureMessage);
}