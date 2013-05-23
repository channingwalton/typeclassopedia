#Borrowed from: http://www.centerkey.com/java/browser/

import java.lang.reflect.Method
import java.lang.System

class Browser
  BROWSERS = ["google-chrome","firefox", "opera", "konqueror", "epiphany", "seamonkey", "galeon", "kazehakase", "mozilla", "netscape"]

  def open(url)
    if OS.osx?
      raise "Not currently supported on: #{OS.name}"
      #file_mgr = Class.forName("com.apple.eio.FileManager")
      #open_url = file_mgr.getDeclaredMethod("openURL", [java.lang.String.java_class].to_java(java.lang.Class))
      #open_url.invoke(nil, [url].to_java(java.lang.String))
    elsif OS.windows?
      `rundll32 url.dll,FileProtocolHandler #{url}`
    else
      BROWSERS.each{|browser| return Thread.start{`#{browser} #{url}`} unless `which #{browser}`.strip.empty? }
    end
  end
end