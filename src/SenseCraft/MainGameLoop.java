package SenseCraft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import Entities.Camera;
import Entities.Entity;
import Models.CubeModel;
import Models.RawModel;
import Models.TexturedModel;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import RenderEngine.MasterRenderer;
import Shaders.StaticShader;
import Textures.ModelTexture;

public class MainGameLoop {

	public static Loader loader1 = null;
	public static StaticShader shader1 = null;
	
	static List<Entity> entities = Collections.synchronizedList(new ArrayList<Entity>());
	static Vector3f camPos = new Vector3f(0, 0, 0);
	static List<Vector3f> usedPos = new ArrayList<Vector3f>();
	static TexturedModel texModel;
	static final int WORLD_SIZE = 10;
	public static boolean closeDisplay = false;
	
	public static void main(String[] args) {
		DisplayManager.createDisplay();
		 
		Loader loader = new Loader();
		loader1 = loader;
		StaticShader shader = new StaticShader();
		shader1 = shader;
		MasterRenderer renderer = new MasterRenderer();
		
		RawModel model = loader.loadToVAO(CubeModel.vertices, CubeModel.indices, CubeModel.uv);
		ModelTexture texture = new ModelTexture(loader.loadTexture("dirtTex"));
		texModel = new TexturedModel(model, texture);
		
		Camera camera = new Camera(new Vector3f(0, 0, 0), 0, 0, 0);
		
		new Thread(new Runnable() {public void run() {
			
			while(!closeDisplay) {
				
				for (int x = (int) (camPos.x - WORLD_SIZE); x < camPos.x; x++) {
					for (int z = (int) (camPos.z); z < camPos.z + WORLD_SIZE; z++) {
						
						if(!usedPos.contains(new Vector3f(x, 0, z))) {
							entities.add(new Entity(texModel, new Vector3f(x, 0, z), 0, 0, 0, 1));
							usedPos.add(new Vector3f(x, 0, z));
						}
					}
				}
				
				for (int x = (int) (camPos.x); x < camPos.x + WORLD_SIZE; x++) {
					for (int z = (int) (camPos.z); z < camPos.z + WORLD_SIZE; z++) {
						
						if(!usedPos.contains(new Vector3f(x, 0, z))) {
							entities.add(new Entity(texModel, new Vector3f(x, 0, z), 0, 0, 0, 1));
							usedPos.add(new Vector3f(x, 0, z));
						}
					}
				}
			}
		}}).start();
		
		new Thread(new Runnable() {public void run() {
			
			while(!closeDisplay) {
				
				for (int x = (int) (camPos.x - WORLD_SIZE); x < camPos.x; x++) {
					for (int z = (int) (camPos.z - WORLD_SIZE); z < camPos.z; z++) {
						
						if(!usedPos.contains(new Vector3f(x, 0, z))) {
							entities.add(new Entity(texModel, new Vector3f(x, 0, z), 0, 0, 0, 1));
							usedPos.add(new Vector3f(x, 0, z));
						}
					}
				}
				
				for (int x = (int) (camPos.x); x < camPos.x + WORLD_SIZE; x++) {
					for (int z = (int) (camPos.z - WORLD_SIZE); z < camPos.z; z++) {
						
						if(!usedPos.contains(new Vector3f(x, 0, z))) {
							entities.add(new Entity(texModel, new Vector3f(x, 0, z), 0, 0, 0, 1));
							usedPos.add(new Vector3f(x, 0, z));
						}
					}
				}
			}
		}}).start();
		
		new Thread(new Runnable() {public void run() {
//			while(!Display.isCloseRequested()) {
//				  for(int i = 0; i < entities.size(); i++) {
//					  int distX = (int) (camPos.x - entities.get(i).getPosition().x);
//					  int distZ = (int) (camPos.z - entities.get(i).getPosition().z);
//					  
//					  if(distX < 0) {
//						  distX = -distX;
//					  }
//					  
//					  if (distZ < 0) {
//						  distZ = -distZ;
//					  }
//					  
//					  if ((distX > WORLD_SIZE) || (distZ > WORLD_SIZE)) {
//						  usedPos.remove(entities.get(i).getPosition());
//						  entities.remove(i);
//					  }
//				  }
//			}
		}}).start();
		
		while(!Display.isCloseRequested()) {
			camera.move();
			camPos = camera.getPosition();
		
			for (int i = 0; i < entities.size(); i++) {
				int distX = (int) (camPos.x - entities.get(i).getPosition().x);
				int distZ = (int) (camPos.z - entities.get(i).getPosition().z);
			  
				if(distX < 0) {
					distX = -distX;
				}
			  
				if (distZ < 0) {
					distZ = -distZ;
				}
			  
				if ((distX <= WORLD_SIZE) && (distZ <= WORLD_SIZE)) {
					renderer.addEntity(entities.get(i));
				}
			}
			
			renderer.render(camera);
			DisplayManager.updateDisplay();
		}
		DisplayManager.closeDisplay();
		closeDisplay = true;
	}

}
