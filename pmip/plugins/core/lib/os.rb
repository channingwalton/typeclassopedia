import java.lang.System

class OS
  def self.osx?
    name =~ /^Mac OS X$/
  end

  def self.windows?
    name =~ /^Windows/
  end

  def self.linux?
    name =~ /^Linux$/
  end

  def self.name
    System.getProperty("os.name")
  end
end