import static org.lwjgl.glfw.GLFW.glfwGetTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Timer {
		 
		private float timeCount;
		private int fps;
		private int fpsCount;
		private int ups;
		private int upsCount;
		
		private double lastLoopTime;
		private double analysistime;
		private double analysisstart;
		private double analysisstop;
		private Map<String, Double> analysis = new HashMap<String, Double>();
		private int i;
		
	 
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
		public void startAnalysis(){
			analysisstart = this.getTime();
			analysis = new HashMap<String, Double>();
			analysistime = this.getTime();
		}
		public void analysis(String name){
			double time = this.getTime();
			double delta = time - analysistime;
			analysistime = this.getTime();
			analysis.put(name + i++, delta);
		}
		public void stopAnalysis(){
			analysisstop = this.getTime();
			i = 0;
		}

		public void printAnalysis() {
			System.out.println("Time Analysis: ");
			Double span = analysisstop - analysisstart;
			Iterator<String> keys = analysis.keySet().iterator();
	    	while(keys.hasNext()){
	    		String key = keys.next();
	    		Double time = analysis.get(key);
	    		System.out.println("stage: " + key + " time%: " + time/span);
	    	}
		}

	

}
