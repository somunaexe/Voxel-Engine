package RenderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import Entities.Entity;
import Models.TexturedModel;
import Shaders.StaticShader;
import Toolbox.Maths;

public class EntityRenderer {
	
	StaticShader shader = new StaticShader();
	
//	public static void render(Entity entity, StaticShader shader) {
//		GL30.glBindVertexArray(entity.getModel().getModel().getVaoID());
//		GL20.glEnableVertexAttribArray(0);
//		GL20.glEnableVertexAttribArray(1);
//		
//		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
//		shader.loadTransformationMatrix(transformationMatrix);
//		
//		
//		GL13.glActiveTexture(GL13.GL_TEXTURE0);
//		GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getModel().getTexture().getTextureID());
//		GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
//		GL20.glDisableVertexAttribArray(0);
//		GL20.glDisableVertexAttribArray(1);
//		GL30.glBindVertexArray(0);
//	}
//	

	public void render (Map<TexturedModel, List<Entity>> entities){
		for(TexturedModel model : entities.keySet()) {
			GL30.glBindVertexArray(model.getModel().getVaoID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
			
			List<Entity> batch = entities.get(model);
			
			for(Entity entity : batch) {
				Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
				shader.loadTransformationMatrix(transformationMatrix);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
		}
	}
	
}
