package smith.lib.net.reporter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.*;
import android.os.Build;
import androidx.annotation.NonNull;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import smith.lib.net.*;

public class TeleReporter {
	
    public static final int USER_INFO = 0;
    public static final int CUSTOM = 1;
    
	private String botToken, chatUsername;
    private int targetTopic = 0;
    private String reportMessage, reportHeader, reportSubHeader, reportFooter;
    private String deviceModel, androidVersion, appVersion, appName;
    private final String noTokenMsg, noUsernameMsg, failMsg, malformedUrl, noInternet;
	
    private ReporterCallBack callback;
    private final Context context;
    private PackageInfo info;
    
    public TeleReporter(@NonNull Context context) {
        this.context = context;
        noTokenMsg = context.getString(R.string.tele_reporter_no_token_msg);
        noUsernameMsg = context.getString(R.string.tele_reporter_no_id_msg);
        failMsg = context.getString(R.string.tele_reporter_failed);
		reportHeader = context.getString(R.string.tele_reporter_header_title, getAppName());
        reportMessage = context.getString(R.string.tele_reporter_no_msg);
    	noInternet = context.getString(R.string.tele_reporter_no_internet);
        malformedUrl = context.getString(R.string.tele_reporter_malformed_url);
        try {
            PackageManager manager = context.getPackageManager();
            info = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException ignored) {}
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public void setTargetChatId(String chatUsername) {
        this.chatUsername = chatUsername;
    }

    public void setTargetChatTopic(int targetTopic) {
        this.targetTopic = targetTopic;
    }

    public void setReportHeader(@NonNull String reportHeader) {
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
            case USER_INFO -> this.reportFooter = getUserInfo();
            case CUSTOM -> this.reportFooter = reportFooter;
        }
    }
	
	public void setReportCallBack(ReporterCallBack callback) {
        this.callback = callback;
	}
    
    public void sendReport() {
        if (botToken.isEmpty()) callback.onFailure(noTokenMsg);
		else if (chatUsername.isEmpty()) callback.onFailure(noUsernameMsg);
		else if (!SConnect.isDeviceConnected(context)) callback.onFailure(noInternet);
		else try {
            String url = getFinalURL();
            URL u = new URL(url);
            SConnect.with((Activity) context)
                    .callback(new SConnectCallBack() {
                        @Override public void onSuccess(SResponse response, String tag, Map<String, Object> responseHeaders) {
                            callback.onSuccess();
                        }

                        @Override public void onFailure(SResponse response, String tag) {
                            callback.onFailure(failMsg);
                        }
                    })
                    .url(url).tag("sendTeleReport").get();
        } catch (MalformedURLException e) { callback.onFailure(malformedUrl); }
    }
    
    public String getReport() {
        return getFinalReport();
    }
    
    public String getReportURL() {
        return getFinalURL();
    }
	
	@NonNull
    private String getFinalURL() {
        StringBuilder finalUrl = new StringBuilder();

        finalUrl.append("https://api.telegram.org/bot").append(botToken);
        finalUrl.append("/sendMessage?chat_id=").append(chatUsername);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                finalUrl.append("&text=").append(URLEncoder.encode(getFinalReport(), StandardCharsets.UTF_8));
            else finalUrl.append("&text=").append(URLEncoder.encode(getFinalReport(), StandardCharsets.UTF_8.toString()));
        } catch (Exception ignored) {}

        if (targetTopic > 0) finalUrl.append("&message_thread_id=").append(targetTopic);

        finalUrl.append("&parse_mode=MarkDown&disable_web_page_preview=true");

        return finalUrl.toString();
	}
	
	@NonNull
    private String getFinalReport() {
		StringBuilder buffer = new StringBuilder();
        
        buffer.append("**").append(reportHeader).append("**\n");
        if (!reportSubHeader.isEmpty()) buffer.append(reportSubHeader).append("\n");
        buffer.append("\n**").append(context.getString(R.string.tele_reporter_message_title)).append("**\n");
		buffer.append(reportMessage).append("\n\n");
        buffer.append("**").append(context.getString(R.string.tele_reporter_footer_title)).append("**\n");
        buffer.append(reportFooter);
        
        return buffer.toString();
	}
	
	@NonNull
    private String getUserInfo() {
        return context.getString(R.string.tele_reporter_device_model, Build.MANUFACTURER, Build.MODEL) + "\n" +
                context.getString(R.string.tele_reporter_android_version, Build.VERSION.RELEASE, Build.VERSION.CODENAME) + "\n" +
                context.getString(R.string.tele_reporter_app_used, getAppName(), info.versionName);
    }
	
	private String getAppName() {
		ApplicationInfo applicationInfo = context.getApplicationInfo();
		int stringId = applicationInfo.labelRes;
		return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
	}
    
    @NonNull
    @Override
    public String toString() {
        return "Bot Token        | " + botToken + "\n" +
                "Target Chat      | " + chatUsername + "\n" +
                "Report Header    | " + reportHeader + "\n" +
                "Report SubHeader | " + reportSubHeader + "\n" +
                "Report Body      | " + reportMessage + "\n" +
                "Report Footer    | " + reportFooter + "\n" +
                "Device Info.     | " + getUserInfo() + "\n" +
                "Used App Name    | " + getAppName() + "\n" +
                "Final API URL    | " + getFinalURL();
    }
}