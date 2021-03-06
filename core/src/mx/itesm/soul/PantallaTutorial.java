package mx.itesm.soul;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static mx.itesm.soul.ColourlessSoul.clickSound;

/**
 * Created by User on 28/03/2017.
 */

public class PantallaTutorial extends Pantalla {
    private final mx.itesm.soul.ColourlessSoul menu;
    private final AssetManager manager;
    //sonidos
    private Sound efectoHoja;

    private Animation<TextureRegion> spriteAnimado1, spriteAnimado2;         // Animación caminando, en reposo y parándose
    private float timerAnimacion1, timerAnimacion2;

    private EstadoTutorial estadoTutorial;

    //texturas
    private Texture texturaTutorial1;
    private Texture texturaTutorialSprites1;
    private Texture texturaTutorialSprites2;
    private Texture texturaTutorialSprites3;
    private Texture texturaTutorialSprites4;
    private Image imgTutorial1;

    //Escena
    private Stage escena;
    private SpriteBatch batch;
    private TextureRegion region;

    //Preferencias
    private Preferences prefs = Gdx.app.getPreferences("Settings");

    public PantallaTutorial(mx.itesm.soul.ColourlessSoul menu) {
        this.menu = menu;
        manager=menu.getAssetManager();
    }

    @Override
    public void show() {
        // Cuando cargan la pantalla
        cargarTexturas();
        crearObjetos();
        Gdx.input.setInputProcessor(new Procesador());
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escena = new Stage(vista, batch);
        escena.addActor(imgTutorial1);

        TextureRegion texturaCompleta1 = new TextureRegion(texturaTutorialSprites1);
        TextureRegion[][] texturaAnimada1 = texturaCompleta1.split(1280,800);
        TextureRegion texturaCompleta2 = new TextureRegion(texturaTutorialSprites2);
        TextureRegion[][] texturaAnimada2 = texturaCompleta2.split(1280,800);
        spriteAnimado1 = new Animation(0.1f, texturaAnimada1[0][0], texturaAnimada1[0][1], texturaAnimada1[0][2], texturaAnimada2[0][0], texturaAnimada2[0][1]);

        TextureRegion texturaCompleta3 = new TextureRegion(texturaTutorialSprites3);
        TextureRegion[][] texturaAnimada3 = texturaCompleta3.split(1280,800);
        TextureRegion texturaCompleta4 = new TextureRegion(texturaTutorialSprites4);
        TextureRegion[][] texturaAnimada4 = texturaCompleta4.split(1280,800);
        spriteAnimado2 = new Animation(0.1f, texturaAnimada3[0][0], texturaAnimada3[0][1], texturaAnimada3[0][2], texturaAnimada4[0][0], texturaAnimada4[0][1]);

        //Cargar audios
        manager.load("FondosTutorial/turnPage.mp3",Sound.class);
        manager.finishLoading();
        efectoHoja = manager.get("FondosTutorial/turnPage.mp3");

        estadoTutorial = EstadoTutorial.ESTATICO;
        Gdx.input.setInputProcessor(escena);
        Gdx.input.setCatchBackKey(true);
    }

    private void cargarTexturas() {
        texturaTutorial1 = manager.get("FondosTutorial/howTo1.png");
        texturaTutorialSprites1 = manager.get("FondosTutorial/howToSprites1.png");
        texturaTutorialSprites2 = manager.get("FondosTutorial/howToSprites2.png");
        texturaTutorialSprites3 = manager.get("FondosTutorial/howToSprites3.png");
        texturaTutorialSprites4 = manager.get("FondosTutorial/howToSprites4.png");
        imgTutorial1 = new Image(texturaTutorial1);
    }


    @Override
    public void render(float delta) {
        // 60 x seg
        borrarPantalla();
        batch.begin();
        if(estadoTutorial == EstadoTutorial.CAMBIANDO1) {
            timerAnimacion1 += Gdx.graphics.getDeltaTime();
            region = spriteAnimado1.getKeyFrame(timerAnimacion1);
            batch.draw(region, 0, 0);
        }
        if(estadoTutorial == EstadoTutorial.CAMBIANDO2) {
            timerAnimacion2 += Gdx.graphics.getDeltaTime();
            region = spriteAnimado2.getKeyFrame(timerAnimacion2);
            batch.draw(region, 0, 0);
        }
        batch.end();
        escena.draw();
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            if(prefs.getBoolean("Sounds",true))
                clickSound.play();
            menu.setScreen(new mx.itesm.soul.PantallaExtras(menu));
            clickSound.stop();
        }
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        escena.dispose();
        manager.unload("FondosTutorial/howToSprites1.png");
        manager.unload("FondosTutorial/howToSprites2.png");
        manager.unload("FondosTutorial/howToSprites3.png");
        manager.unload("FondosTutorial/howToSprites4.png");
        manager.unload("FondosTutorial/howTo1.png");
    }

    private class Procesador implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if(estadoTutorial == EstadoTutorial.ESTATICO) {
                imgTutorial1.remove();
                estadoTutorial = EstadoTutorial.CAMBIANDO1;
                if(prefs.getBoolean("Sounds",true))
                    efectoHoja.play();
            }
            else if(estadoTutorial == EstadoTutorial.CAMBIANDO1) {
                estadoTutorial = EstadoTutorial.CAMBIANDO2;
                if(prefs.getBoolean("Sounds",true))
                    efectoHoja.play();
            }
            else {
                estadoTutorial = EstadoTutorial.ESTATICO;
                if(prefs.getBoolean("Sounds",true))
                    clickSound.play();
                menu.setScreen(new mx.itesm.soul.PantallaExtras(menu));
                clickSound.stop();
            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }

    private enum EstadoTutorial {
        ESTATICO,
        CAMBIANDO1,
        CAMBIANDO2
    }
}