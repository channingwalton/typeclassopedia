class Focus
  def self.ide
    #TIP: unfortunately windows seems to disrespect this
    WindowManager.instance.get_ide_frame(nil).toFront
  end
end