import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.KeyboardShortcut
import com.intellij.openapi.keymap.KeymapManager
import javax.swing.KeyStroke

PMIP_MENU = "PMIP::PopupMenu"

class Binder
  def self.bind(key, action, force)
    key.sub!('banana', 'ctrl alt shift')
    key.sub!('pear', 'ctrl shift')
    id = action.name

    unregister(key, id, force)
    register(key, action, id)
    add_menu_item(action)

    key_binding = key == '' ? ' ' : " -> #{key} "
    puts "- Bound #{id}#{key_binding}#{render_usages(id)}"
  end

  private

  def self.shortcut(key)
    KeyboardShortcut.new(key_stroke(key), nil)
  end

  def self.key_stroke(key)
    KeyStroke.get_key_stroke(key)
  end

  def self.add_menu_item(action)
    action_manager.get_action(PMIP_MENU).add(action)
  end

  def self.unregister(key, id, force)
    action_manager.unregister_action(id)

    keymap.action_ids(key_stroke(key)).each{|bound_id|
      if !force && bound_id != id
        raise "- Unable to bind #{id} -> #{key}, because shortcut is already used by '#{bound_id}', to override use: 'bind [key_stroke], [action], {:force = true}'"
      else
        keymap.get_shortcuts(bound_id).each{|s| keymap.remove_shortcut(bound_id, s) }
      end
    }
  end

  def self.register(key, action, id)
    action_manager.register_action(id, action)
    keymap.add_shortcut(id, shortcut(key)) unless key == ''
  end

  def self.keymap
    KeymapManager.instance.active_keymap
  end

  def self.action_manager
    ActionManager.instance
  end

  #TOOD: remove duplication with mounter
  def self.render_usages(id)
    count = usages(id)
    count == 0 ? '' : "(#{count})"
  end
end

def bind(key, action='', options={})
  force = options.has_key?(:force) ? options[:force] : true
  #TIP: yikes, nasty, if only one param then assume its an action without a key
  action == '' ? Binder.bind('', key, force) : Binder.bind(key, action, force)
end

#TODO: add $plugin namespace support
action_manager = ActionManager.instance
pmip_action_group = action_manager.getAction(PMIP_MENU)
pmip_action_group.remove_all