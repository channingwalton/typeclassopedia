require 'webrick'

$mounts = {}
$servers = {} if $servers.nil?

#TIP: borrowed from http://tobyho.com/HTTP%20Server%20in%205%20Lines%20With%20Webrick
class NonCachingFileHandler < WEBrick::HTTPServlet::FileHandler
  def prevent_caching(res)
    res['ETag']          = nil
    res['Last-Modified'] = Time.now + 100**4
    res['Cache-Control'] = 'no-store, no-cache, must-revalidate, post-check=0, pre-check=0'
    res['Pragma']        = 'no-cache'
    res['Expires']       = Time.now - 100**4
  end

  #noinspection RubyCodeStyle
  def do_GET(req, res)
    super
    prevent_caching(res)
  end
end

class Mounter
  def self.mount(url, servlet, *args)
    puts "- Mounted #{url} -> #{servlet} #{render_usages(mangle_name(servlet.to_s))}"
    $mounts[url] = [servlet, *args]
    self
  end

  private

  #TODO: remove duplication with binder
  def self.render_usages(id)
    count = usages(id)
    count == 0 ? '' : "(#{count})"
  end

  #TODO: remove duplication with binder
  def self.mangle_name(name)
    name.gsub(/::/, '/').gsub(/([A-Z]+)([A-Z][a-z])/,'\1_\2').gsub(/([a-z\d])([A-Z])/,'\1 \2')
  end
end

def mount(url, servlet, *args)
  Mounter.mount(url, servlet, args)
end

#TODO: pull out server into another class?
def server(port = 9319)
  puts "- Starting server on port: #{port}"

  $servers[port] = WEBrick::HTTPServer.new(:Port => port) if $servers[port].nil?
  server = $servers[port]
  $mounts.keys.each{|url|
    server.unmount(url)
    server.mount(url, $mounts[url][0], *$mounts[url][1])
  }
  $mounts.clear

  Thread.new { server.start unless server.status == :Running }
end