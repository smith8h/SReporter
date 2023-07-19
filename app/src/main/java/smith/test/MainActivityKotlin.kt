package smith.test

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import smith.lib.net.reporter.*

class MainActivityKotlin: AppCompatActivity(), ReporterCallBack {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun check(v: View) {
        val et = findViewById<EditText>(R.id.et)

        val webhookURL = "https://discord.com/api.........."
        val content = et.text.toString()
        val username = "SReporter"
        val avatarURL = "https://te.legra.ph/file/e86668a3699571a74c411.png"
        val tts = true

        val dr = DiscReporter(this)
        dr.setWeebHook(webhookURL)
        dr.setUsername(username)
        dr.setAvatarUrl(avatarURL)
        dr.setContent(content)
        dr.setReportCallBack(this)
        dr.setTts(tts)
        dr.addEmbed(DiscEmbed.Builder()
            .setDescription("Description")
            .setTitle("Title")
            .setURL("https://t.me/smithdev")
            .setAuthorEmbed("Author smith8h","https://t.me/smithdev","https://te.legra.ph/file/e86668a3699571a74c411.png")
            .setFooterEmbed("footer embed","https://te.legra.ph/file/e86668a3699571a74c411.png")
            .setImageEmbed("https://te.legra.ph/file/e86668a3699571a74c411.png")
            .setThumbnailEmbed("https://te.legra.ph/file/e86668a3699571a74c411.png")
            .addFieldEmbed("Field inline", "field value")
            .addFieldEmbed("field inline", "field value")
            .build())
        dr.addEmbed(DiscEmbed.Builder()
            .setDescription("Description")
            .setTitle("Title")
            .setURL("https://t.me/smithdev")
            .setAuthorEmbed("Author smith8h","https://t.me/smithdev","https://te.legra.ph/file/e86668a3699571a74c411.png")
            .setFooterEmbed("footer embed","https://te.legra.ph/file/e86668a3699571a74c411.png")
            .setImageEmbed("https://te.legra.ph/file/e86668a3699571a74c411.png")
            .setThumbnailEmbed("https://te.legra.ph/file/e86668a3699571a74c411.png")
            .addFieldEmbed("Field inline", "field value")
            .addFieldEmbed("field inline", "field value")
            .build())
        dr.sendReport()




        val token = "19521......-6JQ"
        val id = "@smith_com"
        val header = "" // keep it empty to use default header from library
        val subHeader = "This is sub header"
        val body = "Testing TeleReporter lib new update...\nThis is report body message\n" + et.text.toString()
        val footer = "This is custom footer" // or keep it empty to use default library footer

        val tr = TeleReporter(this)
        tr.setBotToken(token)
        tr.setTargetChatId(id)
        tr.setTargetChatTopic(123456789) // optional
        tr.setReportHeader(header)
        tr.setReportSubHeader(subHeader) // optional
        tr.setReportMessage(body)
        tr.setReportFooter(TeleReporter.USER_INFO, "")  // keep it empty to use default footer from library
        tr.setReportFooter(TeleReporter.CUSTOM, footer)  // custom footer from your app
        tr.setReportCallBack(this)
        tr.sendReport()
    }


    override fun onSuccess() {
        Toast.makeText(this, "REPORT SUCCEED!", Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(failureMessage: String) {
        Toast.makeText(this, failureMessage, Toast.LENGTH_SHORT).show()
    }
}