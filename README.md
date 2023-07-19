# SReporter

[![Build Status](https://travis-ci.org/niltonvasques/simplecov-shields-badge.svg?branch=master)](https://travis-ci.org/niltonvasques/simplecov-shields-badge)
![stability-stable](https://img.shields.io/badge/stability-stable-green.svg)
![minimumSDK](https://img.shields.io/badge/minSDK-21-f39f37)
![stable version](https://img.shields.io/badge/stable_version-v1.0-blue)
![Repository size](https://img.shields.io/github/repo-size/smith8h/SReporter)

<br/>

# Content
- [How to setup](#setup)
- [How to implement (Documentations)](#documentations)
  - [The use of TeleReporter](#telereporter)
  - [The use of DiscReporter](#discreporter)
    - [DiscReporter Embeds](#discreporter-embeds)
  - [The implementation of CallBack Interface](#callback)
- [Support library improvements (Donations)](#donations)

<br/>

# Setup
> **Step 1.** Add the JitPack repository to your build file.</br>
Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
    repositories {
	...
	maven { url 'https://jitpack.io' }
    }
}
```
> **Step 2.** Add the dependency:
```gradle
dependencies {
    implementation 'com.github.smith8h:SReporter:v1.0'
}
```

<br/>

# Documentations
Send reports, notifications, app crash logs and etc.. simply with SReporter library.
You can choose how to send reports to your Telegram group by **Telegram APIs** or to your Discord server by **Discord Webhooks**!
Well with SReporter you can send them properly.
Follow the instructions below on how to implement `TeleReporter` and `DiscReporter` classes:

## TeleReporter
The TeleReport composed of Header, SubHeader ( __optional__ ), Message or Body and Footer. As in the image bellow:
<p align="center">
    <img src="https://te.legra.ph/file/56186f1c936c7e2b813c5.jpg" style="width: 80%;" />
</p>
<br/>

- Create new object of TeleReporter class:
  ```java
      TeleReporter tr = new TeleReporter(this);
  ```
- Now set your Telegram bot token:
  ```java
      tr.setBotToken("19521... ...-6JQ");
  ```
  > 📃 Go to [Telegram BotFather](https://botfather.t.me) and create or surf your bots and get its token
- Now set your target chat id (or username):
  ```java
      tr.setTargetChatId("@smith_com");
      // or chat id (1323671558)
  ```
- Set Header for the report:
  ```java
      tr.setReportHeader("Report Title (Report Header)...");
      // You can leave it blank for default title
  ```
- Set SubHeader for the report:
  ```java
      tr.setReportSubHeader("SubTitle (SubHeader)...");
      // You can leave it blank if you don't want it to be in the report
  ```
- Set the content of the report:
  ```java
      tr.setReportMessage("Report Content...");
  ```
- Set the footer of the report:
  ```java
      tr.setReportFooter(TeleReporter.USER_INFO, "");
      // Default footer is the info of user's device, build, app used, app version...
      
      tr.setReportFooter(TeleReporter.CUSTOM, "Custom Footer...");
      // Custom footer info (additional note) if you don't need to attach device & app info of the user
  ```
- Now set the callback interface to get notified of errors or successes of sending reports:
  ```java
      tr.setReportCallBack(callback);
  ```
  > Head straight to [CallBack Implementation](#callback) to see how to implement the callback for TeleReporter.
- Now send the report to your chat/group
  ```java
      tr.sendReport();
  ```
  
## DiscReporter
The DiscReport composed of main report content ( consider it like a header or the main message if you don't need to use the embeds ),
Embeds ( __optional__, ImageEmbed/AuthorEmbed/ThumbnailEmbed/FooterEmbed/Fields ).
As in the image bellow:
<p align="center">
    <img src="https://te.legra.ph/file/458fafdd09484074f67c5.jpg" style="width: 80%;" />
</p>
<br/>

- Create new object of TeleReporter class:
  ```java
      DiscReporter dr = new DiscReporter(this);
  ```
- Now set your Discord server's webhook url:
  ```java
      dr.setWeebHook("https://discord.com/api...");
  ```
  > 📃 See [instructions here](https://support.discord.com/hc/en-us/articles/228383668) to create new webhook for your Discord server.
- Now set a username for webhook:
  ```java
      dr.setUsername("SReporter");
      // optional, put your desired one instead
  ```
- Set an icon for webhook:
  ```java
      dr.setAvatarUrl("https://te.legra.ph/file/e86668a3699571a74c411.png");
      // default is the lib icon
  ```
- Now set your content of the report, either as title if you want to use embeds or as main content if you won't:
  ```java
      dr.setContent("Content text...");
  ```
- If you want to set tts to the report:
  ```java
      dr.setTts(true);
      // default is false
  ```
- Now if you want to add embeds:
  ```java
      dr.addEmbed(embed)
      // you can add as many as you need
  ```
  > Head straight to [DiscReporter Embeds](#discreporter-embeds) to see how to implement embeds for DiscReporter.
- Now set the callback interface to get notified of errors or successes of sending reports:
  ```java
      dr.setReportCallBack(callback);
  ```
  > Head straight to [CallBack Implementation](#callback) to see how to implement the callback for DiscReporter.
- Now send the report to your server:
  ```java
      dr.sendReport();
  ```
  
<br/>

## DiscReporter Embeds
> You can dismiss using the embeds and just rely on title, description and url
> Also you can customize it to your prefers! considering title, author, thumbnail and without the inline fields and footer... etc.

```java
    DiscEmbed embed = new DiscEmbed.Builder()
            .setTitle("Title")
            .setDescription("Description")
            .setURL("https://t.me/smithdev")
            
            // author name, author url, author icon
            .setAuthorEmbed("Name","https://t.me/smithdev","https://te.legra.ph/file/e86668a3699571a74c411.png")
            
            // footer text, footer icon
            .setFooterEmbed("thank in advance","https://te.legra.ph/file/e86668a3699571a74c411.png")
            
            // image url
            .setImageEmbed("https://te.legra.ph/file/e86668a3699571a74c411.png")
            
            // thumbnail url
            .setThumbnailEmbed("https://te.legra.ph/file/e86668a3699571a74c411.png")
            
            // inline fields (Copy on click)
            .addFieldEmbed("Field inline", "field value")
            
            .build());
```

<br/>

## CallBack
```java
    ReporterCallBack callback = new ReporterCallBack() {
        @Override
        public void onSuccess() {
            Toast.makeText(context, "REPORT SENT SUCCEESSFULLY!", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onFailure(String failureMessage) {
            Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show();
        }
    };
```

<br/>

# Donations
> If you would like to support this project's further development, the creator of this projects or the continuous maintenance of the project **feel free to donate**.
Your donation is highly appreciated. Thank you!
<br/>

You can **choose what you want to donate**, all donations are awesome!</br>
<br/>

[![Buy me a coffee](https://img.shields.io/badge/Buy_Me_A_Coffee-FFDD00?style=for-the-badge&logo=buy-me-a-coffee&logoColor=black)](https://www.buymeacoffee.com/HusseinShakir)

<br/>

<p align="center">
  <img src="https://raw.githubusercontent.com/smith8h/smith8h/main/20221103_150053.png" style="width: 38%;"/>
  <br><b>With :heart:</b>
</p>
