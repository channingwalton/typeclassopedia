import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.openapi.ui.MessageType

class Balloon
  def initialize(context = PMIPContext.new)
    @context = context
  end

  def info(message)
    balloon(MessageType::INFO, message)
  end

  def warning(message)
    balloon(MessageType::WARNING, message)
  end

  def error(message)
    balloon(MessageType::ERROR, message)
  end

  private

  def balloon(type, message)
    capitalised_message = message[0...1].upcase + message[1..message.length]
    ToolWindowManager.getInstance(@context.project).notifyByBalloon('PMIP', type, capitalised_message, nil, nil)
  end
end