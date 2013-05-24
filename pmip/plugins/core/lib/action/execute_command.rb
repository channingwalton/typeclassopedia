class ExecuteCommand < PMIPAction
  def initialize(command, path='', name = "#{self.class.to_s}: #{command}")
    super(name)
    @command = command
    @path = path
  end

  def run(event, context)
    result('Running ...')
    if OS.windows?
      Run.later { `start /D#{@path.to_s.gsub('/', "\\")} #{@command}` }
    else
      Balloon.new.error("ExecuteCommand not currently supported on: #{OS.name}")
    end
  end
end