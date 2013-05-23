class FindResult
  attr_reader :filepath, :content, :name, :line
  
  def initialize(filepath, line, column, name, content)
    @filepath = filepath
    @line = line
    @column = column
    @name = name
    @content = content
  end

  def describe
    "#{@name} -> #{@filepath.filename} (#{@line})"
  end

  def navigate_to(context = PMIPContext.new)
    Navigator.new(context).open(Elements.new(context).find_file(@filepath).first).move_to(@line, @column)
  end

  def to_s
    "\n[#{@filepath.filename}, (#{@line}, #{@column}), \"#{@content.strip}\"]"
  end
end