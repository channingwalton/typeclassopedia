import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.vfs.VirtualFileManager

class Refresh
   def self.file_system
     FileDocumentManager.instance.save_all_documents
     VirtualFileManager.instance.refresh(true)
   end

   def self.file_system_before(&block)
     file_system
     block.call
   end

   def self.file_system_after(&block)
     block.call
     file_system
   end

   def self.file_system_before_and_after(&block)
     file_system
     block.call
     file_system
   end
end