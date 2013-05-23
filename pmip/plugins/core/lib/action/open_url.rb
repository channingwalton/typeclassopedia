class OpenURL < PMIPAction
  def initialize(url, name="#{self.class.to_s}: #{url}")
    super(name)
    @url = url
  end

  def run(event, context)
    result('Running ...')
    Browser.new.open(@url)
  end
end