import javax.swing.JProgressBar

class ProgressBar
  def initialize(context = PMIPContext.new)
    @status_bar = StatusBar.new(context)
    @label = JLabel.new
    @progress_bar = JProgressBar.new
    @progress_bar.set_string_painted(true)
   end

  def start(text='', label='', tool_tip='')
    @status_bar.add(@label).add(@progress_bar)
    @progress_bar.set_indeterminate(true)
    @progress_bar.set_string(text)
    @progress_bar.set_tool_tip_text(tool_tip)
    @label.set_text(label)
  end

  def finish
    @status_bar.clear
  end

  def update(done, of, text='', label='', tool_tip='')
    @progress_bar.set_maximum(of)
    @progress_bar.set_indeterminate(false)
    @progress_bar.set_string(text)
    @progress_bar.set_tool_tip_text(tool_tip)
    @label.set_text(label)
    @progress_bar.set_value(done)
  end
end