import com.intellij.openapi.actionSystem.AnAction

class PMIPBaseAction < AnAction
  def initialize(name, description, icon = nil)
    @name = name
    super(name(), (description.nil? ? name() : description), icon)
  end

  def name
    "" == @name ? mangle_name(self.class.to_s) : @name
  end

  def mangle_name(name)
    name.gsub(/::/, '/').gsub(/([A-Z]+)([A-Z][a-z])/,'\1_\2').gsub(/([a-z\d])([A-Z])/,'\1 \2')
  end
end

class PMIPAction < PMIPBaseAction
  attr_reader :result

  def initialize(name = "", description = nil)
    super(name, description)
    @plugin_root = PMIPContext.new.plugin_root
  end

  #TIP: this cannot be renamed ...
  def actionPerformed(event)
    context = PMIPContext.new
    reset_result
    StatusBar.new.set("Running #{name} ...")
    track(name)
    begin
      run(event, context)
      message = "#{name}: #{@result}"
      puts "- #{message}"
      StatusBar.new.set(message)
    rescue => e
      message = "Error: #{e.message}:\n#{e.backtrace.join("\n")}"
      puts message
      Dialogs.new(context).error("PMIP Plugin Error", "PMIP encounted an error while executing the action: " + name + "\n\n" + message + "\n\nPlease contact the plugin developer!")
      StatusBar.new.set(message)
    end
  end

  protected

  def result(result)
    @result = result.is_a?(Array) ? result.join(', ') : result.to_s
    @result
  end

  def result_and_balloon(result)
    result(result)
    Balloon.new.info(result)
  end

  def plugin_root
    @plugin_root
  end

  private

  def reset_result
    result('Done')
  end
end

import com.intellij.codeInsight.intention.IntentionAction

class PMIPIntentionAction < PMIPAction
  include IntentionAction

  def invoke(project, editor, psi_file)
    actionPerformed(nil)
  end

  #TIP: this cannot be renamed ...
  def isAvailable(project, editor, psi_file)
    available?(PMIPContext.new)
  end

  #TIP: this cannot be renamed ...
  def startInWriteAction
    true
  end

  #TIP: this cannot be renamed ...
  def getFamilyName
    name
  end

  #TIP: this cannot be renamed ...
  def getText
    describe
  end
end