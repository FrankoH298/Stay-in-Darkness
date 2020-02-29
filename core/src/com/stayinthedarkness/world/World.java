package com.stayinthedarkness.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class World {
    private final TmxMapLoader mapLoader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer rendererMap;

    public World(Batch batch) {
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

    public TiledMap getMap() {
        return map;
    }

    public OrthogonalTiledMapRenderer getRendererMap() {
        return rendererMap;
    }
}
