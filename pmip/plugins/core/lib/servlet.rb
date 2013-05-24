require 'webrick'

class PMIPServlet < WEBrick::HTTPServlet::AbstractServlet
  def initialize(config, args = [])
    super
    @args = args
  end

  def self.get_instance(config, *options)
    #TIP: this is how we do a reload every-time
    load __FILE__
    self.new(config, *options)
  end

  #noinspection RubyCodeStyle
  def do_GET(request, response)
    with(request, response) {|context| get(request, response, context) }
    response.set_redirect(WEBrick::HTTPStatus::Found, @redirect) unless @redirect.nil?
  end

  #noinspection RubyCodeStyle
  def do_POST(request, response)
    with(request, response) {|context| post(request, response, context) }
    response.set_redirect(WEBrick::HTTPStatus::Found, @redirect) unless @redirect.nil?
  end

  protected

  def redirect(url)
    @redirect = url
  end

  def content_type(type)
    @content_type = type
  end

  def result(result)
    @result = result.is_a?(Array) ? result.join(', ') : result.to_s
    @result
  end

  def params
    @params
  end

  private

  def with(request, response, &blk)
    waiting = true
    redirect(nil)
    content_type('text/html')
    context = PMIPContext.new
    reset_result
    StatusBar.new.set("Running #{name} ...")
    track(mangle_name(name))
    Run.later do
      begin
        @params = Params.new(request.query)
        blk.call(context)
        response.status = 200
        response['Content-Type'] = @content_type
        message = "#{name}: #{@result}"
        puts "- #{message}"
        StatusBar.new.set(message)
      rescue => e
        message = "Error: #{e.message}:\n#{e.backtrace.join("\n")}"
        puts message
        response.body = message
        Dialogs.new(context).error("PMIP Plugin Error", "PMIP encounted an error while executing the action: " + name + "\n\n" + message + "\n\nPlease contact the plugin developer!")
        StatusBar.new.set(message)
      ensure
        waiting = false
      end
    end

    while waiting
      #TODO: should there be some kind of max timeout in here
    end
  end

  def name
    @name.nil? ? self.class.to_s : @name
  end

  def reset_result
    result('Nothing to do')
  end

  private

  #TODO: remove duplication with binder
  def mangle_name(name)
    name.gsub(/::/, '/').gsub(/([A-Z]+)([A-Z][a-z])/,'\1_\2').gsub(/([a-z\d])([A-Z])/,'\1 \2')
  end
end

#TODO: pull out class (to servlet package)
class Params
  def initialize(query)
    return if query.nil?
    @key_to_value = query
  end

  def [](key)
    @key_to_value[key]
  end

  def has_key?(key)
    @key_to_value.has_key?(key)
  end
end