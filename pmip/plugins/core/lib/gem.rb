#TIP: for remote gems through proxy try  --http-proxy http://USERNAME:PASS@HOST:PORT gem_name or -p http://proxy:port,
#http://stackoverflow.com/questions/4418/how-do-i-update-ruby-gems-from-behind-a-proxy-isa-ntlm
#TIP: on windows gems will be installed to C:\Documents and Settings\#{username}\.gem\jruby
class Gems
  def install(*gem_filenames)
    fully_qualified_gem_filenames = gem_filenames.collect{|gem_filename|
      #TODO: make path relative
      gem_file_path = "#{Dir.pwd}/gems/#{gem_filename}"
      raise "unable to install gem, because: '#{gem_file_path}' does not exist" unless File.exists?(gem_file_path)
      gem_file_path
    }
    command("install #{fully_qualified_gem_filenames.join(' ')} --user-install --no-rdoc --no-ri", true)
  end

  def uninstall(gem_name, args='-a')
    command("uninstall #{gem_name} #{args}", true)
  end

  def list(args='', print=false)
    command("list #{args}", print)
  end

  def command(args, print_result=false)
    raise "Please upgrade PMIP plugin to 0.3.0 or later" if $jruby_home.nil?

    install_dir = '' #TIP: this should work, but it seems to be ignored "--install-dir=#{gems_directory}"
    gem_command = "gem #{args} #{install_dir}"
    puts "> #{gem_command}"

    if OS.windows?
      jgem_command = "java -jar #{jruby_jar_file} --command j#{gem_command}".gsub('/', "\\")
      command = "cmd /c \"#{jgem_command}\""
      result = `#{command} 2>&1`
      puts result if print_result
      result
    else
      raise "Sorry, gems not currently supported on: #{OS.name}"
    end
  end

  private

  def jruby_jar_file
    $jruby_home.to_s.split('!')[0].sub('jar:file:/', '')
  end

  def gems_directory
    jruby_jar_file.split('lib/jruby')[0] + 'gems'
  end
end

def gems
  Gems.new
end