package smith.lib.net.reporter;

import java.util.ArrayList;
import java.util.List;

public class DiscEmbed {

    private static String title, description, url;
    
    private static Footer footer;
    private static Thumbnail thumbnail;
    private static Image image;
    private static Author author;
    
    private static List<Field> fields = new ArrayList<>();


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getURL() {
        return url;
    }

    public Footer getFooterEmbed() {
        return footer;
    }

    public Thumbnail getThumbnailEmbed() {
        return thumbnail;
    }

    public Image getImageEmbed() {
        return image;
    }

    public Author getAuthorEmbed() {
        return author;
    }

    public List<Field> getFields() {
        return fields;
    }


    public static class Builder {
        String ttl, dsc, ulr;
        Footer ftr;
        Thumbnail thmb;
        Image img;
        Author auth;
        List<Field> flds = new ArrayList<>();
        
        public Builder setTitle(String ttl) {
            this.ttl = ttl;
            return this;
        }

        public Builder setDescription(String dsc) {
            this.dsc = dsc;
            return this;
        }

        public Builder setURL(String ulr) {
            this.ulr = ulr;
            return this;
        }
        
        public Builder setFooterEmbed(String text, String icon) {
            this.ftr = new Footer(text, icon);
            return this;
        }

        public Builder setThumbnailEmbed(String url) {
            this.thmb = new Thumbnail(url);
            return this;
        }

        public Builder setImageEmbed(String url) {
            this.img = new Image(url);
            return this;
        }

        public Builder setAuthorEmbed(String name, String url, String icon) {
            this.auth = new Author(name, url, icon);
            return this;
        }

        public Builder addFieldEmbed(String name, String value) {
            this.flds.add(new Field(name, value, true));
            return this;
        }
        
        public DiscEmbed build() {
            DiscEmbed embed = new DiscEmbed();
            title = ttl;
            description = dsc;
            url = ulr;
            footer = ftr;
            thumbnail = thmb;
            image = img;
            author = auth;
            fields = flds;
            return embed;
        }
    }

    public static class Footer {
        private final String text;
        private final String iconUrl;

        private Footer(String text, String iconUrl) {
            this.text = text;
            this.iconUrl = iconUrl;
        }

        public String getText() {
            return text;
        }

        public String getIconURL() {
            return iconUrl;
        }
    }

    public static class Thumbnail {
        private final String url;

        private Thumbnail(String url) {
            this.url = url;
        }

        public String getURL() {
            return url;
        }
    }

    public static class Image {
        private final String url;

        private Image(String url) {
            this.url = url;
        }

        public String getURL() {
            return url;
        }
    }

    public static class Author {
        private final String name;
        private final String url;
        private final String iconUrl;

        private Author(String name, String url, String iconUrl) {
            this.name = name;
            this.url = url;
            this.iconUrl = iconUrl;
        }

        public String getName() {
            return name;
        }

        public String getURL() {
            return url;
        }

        public String getIconURL() {
            return iconUrl;
        }
    }

    public static class Field {
        private final String name;
        private final String value;
        private final boolean inline;

        private Field(String name, String value, boolean inline) {
            this.name = name;
            this.value = value;
            this.inline = inline;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public boolean isInline() {
            return inline;
        }
    }
}
