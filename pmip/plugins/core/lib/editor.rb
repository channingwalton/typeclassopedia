import com.intellij.openapi.editor.LogicalPosition
import com.intellij.openapi.editor.ScrollType

class Editor
  def initialize(editor)
    @editor = editor
    @selection_model = editor.selection_model
    @caret_model = editor.caret_model
    @scrolling_model = editor.getScrollingModel
  end

  def word
    return '' if !has_editor?
    @selection_model.select_word_at_caret(false)
    word = @selection_model.selected_text
    @selection_model.remove_selection
    word
  end

  def line
    return '' if !has_editor?
    start = selection_range.first
    finish = selection_range.last
    #TIP: do not inline start and finish, the next line changes the state
    @selection_model.select_line_at_caret
    line = @selection_model.selected_text
    @selection_model.remove_selection
    @selection_model.set_selection(start, finish)
    #TODO: find a way to set the caret position
    line
  end

  def line_number
    return -1 if !has_editor?
    @caret_model.getLogicalPosition().line
  end

  def selection_range
    (@selection_model.selection_start..@selection_model.selection_end)
  end

  def selection
    return '' if !has_editor?
    selection = @selection_model.selected_text
    selection.nil? ? '' : selection
  end

  def highlight_word
    @selection_model.select_word_at_caret(true)
  end

  def highlight_line
    @selection_model.select_line_at_caret
  end

  def move_to(line, column)
    #TODO: restore notNull
    #position = LogicalPosition.new(notNull(line, "line"), notNull(column, "column"))
    position = LogicalPosition.new(line, column)
    @caret_model.moveToLogicalPosition(position)
    @scrolling_model.disableAnimation
    @scrolling_model.scrollTo(position, ScrollType::CENTER)
    @scrolling_model.enableAnimation
    self
  end

  private

  def has_editor?
    !@editor.nil?
  end
end