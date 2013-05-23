class UnicodeUpMyScala < PMIPAction
  REPLACEMENTS = {'=>' => '⇒', '<-' => '←', '=>' => '⇒', '|@|' => '⊛', '->' => '→', '=/=' => '≠', '/==' => '≠'}

  def run(event, context)
    Refresh.file_system_before_and_after {
      changes = find_and_unicode_up
      result_and_balloon("#{changes} Scala files were unicoded up")
    }
  end

  private

  def find_and_unicode_up
    changes = 0
    candidates = scala_files

    candidates.each {|f|
      original = f.readlines.join("\r\n")
      updated = mangle(original)

      if original != updated
        changes = changes + 1
        f.writelines(updated.split("\r\n"))
      end
    }

    changes
  end

  def scala_files
    Files.new.include('**/*.scala').find
  end

  def mangle(original)
    REPLACEMENTS.each_key {|k| original = original.gsub(' ' + k, ' ' + REPLACEMENTS[k]) }
    original
  end
end