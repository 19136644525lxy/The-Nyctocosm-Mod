#built using mc-build (https://github.com/mc-build/mc-build)

execute as @a[distance=..5] at @s run function sorcerer:attacks/voidmeteor/hit
particle explosion_emitter ~ ~ ~ 0 0 0 0 1 force
playsound nyctocosm:sorcerer.voidmeteor.impact master @a[distance=..50] ~ ~ ~ 5
playsound minecraft:entity.generic.explode master @a[distance=..50] ~ ~ ~ 5
kill @s