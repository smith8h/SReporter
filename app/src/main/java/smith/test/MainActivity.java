package smith.test;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.itsaky.androidide.logsender.LogSender;
import smith.lib.net.reporter.TeleReporter;
import smith.lib.net.reporter.ReporterCallBack;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogSender.startLogging(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }
    
    public void check(View v) {
        EditText et = findViewById(R.id.et);
        
        String token = "1952111437:AAHruEiN5nU1J83PAvJYNtagvbvWsA--6JQ";
        String id = "@smith_com";
        String header = "" ;
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
        tr.setReportCallBack(new ReporterCallBack() {
            @Override public void onSuccess(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
            @Override public void onFail(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        tr.sendReport();
        
        tr.setReportFooter(TeleReporter.CUSTOM, footer);
        tr.sendReport();
        
        /*
            getReport()
            getReportURL()
        */
    }
}