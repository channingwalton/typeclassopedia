import com.intellij.openapi.ui.Messages

class Dialogs
   def initialize(context = PMIPContext.new)
    @project = context.project
   end

   def error(title, message)
     Messages.showErrorDialog(@project, message, title)
   end

   def info(title, message)
     Messages.showInfoMessage(@project, message, title)
   end

   def yes_no(title, message)
     Messages.showYesNoDialog(@project, message, title, Messages.getQuestionIcon) == 0
   end
end