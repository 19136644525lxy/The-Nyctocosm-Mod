#built using mc-build (https://github.com/mc-build/mc-build)

execute at @e[tag=dashtarget,limit=1] run tp @s ~ ~ ~
particle explosion_emitter ~ ~1 ~ 0 0 0 0 1
particle flash ~ ~1 ~ 0.3 0.3 0.3 0 2
kill @e[tag=dashtarget]
playsound nyctocosm:sorcerer.airdash.slam master @a[distance=..30] ~ ~ ~ 5
execute as @a[distance=..4] at @s run function sorcerer:attacks/airdash/hit