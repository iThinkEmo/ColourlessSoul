package mx.sagh.soul;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by User on 17/02/2017.
 */

public class PantallaCreditos extends Pantalla {
    private final colourlessSoul menu;

    //texturas
    private Texture texturaFondo;
    private Texture texturaCreditos;
    private Image imgCredits;
    boolean vanish = false;

    //Escena
    private Stage escena;
    private SpriteBatch batch;

    public PantallaCreditos(colourlessSoul menu) {
        this.menu = menu;
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
        Image imgFondo = new Image(texturaFondo);
        escena.addActor(imgFondo);

        //Boton

        imgCredits = new Image(texturaCreditos);
        imgCredits.setPosition(0,-ALTO+140);
        escena.addActor(imgCredits);

        Gdx.input.setInputProcessor(escena);
        Gdx.input.setCatchBackKey(true);
    }

    private void cargarTexturas() {
        texturaFondo = new Texture("fondoPrincipal.jpg");
        texturaCreditos = new Texture("creditos.png");
    }


    @Override
    public void render(float delta) {
        // 60 x seg
        actualizarObjetos(delta);
        if (vanish) imgCredits.setColor(1,1,1,imgCredits.getColor().a-0.01f);
        if(imgCredits.getColor().a<=0) menu.setScreen(new PantallaExtras(menu));
        borrarPantalla();
        escena.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            menu.setScreen(new PantallaExtras(menu));
        }
    }

    private void actualizarObjetos(float delta) {
        if(imgCredits.getY()!=0) imgCredits.setY(imgCredits.getY()+1);
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
        texturaFondo.dispose();
        texturaCreditos.dispose();
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
            vanish = true;
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
}