import java.awt.datatransfer.StringSelection
import java.awt.Toolkit

#TIP: http://www.javapractices.com/topic/TopicAction.do?Id=82
class Clipboard
  def self.set(text)
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(StringSelection.new(text), nil)
    text
  end
end