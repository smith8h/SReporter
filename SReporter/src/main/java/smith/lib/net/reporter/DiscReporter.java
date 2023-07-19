package smith.lib.net.reporter;

import android.app.Activity;
import android.content.Context;
import java.util.*;
import smith.lib.net.*;

public class DiscReporter {
    
    private String webhook;
    private String content;
    private String username = "SReporter";
    private String avatarUrl = "https://te.legra.ph/file/e86668a3699571a74c411.png";
    
    private boolean tts = false;
    
    private final Context context;
    
    private final List<DiscEmbed> embeds = new ArrayList<>();
    
    private ReporterCallBack callback;
    
    private final HashMap<String, Object> headers = new HashMap<>();
    private final HashMap<String, Object> params = new HashMap<>();
    
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
        if (!SConnect.isDeviceConnected(context)) callback.onFailure(context.getString(R.string.tele_reporter_no_internet));
        else {
            if (content == null) callback.onFailure(context.getString(R.string.disc_reporter_no_content));
            else if (webhook.isEmpty()) callback.onFailure(context.getString(R.string.disc_reporter_no_webhook));
            else {
                SConnect connect = SConnect.with((Activity) context).callback(new SConnectCallBack() {
                    @Override public void onSuccess(SResponse response, String tag, Map<String, Object> headers) {
                        callback.onSuccess();
                    }
                    
                    @Override public void onFailure(SResponse response, String tag) {
                        callback.onFailure(context.getString(R.string.tele_reporter_failed));
                    }
                });
                
                params.put("content", content);
                params.put("username", username);
                params.put("avatar_url", avatarUrl);
                params.put("tts", tts);
                
                if (!embeds.isEmpty()) {
                    List<HashMap<String, Object>> embedObjects = new ArrayList<>();

                    embeds.forEach(embed -> {
                        HashMap<String, Object> embedObject = new HashMap<>();

                        embedObject.put("title", embed.getTitle());
                        embedObject.put("description", embed.getDescription());
                        embedObject.put("url", embed.getURL());

                        DiscEmbed.Footer footer = embed.getFooterEmbed();
                        DiscEmbed.Image image = embed.getImageEmbed();
                        DiscEmbed.Thumbnail thumbnail = embed.getThumbnailEmbed();
                        DiscEmbed.Author author = embed.getAuthorEmbed();
                        List<DiscEmbed.Field> fields = embed.getFields();
                        
                        if (footer != null) {
                            HashMap<String, Object> jsonFooter = new HashMap<>();
                            jsonFooter.put("text", footer.getText());
                            jsonFooter.put("icon_url", footer.getIconURL());
                            embedObject.put("footer", jsonFooter);
                        }

                        if (image != null) {
                            HashMap<String, Object> jsonImage = new HashMap<>();
                            jsonImage.put("url", image.getURL());
                            embedObject.put("image", jsonImage);
                        }

                        if (thumbnail != null) {
                            HashMap<String, Object> jsonThumbnail = new HashMap<>();
                            jsonThumbnail.put("url", thumbnail.getURL());
                            embedObject.put("thumbnail", jsonThumbnail);
                        }

                        if (author != null) {
                            HashMap<String, Object> jsonAuthor = new HashMap<>();
                            jsonAuthor.put("name", author.getName());
                            jsonAuthor.put("url", author.getURL());
                            jsonAuthor.put("icon_url", author.getIconURL());
                            embedObject.put("author", jsonAuthor);
                        }
                        
                        List<HashMap<String, Object>> jsonFields = new ArrayList<>();
                        for (DiscEmbed.Field field : fields) {
                            HashMap<String, Object> jsonField = new HashMap<>();
                            jsonField.put("name", field.getName());
                            jsonField.put("value", field.getValue());
                            jsonField.put("inline", field.isInline());
                            jsonFields.add(jsonField);
                        }

                        embedObject.put("fields", jsonFields.toArray());
                        embedObjects.add(embedObject);
                    });

                    params.put("embeds", embedObjects.toArray());
                    connect.params(params, SConnect.PARAM);
                }
                
                headers.put("Content-Type", "application/json");
                headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36");
                connect.headers(headers).tag("SendingDiscordReport").post();
            }
        }
    }
}