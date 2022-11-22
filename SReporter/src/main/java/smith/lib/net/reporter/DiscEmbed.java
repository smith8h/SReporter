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

        public Builder setUrl(String ulr) {
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

        public Builder addField(String name, String value, boolean inline) {
            this.flds.add(new Field(name, value, inline));
            return this;
        }
        
        public DiscEmbed build() {
            DiscEmbed embed = new DiscEmbed();
            embed.title = ttl;
            embed.description = dsc;
            embed.url = ulr;
            embed.footer = ftr;
            embed.thumbnail = thmb;
            embed.image = img;
            embed.author = auth;
            embed.fields = flds;
            return embed;
        }
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

        private String getIconURL() {
            return iconUrl;
        }
    }

    private static class Thumbnail {
        private String url;

        private Thumbnail(String url) {
            this.url = url;
        }

        private String getURL() {
            return url;
        }
    }

    private static class Image {
        private String url;

        private Image(String url) {
            this.url = url;
        }

        private String getUrl() {
            return url;
        }
    }

    private static class Author {
        private String name;
        private String url;
        private String iconUrl;

        private Author(String name, String url, String iconUrl) {
            this.name = name;
            this.url = url;
            this.iconUrl = iconUrl;
        }

        private String getName() {
            return name;
        }

        private String getUrl() {
            return url;
        }

        private String getIconUrl() {
            return iconUrl;
        }
    }

    private static class Field {
        private String name;
        private String value;
        private boolean inline;

        private Field(String name, String value, boolean inline) {
            this.name = name;
            this.value = value;
            this.inline = inline;
        }

        private String getName() {
            return name;
        }

        private String getValue() {
            return value;
        }

        private boolean isInline() {
            return inline;
        }
    }
}
