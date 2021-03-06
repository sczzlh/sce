package com.nova.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.nova.game.actor.HandMahjongs;
import com.nova.game.actor.TimeUnit;
import com.nova.game.BaseGame;
import com.nova.game.BaseScreen;
import com.nova.game.BaseStage;
import com.nova.game.assetmanager.Assets;
import com.nova.game.model.MahjGameController;

import java.util.HashMap;

import nova.common.game.mahjong.data.MahjGroupData;

public class GameScreen extends BaseScreen {
    private BaseStage mStage;
    private SpriteBatch mBatch;
    private Texture mBg;
    private TimeUnit mTime;
    private Assets mAssents;

    private MahjGameController mController = MahjGameController.create("local");

    private int[] mahjhand = new int[]{11, 11, 11, 12, 13, 14, 15, 16, 17, 18, 19, 19, 19};

    public GameScreen(BaseGame game) {
        super(game);
        mController.startGame();
    }

    @Override
    public void show() {
        mStage = new BaseStage(this);
        Gdx.input.setInputProcessor(mStage);
        Gdx.input.setCatchBackKey(true);

        mAssents = Assets.getInstance();
        mAssents.loadMahjTexture();

        mBatch = new SpriteBatch();
        mBg = new Texture("ScenceGame/background.jpg");

        mTime = new TimeUnit();
        mTime.setPosition(570, 295);
        mStage.addActor(mTime);

        HashMap<Integer, MahjGroupData> playerDatas = mController.getGroupDatas();

        HandMahjongs myHands = new HandMahjongs(0, playerDatas.get(0).getDatas());
        myHands.setPosition(120, 62, Align.bottomLeft);
        mStage.addActor(myHands);

        HandMahjongs leftHands = new HandMahjongs(3, mahjhand);
        leftHands.setPosition(60, 600, Align.topLeft);
        mStage.addActor(leftHands);

        HandMahjongs rightHands = new HandMahjongs(1, mahjhand);
        rightHands.setPosition(1250, 600, Align.bottomRight);
        mStage.addActor(rightHands);

        HandMahjongs topHands = new HandMahjongs(2, mahjhand);
        topHands.setPosition(300, 660, Align.topRight);
        mStage.addActor(topHands);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mBatch.begin();
        mBatch.draw(mBg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mBatch.end();

        mStage.act();
        mStage.draw();
    }

    @Override
    public void dispose() {
        mAssents.clearMahjTexture();
        mBatch.dispose();
        mBg.dispose();
        mStage.dispose();
    }
}
