import javax.sound.sampled.AudioSystem
import javax.sound.sampled.AudioInputStream

class Sound
  def self.play(file)
    Run.on_pooled_thread {
      clip = AudioSystem.getClip
      clip.open(AudioSystem.getAudioInputStream(java.io.File.new(file)))
      clip.loop(0)
    }
  end
end