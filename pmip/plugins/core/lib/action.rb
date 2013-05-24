class Action
  def self.from_id(id)
    Action.new(ActionManager.instance.getAction(id))
  end

  def run(presentation)
    @action.action_performed(AnActionEvent.new(nil, DataManager.instance.data_context, "", presentation, ActionManager.instance, 0))
  end

  private

  def initialize(action)
    @action = action
  end
end