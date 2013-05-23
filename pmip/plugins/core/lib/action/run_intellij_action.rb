class RunIntellijAction < PMIPAction
  def initialize(intellij_action, name="#{self.class.to_s}: #{intellij_action}")
    super(name)
    @intellij_action = intellij_action
  end

  def run(event, context)
    action_manager = ActionManager.instance
    results = action_manager.action_ids.select{|a| a == @intellij_action }

    if results.size == 1
      result('Running ...')
      Run.read_action { action_manager.get_action(results.first).action_performed(event) }
    elsif results.empty?
      puts action_manager.action_ids.sort
      fail("could not find the intellij action: #{@intellij_action}")
    else
      fail("expected to find one intellij action for: #{@intellij_action}, but found: #{results}.")
    end
  end

  private

  def fail(message)
    Balloon.new.error(message)
    result(message)
  end
end