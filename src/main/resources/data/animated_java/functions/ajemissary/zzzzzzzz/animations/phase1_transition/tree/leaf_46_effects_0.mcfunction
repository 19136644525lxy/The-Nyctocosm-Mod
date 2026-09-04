particle explosion_emitter ~ ~7 ~ 0 0 0 0 1 force
particle lava ~ ~7 ~ 1 1 1 0 50 force
particle flame ~ ~7 ~ 1 1 1 0.1 100 force
execute positioned ~ ~7 ~ run function effects:lightning/explosion_sparks
scoreboard players set Emissary em.phase 2