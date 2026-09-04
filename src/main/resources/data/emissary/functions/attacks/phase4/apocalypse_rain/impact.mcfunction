#built using mc-build (https://github.com/mc-build/mc-build)

execute as @a[gamemode=!spectator,tag=!emissarydebug,distance=..5] at @s run function emissary:attacks/phase4/apocalypse_rain/__generated__/block/696
playsound minecraft:entity.generic.explode master @a[distance=..50] ~ ~ ~ 10
particle explosion_emitter ~ ~ ~ 0 0 0 0 1 force