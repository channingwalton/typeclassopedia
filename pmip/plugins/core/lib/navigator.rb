class Navigator
  def initialize(context = PMIPContext.new)
    @context = context
  end

  def open(element)
    element.navigate(true)
    @context.current_editor
  end
end