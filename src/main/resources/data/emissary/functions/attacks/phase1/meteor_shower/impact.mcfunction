#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[tag=impact] as @a[distance=..3,gamemode=!spectator,tag=!emissarydebug] at @s run function emissary:attacks/phase1/meteor_shower/__generated__/block/66
execute at @s[tag=impact] run playsound minecraft:entity.generic.explode master @a[distance=..50] ~ ~ ~ 10
execute at @s[tag=impact] run particle explosion_emitter ~ ~ ~ 0 0 0 0 1 force
kill @s