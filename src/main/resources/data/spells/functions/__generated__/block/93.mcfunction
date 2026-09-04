#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s run playsound nyctocosm:spells.adamance.dissipate master @a[distance=..30] ~ ~ ~ 2
execute at @s run particle minecraft:item glowstone_dust{CustomModelData:12} ~ ~1 ~ 0.5 0.5 0.5 0.1 100 force
kill @s