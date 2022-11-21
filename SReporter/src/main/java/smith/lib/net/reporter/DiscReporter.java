package smith.lib.net.reporter;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import smith.lib.net.SConnect;
import smith.lib.net.SConnectCallBack;
import smith.lib.net.reporter.DiscEmbed;
import smith.lib.net.reporter.Json;
import smith.lib.net.reporter.ReporterCallBack;

public class DiscReporter {
    
    private String webhook, content, username, avatarUrl;
    private boolean tts;
    private Context context;
    private List<DiscEmbed> embeds = new ArrayList<>();
    private ReporterCallBack callback;
    
    public DiscReporter(Context context) {
        this.context = context;
    }
    
    public void setWeebHook(String webhook) {
        this.webhook = webhook;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setTts(boolean tts) {
        this.tts = tts;
    }

    public void addEmbed(DiscEmbed embed) {
        this.embeds.add(embed);
    }
    
    public void setReportCallBack(ReporterCallBack callback) {
        this.callback = callback;
    }

    public void sendReport() {
        
    }
}