package com.yifei.nyctocosm.client.geckolib.model;

import com.yifei.nyctocosm.client.geckolib.NyctocosmGeoModel;
import com.yifei.nyctocosm.entity.spell.SpellPlaneEntity;

/**
 * 法术平面（ajspellplane rig）GeoModel：7 骨骼 — maincircle / outercircle1/2 / orb1-3 / glyphcircle
 * <p>
 * 动画：
 * - idle   循环播放  (原 400 tick = 20 s)
 * - cast   单次播放  (原 17 tick)
 * - fizzle 单次播放  (原 18 tick)
 * <p>
 * 资源位置：
 * - geo:        assets/nyctocosm/geo/spellplane.geo.json
 * - texture:    assets/nyctocosm/textures/entity/spell/spellplane.png
 * - animations: assets/nyctocosm/animations/spell/spellplane.animation.json
 */
public class SpellPlaneModel extends NyctocosmGeoModel<SpellPlaneEntity> {

    public SpellPlaneModel() {
        super("spellplane", "spell");
    }
}
