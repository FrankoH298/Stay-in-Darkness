package com.stayinthedarkness.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledMapSiD {
    private final TmxMapLoader mapLoader;
    private final com.badlogic.gdx.maps.tiled.TiledMap map;
    private final OrthogonalTiledMapRenderer rendererMap;

    public TiledMapSiD(Batch batch) {
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map/tiledmap.tmx");
        rendererMap = new OrthogonalTiledMapRenderer(map, batch);
    }
    public void dispose(){
        map.dispose();
        rendererMap.dispose();
    }

    public TmxMapLoader getMapLoader() {
        return mapLoader;
    }

    public com.badlogic.gdx.maps.tiled.TiledMap getMap() {
        return map;
    }

    public OrthogonalTiledMapRenderer getRendererMap() {
        return rendererMap;
    }
}
