class ExecuteConfiguration < PMIPAction
  def initialize(configuration, action = 'Run', name = "#{action}Configuration: #{configuration}")
    super(name)
    @configuration = configuration
    @action = action
  end

  def run(event, context)
    result('Running ...')
    RunConfiguration.new.choose(@configuration).run(@action, event.presentation)
  end
end