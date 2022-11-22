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
        @Override public void onSuccess(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
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
        
        String webhookURL = "";
        String content = "Some Content";
        String username = "@sreporter";
        String avatarURL = "https://te.legra.ph/file/9e44d81148b9436acb0f7.jpg";
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
            .setUrl("https://t.me/smithdev")
            .setAuthorEmbed("","","")
            .setFooterEmbed("","")
            .setImageEmbed("")
            .setThumbnailEmbed("")
            .addField("", "", false)
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
