package smith.lib.net.reporter;

import android.graphics.Color;
import java.util.ArrayList;
import java.util.List;

public class DiscEmbed {

    private static String title, description, url;
    
    private static Color color;

    private static Footer footer;
    //private static ThumbnailEmbed thumbnail;
    //private static ImageEmbed image;
    //private static AuthorEmbed author;
    
    //private static List<Field> fields = new ArrayList<>();


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getURL() {
        return url;
    }

    public Footer getFooter() {
        return footer;
    }

    /*public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public Image getImage() {
        return image;
    }

    public Author getAuthor() {
        return author;
    }

    public List<Field> getFields() {
        return fields;
    }*/


    public static class Builder {
        
        public Builder setTitle(String ttl) {
            title = ttl;
            return this;
        }

        public Builder setDescription(String dsc) {
            description = dsc;
            return this;
        }

        public Builder setUrl(String ulr) {
            url = ulr;
            return this;
        }
        
        public Builder setFooter(String text, String icon) {
            footer = new Footer(text, icon);
            return this;
        }

        /*public Builder setThumbnail(String url) {
            thumbnail = new Thumbnail(url);
            return this;
        }

        public Builder setImage(String url) {
            image = new Image(url);
            return this;
        }

        public Builder setAuthor(String name, String url, String icon) {
            author = new Author(name, url, icon);
            return this;
        }

        public Builder addField(String name, String value, boolean inline) {
            fields.add(new Field(name, value, inline));
            return this;
        }*/
    }

    private static class Footer {
        private String text;
        private String iconUrl;

        private Footer(String text, String iconUrl) {
            this.text = text;
            this.iconUrl = iconUrl;
        }

        private String getText() {
            return text;
        }

        private String getIconUrl() {
            return iconUrl;
        }
    }
}
