class Path
  def initialize(path)
    @path = path.gsub('\\', '/')
    @path << '/' unless path =~ /\/$/
  end

  def files(glob)
    Dir.glob(@path + glob).collect{|f| Filepath.new(f) }
  end

  def create_filepath(filename)
    Filepath.new(@path + '/' + filename)
  end

  def to_s
    @path
  end
end