import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Timer {
		 
		private float timeCount;
		private int fps;
		private int fpsCount;
		private int ups;
		private int upsCount;
		
		private double lastLoopTime;

		
	 
		public Timer() {
			//init();	
		}

		public void init() {
			lastLoopTime = getTime();
		}
	 
		public void update() {
			if (timeCount > 1f) {
				setFps(fpsCount);
				fpsCount = 0;

				setUps(upsCount);
				upsCount = 0;

				timeCount -= 1f;
			}
		}
	 
		/** 
		 * Calculate how many milliseconds have passed 
		 * since last frame.
		 * 
		 * @return milliseconds passed since last frame 
		 */
		public float getDelta() {
		    double time = getTime();
		    float delta = (float) (time - lastLoopTime);
		    lastLoopTime = time;
		    timeCount += delta;
		    return delta;
		}
	 
		/**
		 * Get the accurate system time
		 * 
		 * @return The system time in milliseconds
		 */
		public double getTime() {
		    return glfwGetTime();
		}
	 
		/**
		 * Calculate the FPS
		 */
		public void updateFPS() {
		    fpsCount++;
		}

		public void updateUPS() {
		    upsCount++;
		}

		public double getLastLoopTime() {
			// TODO Auto-generated method stub
			return this.lastLoopTime;
		}

		public int getUps() {
			return ups;
		}

		public void setUps(int ups) {
			this.ups = ups;
		}

		public int getFps() {
			return fps;
		}

		public void setFps(int fps) {
			this.fps = fps;
		}

		public float getTimeCount() {
			return timeCount;
		}

		public void setTimeCount(float timeCount) {
			this.timeCount = timeCount;
		}

		public int getFpsCount() {
			return fpsCount;
		}

		public void setFpsCount(int fpsCount) {
			this.fpsCount = fpsCount;
		}

		public int getUpsCount() {
			return upsCount;
		}

		public void setUpsCount(int upsCount) {
			this.upsCount = upsCount;
		}

		public void setLastLoopTime(double lastLoopTime) {
			this.lastLoopTime = lastLoopTime;
		}
	 
	

}
