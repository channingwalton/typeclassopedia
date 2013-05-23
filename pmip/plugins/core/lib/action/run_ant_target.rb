class RunAntTarget < PMIPAction
  def initialize(task, name="#{self.class.to_s}: #{task}")
    super(name)
    @task = task
  end

  def run(event, context)
    action_manager = ActionManager.instance
    results = action_manager.action_ids.reject{|a| a =~ /^#{self.class.to_s}/ }.select{|a| a =~ /#{@task}$/ }

    if results.size == 1
      result('Running ...')
      Run.read_action { action_manager.get_action(results.first).action_performed(event) }
    elsif results.empty?
      fail("could not find the ant task: #{@task} - please ensure that the buildfile is loaded in the Ant Build tab and that the task is included in the filter.")
    else
      fail("expected to find one ant task for: #{@task}, but found: #{results}.")
    end
  end

  private

  def fail(message)
    Balloon.new.error(message)
    result(message)
  end
end
