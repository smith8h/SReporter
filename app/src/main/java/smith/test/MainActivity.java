package smith.test;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import smith.lib.net.reporter.DiscEmbed;
import smith.lib.net.reporter.DiscReporter;
import smith.lib.net.reporter.ReporterCallBack;
import smith.lib.net.reporter.TeleReporter;

public class MainActivity extends AppCompatActivity {
    
    ReporterCallBack callback = new ReporterCallBack() {
        @Override public void onSuccess() {
            Toast.makeText(MainActivity.this, "REPORT SUCCEED!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(String failureMessage) {
            Toast.makeText(MainActivity.this, failureMessage, Toast.LENGTH_SHORT).show();
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }
    
    public void check(View v) {
        EditText et = findViewById(R.id.et);
        
        String webhookURL = "https://discord.com/api..........";
        String content = "Some Content to fill the report";
        String username = "SReporter";
        String avatarURL = "https://te.legra.ph/file/e86668a3699571a74c411.png";
        boolean tts = true;
        
        DiscReporter dr = new DiscReporter(this);
        dr.setWeebHook(webhookURL);
        dr.setUsername(username);
        dr.setAvatarUrl(avatarURL);
        dr.setContent(content);
        dr.setReportCallBack(callback);
        dr.setTts(tts);
        dr.addEmbed(new DiscEmbed.Builder()
            .setDescription("Description")
            .setTitle("Title")
            .setURL("https://t.me/smithdev")
            .setAuthorEmbed("Author smith8h","https://t.me/smithdev","https://te.legra.ph/file/e86668a3699571a74c411.png")
            .setFooterEmbed("footer embed","https://te.legra.ph/file/e86668a3699571a74c411.png")
            .setImageEmbed("https://te.legra.ph/file/e86668a3699571a74c411.png")
            .setThumbnailEmbed("https://te.legra.ph/file/e86668a3699571a74c411.png")
            .addFieldEmbed("Field inline", "field value")
            .addFieldEmbed("field inline", "field value")
            .build());
        dr.addEmbed(new DiscEmbed.Builder()
                .setDescription("Description")
                .setTitle("Title")
                .setURL("https://t.me/smithdev")
                .setAuthorEmbed("Author smith8h","https://t.me/smithdev","https://te.legra.ph/file/e86668a3699571a74c411.png")
                .setFooterEmbed("footer embed","https://te.legra.ph/file/e86668a3699571a74c411.png")
                .setImageEmbed("https://te.legra.ph/file/e86668a3699571a74c411.png")
                .setThumbnailEmbed("https://te.legra.ph/file/e86668a3699571a74c411.png")
                .addFieldEmbed("Field inline", "field value")
                .addFieldEmbed("field inline", "field value")
                .build());
        dr.sendReport();
        
        
        

        String token = "19521......-6JQ";
        String id = "@smith_com";
        String header = ""; // keep it empty to use default header from library
        String subHeader = "This is sub header";
        String body = "Testing TeleReporter lib new update...\nThis is report body message\n"
                + et.getText().toString();
        String footer = "This is custom footer"; // or keep it empty to use default library footer
        
        TeleReporter tr = new TeleReporter(this);
        tr.setBotToken(token);
        tr.setTargetChatId(id);
        tr.setTargetChatTopic(123456789);
        tr.setReportHeader(header);
        tr.setReportSubHeader(subHeader);
        tr.setReportMessage(body);
        tr.setReportFooter(TeleReporter.USER_INFO, "");  // keep it empty to use default footer from library
        tr.setReportFooter(TeleReporter.CUSTOM, footer);  // custom footer from your app
        tr.setReportCallBack(callback);
        tr.sendReport();
    }
}
