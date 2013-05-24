 require 'yaml'

 class Stats
   STATS_FILENAME = 'stats.yaml'

   def self.track(action)
     content = load
     content[action] = 0 if content[action].nil?
     content[action] = content[action].succ
     save(content)
   end

   def self.usages(action)
     content = load
     content[action].nil? ? 0 : content[action]
   end

   private

   def self.load
     begin
       File.exists?(STATS_FILENAME) ? YAML::load_file(STATS_FILENAME) : {}
     rescue => e
       puts "Warning: unable to load stats - #{e}"
       File.delete(STATS_FILENAME) if File.exists?(STATS_FILENAME)
       {}
     end
   end

   def self.save(content)
     File.open(STATS_FILENAME, 'w') { |f| YAML.dump(content, f) }
   end
 end

 def track(action)
   Stats.track(action)
 end

 def usages(action)
   Stats.usages(action)
 end