    /*
	*
	*
	*    THIS LIBRARY CREATED BY HUSSEIN SHAKIR (SMITH)
	*
	*	TELEGRAM : @SMITHDEV
	*	YOUTUBE : HUSSEIN SMITH
	*
	*	YOU GUYS ARE NOT ALLOWED TO MODIFY THIS LIBRARY,
	*	WITHOT ANY PERMISSION FROM ME PERSONALLY..
	*	ALL RIGHTS RESERVED © HUSSEIN SHAKIR, 2022.
	*
	*
	*/

package smith.lib.net.reporter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;
import java.util.HashMap;
import smith.lib.net.SConnect;
import smith.lib.net.SConnectCallBack;

public class TeleReporter {
	
    public static final int USER_INFO = 0;
    public static final int CUSTOM = 1;
    
	private String botToken, chatUsername;
    private String reportMessage, reportHeader, reportSubHeader, reportFooter;
    private String deviceModel, androidVersion, appVersion, appName;
    private String noTokenMsg, noUsernameMsg, failMsg, malformedUrl, noInternet;
	
    private ReporterCallBack callback;
    private Context context;
	private PackageManager manager;
    private PackageInfo info;
    
    public TeleReporter(Context context) {
        this.context = context;
        noTokenMsg = context.getString(R.string.tele_reporter_no_token_msg);
        noUsernameMsg = context.getString(R.string.tele_reporter_no_id_msg);
        failMsg = context.getString(R.string.tele_reporter_failed);
		reportHeader = context.getString(R.string.tele_reporter_header_title, getAppName());
        reportMessage = context.getString(R.string.tele_reporter_no_msg);
    	noInternet = context.getString(R.string.tele_reporter_no_internet);
        malformedUrl = context.getString(R.string.tele_reporter_malformed_url);
        try {
            manager = context.getPackageManager();
            info = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {}
    }
        
    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public void setTargetChatId(String chatUsername) {
        this.chatUsername = chatUsername;
    }
        
    public void setReportHeader(String reportHeader) {
        if (!reportHeader.isEmpty()) this.reportHeader = reportHeader;
    }
    
    public void setReportSubHeader(String reportSubHeader) {
        this.reportSubHeader = reportSubHeader;
    }

    public void setReportMessage(String reportMessage) {
        this.reportMessage = reportMessage;
    }
    
    public void setReportFooter(int footerType, String reportFooter) {
        switch (footerType) {
            case USER_INFO : this.reportFooter = getUserInfo(); break;
            case CUSTOM : this.reportFooter = reportFooter; break;
        }
    }
	
	public void setReportCallBack(ReporterCallBack callback) {
        this.callback = callback;
	}
    
    public void sendReport() {
        if (botToken.isEmpty()) callback.onFail(noTokenMsg);
		else if (chatUsername.isEmpty()) callback.onFail(noUsernameMsg);
		else if (!SConnect.isDeviceConnected(context)) callback.onFail(noInternet);
		else try {
            String url = getFinalURL();
            URL u = new URL(url);
            SConnect connect = new SConnect(context);
            connect.setCallBack(new SConnectCallBack() {
                @Override public void response(String response, String tag, HashMap<String, Object> headers) {
                    callback.onSuccess();
                }
                
                @Override public void responseError(String message, String tag) {
                    callback.onFail(failMsg);
                }
            });
            connect.connect(SConnect.GET, url, "sendTeleReport");
        } catch (MalformedURLException e) { callback.onFail(malformedUrl); }
    }
    
    public String getReport() {
        return getFinalReport();
    }
    
    public String getReportURL() {
        return getFinalURL();
    }
	
	private String getFinalURL() {
		String finalUrl = "";
		try {
			finalUrl = "https://api.telegram.org/bot" + botToken
		    + "/sendMessage?chat_id=" + chatUsername + "&text=" + URLEncoder.encode(getFinalReport(), StandardCharsets.UTF_8.toString())
            + "&parse_mode=Markdown&disable_web_page_preview=true";
		} catch (Exception e) {}
		return finalUrl;
	}
	
	private String getFinalReport() {
		StringBuffer buffer = new StringBuffer();
        
        buffer.append("**" + reportHeader + "**\n");
        if (!reportSubHeader.isEmpty()) buffer.append(reportSubHeader + "\n");
        buffer.append("\n**" + context.getString(R.string.tele_reporter_message_title) + "**\n");
		buffer.append(reportMessage + "\n\n");
        buffer.append("**" + context.getString(R.string.tele_reporter_footer_title) + "**\n");
        buffer.append(reportFooter);
        
        return buffer.toString();
	}
	
	private String getUserInfo() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(context.getString(R.string.tele_reporter_device_model, Build.MANUFACTURER, Build.MODEL) + "\n");
        buffer.append(context.getString(R.string.tele_reporter_android_version, Build.VERSION.RELEASE, Build.VERSION.CODENAME) + "\n");
        buffer.append(context.getString(R.string.tele_reporter_app_used, getAppName(), info.versionName));
        return buffer.toString();
    }
	
	private String getAppName() {
		ApplicationInfo applicationInfo = context.getApplicationInfo();
		int stringId = applicationInfo.labelRes;
		return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
	}
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Bot Token        | " + botToken + "\n");
        builder.append("Target Chat      | " + chatUsername + "\n");
        builder.append("Report Header    | " + reportHeader + "\n");
        builder.append("Report SubHeader | " + reportSubHeader + "\n");
        builder.append("Report Body      | " + reportMessage + "\n");
        builder.append("Report Footer    | " + reportFooter + "\n");
        builder.append("Device Info.     | " + getUserInfo() + "\n");
        builder.append("Used App Name    | " + getAppName() + "\n");
        builder.append("Final API URL    | " + getFinalURL());
        return builder.toString();
    }
}