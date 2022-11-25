package smith.test;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.itsaky.androidide.logsender.LogSender;
import smith.lib.net.reporter.DiscEmbed;
import smith.lib.net.reporter.DiscReporter;
import smith.lib.net.reporter.ReporterCallBack;
import smith.lib.net.reporter.TeleReporter;

public class MainActivity extends AppCompatActivity {
    
    ReporterCallBack callback = new ReporterCallBack() {
        @Override public void onSuccess() {
            Toast.makeText(MainActivity.this, "REPORT SUCCEED!", Toast.LENGTH_SHORT).show();
        }
        @Override public void onFail(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogSender.startLogging(this);
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
            .addFieldEmbed("Field inline", "field value", true)
            .addFieldEmbed("field not inline", "field value", false)
            .build());
        dr.sendReport();
        
        
        
        
        /*
        String token = "19521......-6JQ";
        String id = "@smith_com";
        String header = "";
        String subHeader = "This is sub header";
        String body = "Testing TeleReporter lib new update...\nThis is report body message\n"+et.getText().toString();
        String footer = "This is custom footer";
        
        TeleReporter tr = new TeleReporter(this);
        tr.setBotToken(token);
        tr.setTargetChatId(id);
        tr.setReportHeader(header);
        tr.setReportSubHeader(subHeader);
        tr.setReportMessage(body);
        tr.setReportFooter(TeleReporter.USER_INFO, "");
        tr.setReportCallBack(callback);
        tr.sendReport();
        */
    }
}
