package jaescobar.sacaz;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class SplashScreen extends SimpleBaseGameActivity {

	private static int CAMERA_WIDTH = 800;
	private static int CAMERA_HEIGHT = 480;
	private ITextureRegion backgroundCity, backgroundLamps, backgroundStars;
	private TiledTextureRegion zombie1;
	private BitmapTextureAtlas backgroundAtlas, zombieAtlas;
	
	public EngineOptions onCreateEngineOptions() {
		Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

	@Override
	protected void onCreateResources() {
		this.backgroundAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024);
		this.zombieAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		this.backgroundCity = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.backgroundAtlas, this, "gfx/background_city.png",0,0);
		this.backgroundLamps = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.backgroundAtlas, this, "gfx/background_lamps.png",0,481);
		this.backgroundStars = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.backgroundAtlas, this, "gfx/background_stars.png",0,624);
		this.zombie1 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.zombieAtlas, this, "gfx/zombieIntro.png", 0,0,3,1);
		backgroundAtlas.load();
		zombieAtlas.load();
	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		final Scene scene = new Scene();
		final AutoParallaxBackground parallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
		
		parallaxBackground.attachParallaxEntity(new ParallaxEntity(1.0f, new Sprite(0,0,this.backgroundStars,vertexBufferObjectManager)));
		parallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0,0,this.backgroundCity,vertexBufferObjectManager)));
		parallaxBackground.attachParallaxEntity(new ParallaxEntity(-5f, new Sprite(0,342,this.backgroundLamps,vertexBufferObjectManager)));
		parallaxBackground.setColor(0.1647f, 0.3803f, 0.6745f);
		
		scene.setBackground(parallaxBackground);
		
		final AnimatedSprite zombie = new AnimatedSprite(340, CAMERA_HEIGHT - (26 + zombie1.getHeight()), zombie1, vertexBufferObjectManager);
		zombie.animate(new long[]{300, 300, 300}, 0, 2, true);
		
		scene.attachChild(zombie);
		
		
		return scene;
	}
}
