#built using mc-build (https://github.com/mc-build/mc-build)

execute positioned ~ ~-20 ~ run particle explosion_emitter ~ ~0 ~ 4 4 4 0 10 force
execute positioned ~ ~-20 ~ run particle flame ~ ~0 ~ 4 4 4 0.4 60 force
execute positioned ~ ~-20 ~ run particle flash ~ ~0 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle firework ~ ~0 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle explosion_emitter ~ ~8 ~ 4 4 4 0 10 force
execute positioned ~ ~-20 ~ run particle flame ~ ~8 ~ 4 4 4 0.4 60 force
execute positioned ~ ~-20 ~ run particle flash ~ ~8 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle firework ~ ~8 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle explosion_emitter ~ ~16 ~ 4 4 4 0 10 force
execute positioned ~ ~-20 ~ run particle flame ~ ~16 ~ 4 4 4 0.4 60 force
execute positioned ~ ~-20 ~ run particle flash ~ ~16 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle firework ~ ~16 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle explosion_emitter ~ ~24 ~ 4 4 4 0 10 force
execute positioned ~ ~-20 ~ run particle flame ~ ~24 ~ 4 4 4 0.4 60 force
execute positioned ~ ~-20 ~ run particle flash ~ ~24 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle firework ~ ~24 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle explosion_emitter ~ ~32 ~ 4 4 4 0 10 force
execute positioned ~ ~-20 ~ run particle flame ~ ~32 ~ 4 4 4 0.4 60 force
execute positioned ~ ~-20 ~ run particle flash ~ ~32 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle firework ~ ~32 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle explosion_emitter ~ ~40 ~ 4 4 4 0 10 force
execute positioned ~ ~-20 ~ run particle flame ~ ~40 ~ 4 4 4 0.4 60 force
execute positioned ~ ~-20 ~ run particle flash ~ ~40 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle firework ~ ~40 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle explosion_emitter ~ ~48 ~ 4 4 4 0 10 force
execute positioned ~ ~-20 ~ run particle flame ~ ~48 ~ 4 4 4 0.4 60 force
execute positioned ~ ~-20 ~ run particle flash ~ ~48 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle firework ~ ~48 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle explosion_emitter ~ ~56 ~ 4 4 4 0 10 force
execute positioned ~ ~-20 ~ run particle flame ~ ~56 ~ 4 4 4 0.4 60 force
execute positioned ~ ~-20 ~ run particle flash ~ ~56 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle firework ~ ~56 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle explosion_emitter ~ ~64 ~ 4 4 4 0 10 force
execute positioned ~ ~-20 ~ run particle flame ~ ~64 ~ 4 4 4 0.4 60 force
execute positioned ~ ~-20 ~ run particle flash ~ ~64 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle firework ~ ~64 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle explosion_emitter ~ ~72 ~ 4 4 4 0 10 force
execute positioned ~ ~-20 ~ run particle flame ~ ~72 ~ 4 4 4 0.4 60 force
execute positioned ~ ~-20 ~ run particle flash ~ ~72 ~ 4 4 4 0.4 50 force
execute positioned ~ ~-20 ~ run particle firework ~ ~72 ~ 4 4 4 0.4 50 force
execute at @s run playsound nyctocosm:emissary.attack.sword_plunge.explode master @a[distance=..100] ~ ~ ~ 50
execute at @s positioned over world_surface as @a[distance=..15,gamemode=!spectator,tag=!emissarydebug] run function emissary:attacks/phase2/sword_plunge/__generated__/block/338
fill ~-10 ~-10 ~-3 ~10 ~10 ~3 fire replace air
kill @s