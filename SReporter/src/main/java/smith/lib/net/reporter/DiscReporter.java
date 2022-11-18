package smith.lib.net.reporter;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import smith.lib.net.reporter.DiscEmbed;
import smith.lib.net.reporter.Json;

public class DiscReporter {
    
    private String url;
    private String content;
    private String username;
    private String avatarUrl;
    private boolean tts;
    private List<DiscEmbed> embeds = new ArrayList<>();

    public DiscReporter(String url) {
        this.url = url;
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

    public void execute() {
        if (this.content == null && this.embeds.isEmpty()) {
            throw new IllegalArgumentException("Set content or add at least one EmbedObject");
        }

        Json json = new Json();

        json.put("content", this.content);
        json.put("username", this.username);
        json.put("avatar_url", this.avatarUrl);
        json.put("tts", this.tts);

        if (!this.embeds.isEmpty()) {
            List<Json> embedObjects = new ArrayList<>();

            for (DiscEmbed embed : this.embeds) {
                Json jsonEmbed = new Json();

                jsonEmbed.put("title", embed.getTitle());
                jsonEmbed.put("description", embed.getDescription());
                jsonEmbed.put("url", embed.getUrl());

                DiscEmbed.Footer footer = embed.getFooter();
                DiscEmbed.Image image = embed.getImage();
                DiscEmbed.Thumbnail thumbnail = embed.getThumbnail();
                DiscEmbed.Author author = embed.getAuthor();
                List<DiscEmbed.Field> fields = embed.getFields();

                if (footer != null) {
                    Json jsonFooter = new Json();

                    jsonFooter.put("text", footer.getText());
                    jsonFooter.put("icon_url", footer.getIconUrl());
                    jsonEmbed.put("footer", jsonFooter);
                }

                if (image != null) {
                    Json jsonImage = new Json();

                    jsonImage.put("url", image.getUrl());
                    jsonEmbed.put("image", jsonImage);
                }

                if (thumbnail != null) {
                    Json jsonThumbnail = new Json();

                    jsonThumbnail.put("url", thumbnail.getUrl());
                    jsonEmbed.put("thumbnail", jsonThumbnail);
                }

                if (author != null) {
                    Json jsonAuthor = new Json();

                    jsonAuthor.put("name", author.getName());
                    jsonAuthor.put("url", author.getUrl());
                    jsonAuthor.put("icon_url", author.getIconUrl());
                    jsonEmbed.put("author", jsonAuthor);
                }

                List<Json> jsonFields = new ArrayList<>();
                for (DiscEmbed.Field field : fields) {
                    Json jsonField = new Json();

                    jsonField.put("name", field.getName());
                    jsonField.put("value", field.getValue());
                    jsonField.put("inline", field.isInline());

                    jsonFields.add(jsonField);
                }

                jsonEmbed.put("fields", jsonFields.toArray());
                embedObjects.add(jsonEmbed);
            }

            json.put("embeds", embedObjects.toArray());
        }
        
        URL url = new URL(this.url);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        OutputStream stream = connection.getOutputStream();
        stream.write(json.toString().getBytes());
        stream.flush();
        stream.close();

        connection.getInputStream().close(); //I'm not sure why but it doesn't work without getting the InputStream
        connection.disconnect();
    }
}