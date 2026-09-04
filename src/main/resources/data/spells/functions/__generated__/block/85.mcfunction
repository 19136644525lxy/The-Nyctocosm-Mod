#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s rotated ~ 0 positioned ^ ^-1.4 ^1.3 run function spells:__generated__/block/86
execute at @s run playsound nyctocosm:spells.reflectance.dissipate master @a[distance=..30] ~ ~ ~ 2
execute at @s run particle minecraft:item glowstone_dust{CustomModelData:10} ~ ~ ~ 0.5 0.5 0.5 0.1 100 force
kill @s