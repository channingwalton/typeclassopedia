class Filepath
  def initialize(filepath)
    @filepath = filepath.gsub('\\', '/').gsub('//', '/')
  end

  def readlines
    exist? ? IO.readlines(@filepath) : []
  end

  def read
    readlines.join
  end

  def each_line(&blk)
    File.new(@filepath).each_line{|l| blk.call(l) }
  end
  
  def writelines(lines)
    File.open(@filepath, 'w') {|f| f.print lines.collect{|l| l.chomp }.join("\n") }
    self
  end

  def write(content)
    writelines(content.split("\n"))
    self
  end

  def delete
    File.delete(@filepath) if exist?
  end

  def move_to(destination_filepath)
    FileUtils.mv(@filepath, destination_filepath.to_s)
    destination_filepath
  end

  def copy_to(destination_filepath)
    FileUtils.cp(@filepath, destination_filepath.to_s)
    destination_filepath
  end

  def ends?(value)
    match(/.*\/(.*?)#{value}$/)
  end

  def exist?
    File.exist?(@filepath)
  end

  def filename
    @filepath.split('/').last
  end

  def path
    Path.new(@filepath.sub(filename, ''))
  end

  #TODO: not a good name, and probably shouldn't be on filepath - get rid of me
  def reduce(text)
    #TODO: blow up if text not in filepath
    index = @filepath.index(text)
    @filepath[index..-1].to_s
  end

  def extension
    filename.include?('.') ? filename.split('.').last : ''
  end

  def base
    index = filename.index(".#{extension}")
    filename[0..index-1]
  end

  def replace(old, new)
    Filepath.new(@filepath.sub(old, new))
  end

  def to_s
    @filepath
  end

  private

  def match(regexp)
    @filepath.match(regexp)
  end
end