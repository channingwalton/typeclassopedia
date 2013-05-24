import com.intellij.execution.RunManager
import com.intellij.openapi.actionSystem.AnActionEvent

class RunConfiguration
  def initialize(context = PMIPContext.new)
    @context = context
  end

  def choose(name)
    runner_manager = RunManager.get_instance(@context.project)
    run_configurations = runner_manager.all_configurations().select{|c| name == c.name }

    if run_configurations.size != 1
      raise "Configuration error, expected to find 1 run configuration called: " + name + ", but found #{run_configurations.size}"
    end

    runner_manager.set_selected_configuration(runner_manager.get_settings(run_configurations.first))
    self
  end

  def run(action, presentation)
    Action.from_id(action).run(presentation)
  end
end